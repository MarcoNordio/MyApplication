package com.example.marco.wifiprova;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
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
        try {

            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            InputStream inputstream = client.getInputStream();
            serverSocket.close();
        } catch (IOException e) {
            Toast.makeText(context, "Non sono riuscito a farmi trasferire il file", Toast.LENGTH_SHORT).show();
            return null;
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        if (result != null) {

        }


    }
}