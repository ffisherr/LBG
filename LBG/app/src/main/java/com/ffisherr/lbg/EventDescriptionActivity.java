package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class EventDescriptionActivity extends AppCompatActivity {

    public static final String TITLE_EXTRA = "title_extra";
    public static final String ABOUT_EXTRA = "about_extra";
    public static final String DT_EXTRA    = "dt_extra";
    public static final String ID_EXTRA    = "id_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        Intent intent = getIntent();
        String about = intent.getStringExtra(ABOUT_EXTRA);
        String title = intent.getStringExtra(TITLE_EXTRA);
        String dt    = intent.getStringExtra(DT_EXTRA);
        String id    = intent.getStringExtra(ID_EXTRA);

        TextView textView = findViewById(R.id.title_text);
        textView.setText(title);

        textView = findViewById(R.id.about_text);
        textView.setText(about);


        textView = findViewById(R.id.id_text);
        textView.setText(id);

        textView = findViewById(R.id.dt_text);
        textView.setText(dt);
    }
}
