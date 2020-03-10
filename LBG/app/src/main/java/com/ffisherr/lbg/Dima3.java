package com.ffisherr.lbg;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Dima3 extends AppCompatActivity {

    String[] names = { "МГТУ", "МГУ", "МАИ", "МЭИ" };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dima3);

        // находим список
        ListView University = (ListView) findViewById(R.id.University);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        University.setAdapter(adapter);


    }
}