package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;

    public static final String PREFERENCE_NAME = "my_settings";
    public static final String ROLE_TEXT       = "role_text";
    public static final String LOGIN_TEXT      = "login_text";
    public static final String IS_KNOWN_BOOL   = "is_known_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        String  uLogin  = sPref.getString(LOGIN_TEXT    , "");
        String  uRole   = sPref.getString(ROLE_TEXT     , "unknownUser");
        Boolean isKnown = sPref.getBoolean(IS_KNOWN_BOOL, false);

        if (isKnown.equals(false)) {
            Intent intent = new Intent(MainActivity.this, Dima.class);
            startActivity(intent);
        }

    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(MainActivity.this, Dima.class);
        startActivity(intent);
    }

    public void onClickChat(View view) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        startActivity(intent);
    }

    public void onClickSmthElse(View view) {
        Intent intent = new Intent(MainActivity.this, llexmActivity.class);
        startActivity(intent);
    }
}
