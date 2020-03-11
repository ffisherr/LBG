package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
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

        calendar_layout = (LinearLayout) findViewById(R.id.calendar_layout);

        String [] tags = {"public"}; //TODO это мы спрашиваем по тегу паблик?
        getEvents(tags); //спросить у сервера события
        if (myEvents != null) {
            //значит что-то пришло
            serverAnswered();
        } else {
            Toast.makeText(this, "нет ответа от сервера", Toast.LENGTH_LONG).show();
        }

    }

    protected void serverAnswered() {
        //в цикле перебора пришедших событий они добавляются в два layouta, из которых отображается один
        for (EventPesronse event : myEvents) {
            //System.out.println(event.getTitle());
            int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    0, wrapContent);
            LinearLayout common_calendar_layout = new LinearLayout(calendar_layout.getContext());
            common_calendar_layout.setOrientation(LinearLayout.VERTICAL);
            calendar_layout.addView(common_calendar_layout, lParams);
            LinearLayout personal_calendar_layout = new LinearLayout(calendar_layout.getContext());
            personal_calendar_layout.setOrientation(LinearLayout.VERTICAL);
            calendar_layout.addView(personal_calendar_layout, lParams);
            //TODO в зависимости от выбранного календаря задать видимость
            //personal_calendar_layout.setVisibility(View.INVISIBLE);

            if (true) //TODO сравнить нужный тег
            {
                lParams = new LinearLayout.LayoutParams(
                        0, wrapContent);
                lParams.gravity = Gravity.LEFT;
                LinearLayout newLayout = new LinearLayout(common_calendar_layout.getContext());
                newLayout.setOrientation(LinearLayout.HORIZONTAL);
                common_calendar_layout.addView(newLayout, lParams);

                TextView newLabel = new TextView(newLayout.getContext());
                newLabel.setText(event.getTitle());
                newLabel.setWidth(0);
                newLayout.addView(newLabel);

                Button newButton = new Button(newLayout.getContext()); //TODO дизайн кнопки найти или заменить кнопку на текствью с обработчиком(?)
                newButton.setText("+");
                newButton.setWidth(wrapContent);
                newButton.setGravity(Gravity.RIGHT);
                newLayout.addView(newButton);
                View.OnClickListener newBtnListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO отправляем запрос о том, что хотим добавить событие в личное
                    }
                };
                newButton.setOnClickListener(newBtnListener);
            }

            if (true) //TODO сравнить нужный тег
            {
                lParams = new LinearLayout.LayoutParams(
                        0, wrapContent);
                lParams.gravity = Gravity.LEFT;
                LinearLayout newLayout = new LinearLayout(personal_calendar_layout.getContext());
                newLayout.setOrientation(LinearLayout.VERTICAL);
                personal_calendar_layout.addView(newLayout, lParams);

                TextView newLabel = new TextView(newLayout.getContext());
                newLabel.setText(event.getTitle());
                newLabel.setWidth(0);
                newLayout.addView(newLabel);

                Button newButton = new Button(newLayout.getContext());
                newButton.setText("+");
                newButton.setWidth(wrapContent);
                newButton.setGravity(Gravity.RIGHT);
                newLayout.addView(newButton);
                View.OnClickListener newBtnListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO отправляем запрос о том, что хотим добавить событие в личное
                    }
                };
                newButton.setOnClickListener(newBtnListener);
            }
        }

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
