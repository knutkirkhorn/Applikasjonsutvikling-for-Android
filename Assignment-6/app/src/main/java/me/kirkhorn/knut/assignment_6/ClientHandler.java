package me.kirkhorn.knut.assignment_6;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Knut on 01.11.2017.
 */

public class ClientHandler extends Thread {
    private final static String TAG = "ClientHandler";
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            String aLine = bufferedReader.readLine();
            while (aLine != null) {
                if (aLine.contains(":")) {
                    int number1 = Integer.parseInt(aLine.split(":")[0]);
                    int number2 = Integer.parseInt(aLine.split(":")[1]);
                    int sum = number1 + number2;
                    printWriter.println(sum);
                }
                aLine = bufferedReader.readLine();
            }

            bufferedReader.close();
            printWriter.close();
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        }
    }
}
