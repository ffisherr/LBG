package com.ffisherr.lbg;

import android.os.AsyncTask;

import java.io.IOException;


public class TaskGetServer extends AsyncTask<String, Void, String>{
    @Override
    protected String doInBackground(String... urls) {
        ServerDescriptor serverDescriptor = new ServerDescriptor();
        String sResponse;
        try {
            sResponse = serverDescriptor.doGetRequest(urls[0]);
        } catch (IOException ex) {
            sResponse = "{'status':'connectionError'}";
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

