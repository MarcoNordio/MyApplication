package com.example.marco.wifiprova;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Marco on 18/12/16.
 */

public class FileTransferService extends IntentService {

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "com.example.pc_2.basicmetronome.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
    public static final String BPMS = "BMPS";

    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    /*
     * (non-Javadoc)
     * @see android.app.IntentService#onHandleIntent(android.content.Intent)
     */
    @Override
    protected void onHandleIntent(Intent intent) {

        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            DataOutputStream stream=null;
            String bpmsToSend=intent.getExtras().getString(BPMS);
            //String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            try {
                //Log.d(TabConnection.TAG, "Opening client socket - ");
                socket.bind(null);
                //socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                socket.connect((new InetSocketAddress(host, port)));
                //Log.d(TabConnection.TAG, "Client socket - " + socket.isConnected());
                stream =new DataOutputStream(socket.getOutputStream());
                stream.writeUTF(bpmsToSend);
                //Log.d(TabConnection.TAG, "Client: Data written");
            } catch (IOException e) {
                //Log.e(TabConnection.TAG, e.getMessage());
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
    }
}

