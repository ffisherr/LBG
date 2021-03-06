package com.ffisherr.lbg;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CreateEventActivity extends AppCompatActivity {


    private String uLogin;
    private String uUniversity;
    private Boolean uIsKnown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        uUniversity = intent.getStringExtra("university");
        uLogin      = intent.getStringExtra("login");
    }

    public void onClickCreateEvent(View view){
        EditText editText = findViewById(R.id.edit_event_title);

        String title = editText.getText().toString();
        if (title.equals("")) {
            Toast.makeText(this, "Введите заголовок", Toast.LENGTH_LONG).show();
            return;
        }
        editText = findViewById(R.id.edit_event_about);
        String about = editText.getText().toString();

        editText = findViewById(R.id.edit_event_date);
        String strDate = editText.getText().toString();
        DateFormat myDf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = null;
        try {
            d = myDf.parse(strDate);
        } catch (ParseException e) {
            Toast.makeText(this, "Неверный формат", Toast.LENGTH_LONG).show();
            return;
        };

        String[] tags = {Config.common_tag};
        RadioButton who = findViewById(R.id.only_me_can_see);
        if (who.isChecked()) {
            tags[0] = uLogin;
        }
        who = findViewById(R.id.erybody_can_see);
        if (who.isChecked()) {
            tags[0] = Config.common_tag;
        }
        who = findViewById(R.id.univer_can_see);
        if (who.isChecked()) {
            tags[0] = uUniversity;
        }

        EventPesronse event = new EventPesronse(-1, title, about, tags, strDate);

        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/add_event";

        Gson g = new Gson();
        String event_data = g.toJson(event);
        ts.execute(url, event_data);
        try {
            result = ts.get();
            if (result.equals("[{'status':'connectionError'}]")) {
                Toast.makeText(this, "Нет связи с сервером", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                EventPesronse getEvent = g.fromJson(result, EventPesronse.class);
                if (getEvent != null) {
                    if (getEvent.getId() != 0) {
                        event = getEvent;
                        Toast.makeText(this, "Событие успешно создано", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    Toast.makeText(this, "Событие успешно создано", Toast.LENGTH_LONG).show();
                    finish();
                }
                Toast.makeText(this, "Событие успешно создано", Toast.LENGTH_LONG).show();
                finish();
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка 1", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка 2", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка 3", Toast.LENGTH_LONG).show();
        }

    }
}
