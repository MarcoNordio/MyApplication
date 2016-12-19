package com.example.marco.wifiprova;
import android.content.Context;
import android.database.CursorJoiner;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Marco on 18/12/16.
 */

public class FileServerAsyncTask extends AsyncTask<Void, Void, String>  {

    private Context context;


    public FileServerAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        DataInputStream inputstream = null;
        ServerSocket serverSocket=null;
        Log.d(MainActivity.TAG,"Sono entrato dentro server");
        try {

            serverSocket = new ServerSocket(8988);
            Log.d(MainActivity.TAG, "Server: Socket opened");
            Socket client = serverSocket.accept();
            Log.d(MainActivity.TAG, "Server: connection done");

            inputstream = new DataInputStream(client.getInputStream());
            //Log.d(TabConnection.TAG, "server: copying files ");
            String str = inputstream.readUTF();
            serverSocket.close();
            return client.getInetAddress().toString();
        } catch (IOException e) {
            Toast.makeText(context, "Non sono riuscito a farmi trasferire il file", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }


    }
}