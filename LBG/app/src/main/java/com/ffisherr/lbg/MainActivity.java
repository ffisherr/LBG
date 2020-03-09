package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Dima.class);
        startActivity(intent);
    }

    public void onClickChat(View view) {
        // TODO: Go to chat activity
    }

    public void onClickSmthElse(View view) {
        // TODO: Go to smth else activity
    }
}
