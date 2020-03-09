package com.ffisherr.lbg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

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
        new taskServer().execute("https://www.vogella.com/index.html");
    }

    private class taskServer extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... param) {
            ServerDescriptor serverDescriptor = new ServerDescriptor();
            String sResponse = "";
            try {
                sResponse = serverDescriptor.doGetRequest("https://www.vogella.com/index.html");//"http://192.168.1.156:5002/users/1");
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
