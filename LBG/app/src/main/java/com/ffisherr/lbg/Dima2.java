package com.ffisherr.lbg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Dima2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dima2);
    }

    public void onClickLogin(View view) {
        EditText editTextLogin = findViewById(R.id.edit_user);
        String userLogin = editTextLogin.toString();
        String result;
        TaskGetServer ts = new TaskGetServer();
        String url = ServerDescriptor.serverIpAdress + "/get_user_by_login/" + userLogin;
        ts.execute(url);
        try {
            result = ts.get();
            Toast.makeText(this, "Полученный результат: " + result, Toast.LENGTH_LONG)
                    .show();
            // TODO Convert response from json to smth good
            //  Check server answer
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class TaskGetServer extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            ServerDescriptor serverDescriptor = new ServerDescriptor();
            String sResponse = "";
            try {
                sResponse = serverDescriptor.doGetRequest(urls[0]);
            } catch (IOException ex) {
                sResponse = "Нет соединения с сервером";
            }
            System.out.println(sResponse);
            return sResponse;
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println("Finished!!!");
            System.out.println(result);
            super.onPostExecute(result);
        }
    }

    private class TaskPostServer extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... urls) {
            ServerDescriptor serverDescriptor = new ServerDescriptor();
            String sResponse;
            try {
                sResponse = serverDescriptor.doPostRequest(urls[0], urls[1]);
            } catch (IOException ex) {
                sResponse = "Нет соединения с сервером";
            }
            System.out.println(sResponse);
            return sResponse;
        }
        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            super.onPostExecute(result);
        }
    }
}
