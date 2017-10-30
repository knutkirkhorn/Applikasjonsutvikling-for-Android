package me.kirkhorn.knut.assignment_5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private HttpConnection httpConnection;
    final private String TAG = "MainActivity";
    private boolean gameStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        httpConnection = new HttpConnection(this);
    }

    public void displayResponse(String response) {
        Log.i(TAG, response);
        if (response.contains("Oppgi et tall mellom ")) {
            // First attempt
            TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
            textViewAttempts.setText(response);
            textViewAttempts.setVisibility(View.VISIBLE);
            EditText editTextAttempts = findViewById(R.id.edit_text_attempt_number);
            editTextAttempts.setVisibility(View.VISIBLE);

            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.INVISIBLE);
        } else if(response.contains("du må starte på nytt")) {
            // All three attempts have been used
            gameStarted = false;
            TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
            textViewAttempts.setText(response);
            EditText editTextAttempts = findViewById(R.id.edit_text_attempt_number);
            editTextAttempts.setVisibility(View.INVISIBLE);

            RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
            relativeLayout.setVisibility(View.VISIBLE);
        } else if (response.contains("du har vunnet")){
            // User won
            TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
            textViewAttempts.setText(response);
            Button buttonSend = findViewById(R.id.button_send);
            buttonSend.setVisibility(View.INVISIBLE);
            EditText editTextAttempts = findViewById(R.id.edit_text_attempt_number);
            editTextAttempts.setVisibility(View.INVISIBLE);
        } else {
            // New attempt
            TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
            textViewAttempts.setText(response);
        }
    }

    private void startNewGame() {
        gameStarted = false;
        TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
        textViewAttempts.setVisibility(View.INVISIBLE);
        EditText editTextAttempts = findViewById(R.id.edit_text_attempt_number);
        editTextAttempts.setVisibility(View.INVISIBLE);

        RelativeLayout relativeLayout = findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.VISIBLE);

        Button buttonSend = findViewById(R.id.button_send);
        buttonSend.setVisibility(View.VISIBLE);
    }

    public void onSendButtonClick(View view) {
        if (!gameStarted) {
            gameStarted = true;
            EditText editTextName = findViewById(R.id.edit_text_name);
            EditText editTextCardNumber = findViewById(R.id.edit_text_card_number);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("navn", editTextName.getText().toString());
            parameters.put("kortnummer", editTextCardNumber.getText().toString());
            httpConnection.startNewThread(parameters);
            TextView textViewAttempts = findViewById(R.id.textViewAttemptNumber);
            textViewAttempts.setVisibility(View.INVISIBLE);
        } else {
            EditText editTextNumber = findViewById(R.id.edit_text_attempt_number);
            Map<String, String> parameters = new HashMap<>();
            parameters.put("tall", editTextNumber.getText().toString());
            httpConnection.startNewThread(parameters);
        }
    }

    public void onStartNewButtonClick(View view) {
        startNewGame();
    }
}
