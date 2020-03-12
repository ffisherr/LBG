package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class ChatActivity extends AppCompatActivity {
    ArrayAdapter<Message_Response> adapter;
    Message_Response   message;
    Message_Response[] AllMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }

    private void displayAllMessages() {
        getAllMessages();
        if (AllMessages != null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, AllMessages);
            //TODO сделать отображение полученного списка

        } else {
            Toast.makeText(this, "Ошибка получения сообщений", Toast.LENGTH_LONG).show();
        }
    }

    private void getAllMessages() {
        AllMessages = null;
        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/get_all_messages";

        Gson g = new Gson();
        ts.execute(url);
        try {
            result = ts.get();
            try {
                Message_Response[] getMess = g.fromJson(result, Message_Response[].class);
                if (getMess[0] != null) {
                    if (getMess[0].getId() != 0) {
                        AllMessages = getMess;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Message_Response mess) {
        message = null;

        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/messages/add_message";

        Gson g = new Gson();
        String event_data = g.toJson(mess);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            try {
                Message_Response getMess = g.fromJson(result, Message_Response.class);
                if (getMess != null) {
                    if (getMess.getId() != 0) {
                        message = getMess;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
