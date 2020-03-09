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
        System.out.println("Entered");
        EditText editTextLogin = findViewById(R.id.edit_user);
        String userLogin = editTextLogin.toString();
        TextView serverResponse = findViewById(R.id.serverResponse);
        serverResponse.setText(userLogin);
        String p, result;
        TaskServer ts = new TaskServer();
        ts.execute("http://192.168.1.156:5002/users/1");
        if (ts == null)
            return;
        try {
            result = ts.get();
            Toast.makeText(this, "Полученный результат: " + result, Toast.LENGTH_LONG)
                    .show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class TaskServer extends AsyncTask<String, Void, String>{
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
}
