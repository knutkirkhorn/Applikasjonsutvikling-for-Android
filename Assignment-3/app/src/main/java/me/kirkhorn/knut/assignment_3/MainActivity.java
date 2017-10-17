package me.kirkhorn.knut.assignment_3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.kirkhorn.knut.assignment_3.model.User;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<User> users = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        users.add(new User("Knut Kirkhorn", "10. nov. 1996"));
        users.add(new User("Ola Nordmann", "1. jan. 1996"));
        initializeSpinner();
    }

    void initializeSpinner() {
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item, users);
        Spinner spinner = (Spinner) findViewById(R.id.spinner_venner);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textViewName = (TextView) findViewById(R.id.text_view_name);
                textViewName.setText(users.get(position).getName());
                TextView textViewBirthday = (TextView) findViewById(R.id.text_view_birthday);
                textViewBirthday.setText(users.get(position).getBirthday());

                EditText editTextName = (EditText) findViewById(R.id.editText_name);
                editTextName.setText("");
                EditText editTextBirthday = (EditText) findViewById(R.id.editText_birthday);
                editTextBirthday.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String name = data.getStringExtra("newName");
            String birthday = data.getStringExtra("newBirthday");
            users.add(new User(name, birthday));
        }
    }

    public void onAddNewUserButtonClick(View view) {
        Intent intent = new Intent("me.kirkhorn.knut.VisVennerActivity");
        //startActivity(intent);
        startActivityForResult(intent, 1);
    }

    public void onChangeUserButtonClick(View view) {
        //Make visible
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout_change_user);
        gridLayout.setVisibility(View.VISIBLE);
        Button button = (Button) findViewById(R.id.button_dismiss_changes);
        button.setVisibility(View.VISIBLE);
        Button changeButton = (Button) findViewById(R.id.button_change_user);
        changeButton.setVisibility(View.INVISIBLE);
    }

    public void onSaveChangesButtonClick(View view) {
        Spinner spinner = (Spinner) findViewById(R.id.spinner_venner);
        User selectedUser = (User) spinner.getSelectedItem();
        EditText editTextName = (EditText) findViewById(R.id.editText_name);
        EditText editTextBirthday = (EditText) findViewById(R.id.editText_birthday);

        boolean changedSomething = false;
        String newName = editTextName.getText().toString();
        if (!newName.equals("")) {
            selectedUser.setName(newName);
            changedSomething = true;
            TextView textViewName = (TextView) findViewById(R.id.text_view_name);
            textViewName.setText(newName);
        }

        String newBirthDay = editTextBirthday.getText().toString();
        if (!newBirthDay.equals("")) {
            selectedUser.setBirthday(newBirthDay);
            changedSomething = true;
            TextView textViewBirthday = (TextView) findViewById(R.id.text_view_birthday);
            textViewBirthday.setText(newBirthDay);
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout_change_user);
        gridLayout.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.button_dismiss_changes);
        button.setVisibility(View.INVISIBLE);
        Button changeButton = (Button) findViewById(R.id.button_change_user);
        changeButton.setVisibility(View.VISIBLE);


        String toastText;
        if (changedSomething) {
            toastText = "Endringer ble lagret.";
        } else {
            toastText = "Ingen nye endringer.";
        }

        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        toast.show();
    }

    public void onDismissChangesButtonClick(View view) {
        //Change to default text values
        EditText editTextName = (EditText) findViewById(R.id.editText_name);
        editTextName.setText("");
        EditText editTextBirthday = (EditText) findViewById(R.id.editText_birthday);
        editTextBirthday.setText("");

        //Make invisible
        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid_layout_change_user);
        gridLayout.setVisibility(View.INVISIBLE);
        Button button = (Button) findViewById(R.id.button_dismiss_changes);
        button.setVisibility(View.INVISIBLE);
        Button changeButton = (Button) findViewById(R.id.button_change_user);
        changeButton.setVisibility(View.VISIBLE);
    }
}
