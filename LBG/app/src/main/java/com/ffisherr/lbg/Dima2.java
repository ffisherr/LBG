package com.ffisherr.lbg;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Dima2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dima2);
    }

    public void onClickLogin(View view) {
        EditText editTextLogin = findViewById(R.id.edit_user);
        String userLogin = editTextLogin.getText().toString();
        EditText editTextPassw = findViewById(R.id.edit_password);
        String userPassw = editTextPassw.getText().toString();
        String result;
        TaskPostServer ts = new TaskPostServer();
        String url = ServerDescriptor.serverIpAdress + "/get_user_by_login";
        UserResponse u = new UserResponse(userLogin, userPassw);
        Gson g = new Gson();
        String user_data = g.toJson(u);
        ts.execute(url, user_data);
        TextView unSuccess = findViewById(R.id.attempts);
        try {
            result = ts.get();
            UserResponse ur = g.fromJson(result, UserResponse.class);
            if (ur.getStatus().equals(ServerDescriptor.SUCCESS)) {
                unSuccess.setText("");

                Toast.makeText(this, "Вы вошли", Toast.LENGTH_LONG).show();
                SharedPreferences sPref = getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();
                ed.putString(Config.LOGIN_TEXT, ur.getLogin());
                ed.putInt(Config.ROLE_ID, ur.getRole_id());
                ed.putInt(Config.USER_ID, ur.getId());
                ed.putInt(Config.UNIVERSITY_ID, ur.getUniversity_id());
                ed.putBoolean(Config.IS_KNOWN_BOOL, true);
                ed.commit();
                Intent mStartActivity = new Intent(Dima2.this, MainActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(Dima2.this, mPendingIntentId, mStartActivity,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager) Dima2.this.getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            } else if (ur.getStatus().equals(ServerDescriptor.INTERNET_ERROR)){

                Toast.makeText(this, "Нет доступа к серверу", Toast.LENGTH_LONG).show();
                //unSuccess.setText("Не удается получить доступ к серверу");
            } else {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
                //unSuccess.setText("Не удается получить доступ к серверу");
            }
        } catch (InterruptedException e) {
            unSuccess.setText("error");
            e.printStackTrace();
        } catch (ExecutionException e) {
            unSuccess.setText("error");
            e.printStackTrace();
        }
    }

}
