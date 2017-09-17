package me.kirkhorn.knut.assignment_2_exercise_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int upperLimit = 100;
        Intent intent = new Intent("me.kirkhorn.knut.NewActivity");
        intent.putExtra("upperLimit", upperLimit);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.text_view_random_number);
            String text = "Random number from other activity: " + Integer.toString(data.getIntExtra("randomNumber", 1));
            textView.setText(text);
        }
    }
}
