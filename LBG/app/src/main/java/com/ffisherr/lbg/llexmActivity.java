package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class llexmActivity extends AppCompatActivity {
    LinearLayout calendar_layout = (LinearLayout) findViewById(R.id.calendar_layout);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llexm);

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
}
