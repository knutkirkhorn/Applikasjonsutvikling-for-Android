package me.kirkhorn.knut.assignment_6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Client.OnCalculateNumbersListener {
    private Client client;
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRadioButtonsClick(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonClient:
                if (checked) {
                    Button buttonConnectServer = findViewById(R.id.buttonConnectServer);
                    buttonConnectServer.setVisibility(View.VISIBLE);
                    Button buttonStartServer = findViewById(R.id.buttonStartServer);
                    buttonStartServer.setVisibility(View.INVISIBLE);
                }
                break;

            case R.id.radioButtonServer:
                if (checked) {
                    Button buttonConnectServer = findViewById(R.id.buttonConnectServer);
                    buttonConnectServer.setVisibility(View.INVISIBLE);
                    Button buttonStartServer = findViewById(R.id.buttonStartServer);
                    buttonStartServer.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void onStartServerButtonClick(View view) {
        TextView textViewBase = findViewById(R.id.textViewBase);
        textViewBase.setText("Du er en server");
        Server server = new Server();
        server.start();
        Button startServerButton = findViewById(R.id.buttonStartServer);
        startServerButton.setVisibility(View.INVISIBLE);
        RadioGroup radioGroupButtons = findViewById(R.id.radioGroupButtons);
        radioGroupButtons.setVisibility(View.INVISIBLE);
    }

    public void onConnectServerButtonClick(View view) {
        TextView textViewBase = findViewById(R.id.textViewBase);
        textViewBase.setText("Du er en klient");
        LinearLayout layoutSumNumbers = findViewById(R.id.layoutSumNumbers);
        layoutSumNumbers.setVisibility(View.VISIBLE);
        //client = new Client(this);
        //client.start();
        Button connectServerButton = findViewById(R.id.buttonConnectServer);
        connectServerButton.setVisibility(View.INVISIBLE);
        RadioGroup radioGroupButtons = findViewById(R.id.radioGroupButtons);
        radioGroupButtons.setVisibility(View.INVISIBLE);

    }

    public void onCalculateSumButtonClick(View view) {
        EditText editTextNumber1 = findViewById(R.id.editTextNumber1);
        EditText editTextNumber2 = findViewById(R.id.editTextNumber2);
        int number1 = Integer.parseInt(editTextNumber1.getText().toString());
        int number2 = Integer.parseInt(editTextNumber2.getText().toString());

        client = new Client(this, number1, number2);
        client.start();
    }

    @Override
    public void OnCalculateNumbersListener(final int result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText editTextNumber1 = findViewById(R.id.editTextNumber1);
                EditText editTextNumber2 = findViewById(R.id.editTextNumber2);
                int number1 = Integer.parseInt(editTextNumber1.getText().toString());
                int number2 = Integer.parseInt(editTextNumber2.getText().toString());
                TextView textViewSum = findViewById(R.id.textViewSumNumbers);
                textViewSum.setVisibility(View.VISIBLE);
                textViewSum.setText("Summen av " + number1 + " og " + number2 + " er " + Integer.toString(result));
                Log.i(TAG, "Sum of " + number1 + " and " + number2 + " is " + Integer.toString(result));
            }
        });
    }
}
