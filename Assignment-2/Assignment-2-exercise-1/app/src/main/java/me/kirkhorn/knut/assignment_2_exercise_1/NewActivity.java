package me.kirkhorn.knut.assignment_2_exercise_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Knut on 17.09.2017.
 */

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        int upperLimit = getIntent().getIntExtra("upperLimit", 100);
        TextView textView = (TextView) findViewById(R.id.text_view_upper_limit);
        textView.setText("Upper limit:" + upperLimit);

        int randomNumber = (int) (Math.random() * upperLimit);
        /*String toastText = "Random number: " + randomNumber;
        Toast toast  = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
        toast.show();*/

        Intent intent = new Intent();
        intent.putExtra("randomNumber", randomNumber);
        setResult(RESULT_OK, intent);
        finish();
    }
}
