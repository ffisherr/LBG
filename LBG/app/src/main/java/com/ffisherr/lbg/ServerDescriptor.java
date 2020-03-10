package com.ffisherr.lbg;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class ServerDescriptor {
    private OkHttpClient client;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String serverIpAdress = "http://192.168.1.156:5002";

    public ServerDescriptor() {
        client = new OkHttpClient();
    }

    public String doGetRequest(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doPostRequest(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
