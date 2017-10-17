package me.kirkhorn.knut.assignment_3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Knut on 16.10.2017.
 */

public class VisVennerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vis_venner);
    }

    public void onNewUserButtonClicked(View view) {
        EditText editTextName = (EditText) findViewById(R.id.editText_new_user_name);
        EditText editTextBirthday = (EditText) findViewById(R.id.editText_new_user_birthday);
        String name = editTextName.getText().toString();
        String birthday = editTextBirthday.getText().toString();

        if (name.equals("") || birthday.equals("")) {
            Toast toast = Toast.makeText(this, "Fyll inn begge feltene for å legge til en ny venn",
                                                Toast.LENGTH_LONG);
            toast.show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("newName", name);
            intent.putExtra("newBirthday", birthday);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onCancelButtonClicked(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
