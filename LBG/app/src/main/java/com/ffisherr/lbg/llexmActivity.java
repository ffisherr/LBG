package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class llexmActivity extends AppCompatActivity {
    LinearLayout calendar_layout;// = (LinearLayout) findViewById(R.id.calendar_layout);
    EventPesronse [] myEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llexm);

        String [] tags = {"public"};
        getEvents(tags);
        if (myEvents != null) {
            // TODO значит что-то пришло
            for (EventPesronse event : myEvents) {
                System.out.println(event.getTitle());
            }
        }
        //TODO спросить у сервера события
    }

    protected void serverAnswered() {
        //TODO добавить все пришедшие события в два layout'а, которые будут отображаться попеременно

        //TODO в цикле перебора пришедших событий они добавляются в два layouta, из которых отображается один
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                0, wrapContent);
        lParams.gravity = Gravity.LEFT;
        LinearLayout newLayout = new LinearLayout(calendar_layout.getContext());
        newLayout.setOrientation(LinearLayout.VERTICAL);
        calendar_layout.addView(newLayout, lParams);
        TextView newLabel = new TextView(newLayout.getContext());
        newLabel.setText("текст события");
        newLayout.addView(newLabel);
        Button newButton = new Button(newLayout.getContext());
        newButton.setText("+");
        newLayout.addView(newButton);

    }


    public void getEvents(String [] tags) {
        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/get_event_by_tags";

        EventPesronse event = new EventPesronse(tags);
        Gson g = new Gson();
        String event_data = g.toJson(event);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            EventPesronse[] ev = g.fromJson(result, EventPesronse[].class);
            if (ev[0].getStatus().equals(ServerDescriptor.SUCCESS)) {
                myEvents = ev;
            } else if (ev[0].getStatus().equals(ServerDescriptor.INTERNET_ERROR)){
                Toast.makeText(this, "Нет доступа к серверу", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }



}
