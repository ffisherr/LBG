package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class llexmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llexm);

        //TODO спросить у сервера события
    }

    protected void serverAnswered() {
        //TODO добавить все пришедшие события в два layout'а, которые будут отображаться попеременно
    }
}
