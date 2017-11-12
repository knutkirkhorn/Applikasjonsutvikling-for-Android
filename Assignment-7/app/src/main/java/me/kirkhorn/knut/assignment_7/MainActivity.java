package me.kirkhorn.knut.assignment_7;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private String TAG = "MainActivity";
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems;
    private boolean authorRadioButtonSelected = false;
    private ArrayList<String[]> databaseFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseFile = readDatabaseFile();
        try {
            databaseManager = new DatabaseManager(this);
            databaseManager.clean();
            for (int i = 0; i < databaseFile.size(); i++ ) {
                databaseManager.insert(databaseFile.get(i)[0], databaseFile.get(i)[1]);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        ListView listView = findViewById(R.id.dbListView);
        listItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        String backgroundColor = getDefaultSharedPreferences(this).getString("backgroundColorPref", "#ffffff");
        listView.setBackgroundColor(Color.parseColor(backgroundColor));
    }

    private ArrayList<String[]> readDatabaseFile() {
        Log.i(TAG, "Reading database file");
        ArrayList<String[]> tempDatabaseFile = new ArrayList<>();
        int id = R.raw.databasefile;

        InputStream inputStream = getResources().openRawResource(id);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                String author = line;
                String book = bufferedReader.readLine();
                String[] authorBook = new String[]{author, book};
                tempDatabaseFile.add(authorBook);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return tempDatabaseFile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.menu_color_settings) {
            Intent intent = new Intent("me.kirkhorn.knut.SettingsActivity");
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ListView listView = findViewById(R.id.dbListView);
            String backgroundColor = getDefaultSharedPreferences(this).getString("backgroundColorPref", "#ffffff");
            listView.setBackgroundColor(Color.parseColor(backgroundColor));
        }
    }

    public void onRadioButtonsClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        Button buttonShowElements = findViewById(R.id.buttonShowElements);
        buttonShowElements.setVisibility(View.VISIBLE);

        switch (view.getId()) {
            case R.id.radioButtonAuthor:
                if (checked) {
                    authorRadioButtonSelected = true;
                }
                break;

            case R.id.radioButtonTitle:
                if (checked) {
                    authorRadioButtonSelected = false;
                }
                break;
        }
    }

    public void onShowElementsButtonClicked(View view) {
        if (authorRadioButtonSelected) {
            ArrayList<String> authors = databaseManager.getAllAuthors();
            listItems.clear();
            listItems.addAll(authors);
            adapter.notifyDataSetChanged();
        } else {
            ArrayList<String> books = databaseManager.getAllBooks();
            listItems.clear();
            listItems.addAll(books);
            adapter.notifyDataSetChanged();
        }
    }
}
