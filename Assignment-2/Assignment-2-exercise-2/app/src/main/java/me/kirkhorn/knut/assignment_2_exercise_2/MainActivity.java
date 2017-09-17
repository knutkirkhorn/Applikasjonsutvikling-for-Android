package me.kirkhorn.knut.assignment_2_exercise_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void checkCalculation(boolean adding) {
        try {
            TextView number1TextView = (TextView) findViewById(R.id.number_one);
            int number1 = Integer.parseInt(number1TextView.getText().toString());
            TextView number2TextView = (TextView) findViewById(R.id.number_two);
            int number2 = Integer.parseInt(number2TextView.getText().toString());
            int sum = 0;
            if (adding) {
                sum = number1 + number2;
            } else {
                sum = number1 * number2;
            }

            EditText answerEditText = (EditText) findViewById(R.id.edittext_answer);
            int answer = Integer.parseInt(answerEditText.getText().toString());

            String toastText;
            if (sum == answer) {
                toastText = getString(R.string.correct);
            } else {
                toastText = getString(R.string.wrong) + " " + sum;
            }

            Toast toast  = Toast.makeText(this, toastText, Toast.LENGTH_LONG);
            toast.show();
        } catch(Exception e) {
            String exceptionMessage = "Feil: Du skrev inn noe ugyldig";
            Toast toast  = Toast.makeText(this, exceptionMessage, Toast.LENGTH_LONG);
            toast.show();
        }

        TextView upperLimitEditText = (TextView) findViewById(R.id.upperLimitEditText);
        int upperLimit = Integer.parseInt(upperLimitEditText.getText().toString());

        Intent intent = new Intent("me.kirkhorn.knut.NewActivity");
        intent.putExtra("upperLimit", upperLimit);
        startActivityForResult(intent, 1);

        Intent intent2 = new Intent("me.kirkhorn.knut.NewActivity");
        intent2.putExtra("upperLimit", upperLimit);
        startActivityForResult(intent2, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.number_one);
            textView.setText(Integer.toString(data.getIntExtra("randomNumber", 1)));
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            TextView textView = (TextView) findViewById(R.id.number_two);
            textView.setText(Integer.toString(data.getIntExtra("randomNumber", 1)));
        }
    }

    public void onAddingButtonClick(View view) {
        checkCalculation(true);
    }

    public void onMultiplyButtonClick(View view) {
        checkCalculation(false);
    }
}
