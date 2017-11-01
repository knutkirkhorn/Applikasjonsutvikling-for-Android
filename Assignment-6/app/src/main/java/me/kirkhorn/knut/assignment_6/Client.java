package me.kirkhorn.knut.assignment_6;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Knut on 31.10.2017.
 */

public class Client extends Thread {
    private final static String TAG = "Client";
    private final static String IP_ADDRESS = "10.0.2.2";
    private final static int PORT_NUMBER= 1337;
    private Socket socket = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;
    private PrintWriter printWriter = null;
    private OnCalculateNumbersListener numbersListener;
    private int number1;
    private int number2;

    public Client(Activity activity, int number1, int number2) {
        this.number1 = number1;
        this.number2 = number2;
        try {
            numbersListener = (OnCalculateNumbersListener) activity;
        } catch (ClassCastException cce) {
            throw new ClassCastException(activity.toString() + " must implement clientListener");
        }
    }

    @Override
    public void run() {
        try {
            socket = new Socket(IP_ADDRESS, PORT_NUMBER);
            Log.i(TAG, "Connected to server: " + socket);
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);
            printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(number1 + ":" + number2);

            String response = bufferedReader.readLine();
            numbersListener.OnCalculateNumbersListener(Integer.parseInt(response));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }

                if (bufferedReader != null) {
                    bufferedReader.close();
                }

                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnCalculateNumbersListener {
        void OnCalculateNumbersListener(int result);
    }
}
