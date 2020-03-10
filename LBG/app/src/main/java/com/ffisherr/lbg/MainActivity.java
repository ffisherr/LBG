package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sPref;

    private final String ROLE_TEXT     = "role_text";
    private final String LOGIN_TEXT    = "login_text";
    private final String IS_KNOWN_BOOL = "is_known_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref = getPreferences(MODE_PRIVATE);
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
        // TODO: Go to chat activity
    }

    public void onClickSmthElse(View view) {
        // TODO: Go to smth else activity
    }
}
