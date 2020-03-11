package com.ffisherr.lbg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class crestici extends AppCompatActivity {
    Button[][] fields = new Button[3][3];
    TableLayout Table1 = (TableLayout)findViewById(R.id.Table1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crestici);
        buildGame();


    }
    private void buildGame(){
        for (int i =0; i < 3; ++i) {
            TableRow row =new TableRow(this);
            for (int j =0; i < 3; ++j) {
                final Button button = new Button (this);
                fields[i][j]=button;
                row.addView(button,new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                fields[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setText("X");
                        button.setClickable(false);
                        moveA1();
                    }
                });
            }
            Table1.addView(row, new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        }
    }
      private void moveA1() {
          for (int i = 0; i < 3; ++i) {
              for (int j =0; i < 3; ++j){
                  if(fields[i][j].getText() != "X" && fields[i][j].getText() != "0"){
                      fields[i][j].setText("0");
                      fields[i][j].setClickable(false);
                      return;
                  }
              }

          }
      }
      private void checkVictory(){
        refresh();
      }
      private void refresh() {
          for (int i = 0; i < 3; ++i) {
              for (int j = 0; i < 3; ++j) {
                fields[i][j].setText("");
                fields[i][j].setClickable(true);
              }
          }
      }
}
