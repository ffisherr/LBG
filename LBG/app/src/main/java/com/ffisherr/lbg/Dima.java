package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dima extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dima);

        Button In = (Button)findViewById(R.id.In);
        In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Dima.this, Dima2.class);
                    startActivity(intent);finish();
                }catch (Exception e){

                }
            }
        });

        Button Reg = (Button)findViewById(R.id.Reg);
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent1 = new Intent(Dima.this, Dima3.class);
                    startActivity(intent1);finish();
                }catch (Exception e){

                }
            }
        });

    }
}
