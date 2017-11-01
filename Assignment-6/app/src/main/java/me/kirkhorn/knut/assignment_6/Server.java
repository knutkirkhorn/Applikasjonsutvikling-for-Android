package me.kirkhorn.knut.assignment_6;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Knut on 31.10.2017.
 */

public class Server extends Thread {
    private final static String TAG = "Server";
    private final static int PORT_NUMBER= 1337;

    @Override
    public void run() {
        Socket socket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT_NUMBER);
            Log.i(TAG, "Server started");

            while (true) {
                socket = serverSocket.accept();
                ClientHandler client = new ClientHandler(socket);
                client.start();
                Log.i(TAG, "New client: " + socket.toString());
            }
        } catch (IOException ioe) {
            Log.e(TAG, ioe.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
