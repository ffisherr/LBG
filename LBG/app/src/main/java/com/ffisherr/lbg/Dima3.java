package com.ffisherr.lbg;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class Dima3 extends AppCompatActivity {

    public static final String PREFERENCE_NAME = "my_settings";
    public static final String ROLE_TEXT       = "role_text";
    public static final String LOGIN_TEXT      = "login_text";
    public static final String IS_KNOWN_BOOL   = "is_known_bool";

    String[] names = { "МГТУ", "МГУ", "МАИ", "МЭИ" };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dima3);

        // находим список
        Spinner University = (Spinner) findViewById(R.id.University);

        // создаем адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, names);

        // присваиваем адаптер списку
        University.setAdapter(adapter);


    }

    public void onClickRegister(View view) {
        UserResponse user = new UserResponse();

        EditText editText = findViewById(R.id.edit_password_reg);
        EditText repeatPassword = findViewById(R.id.edit_repeatPassword);

        TextView infText = findViewById(R.id.info);

        String p1 = editText.getText().toString();
        String p2 = repeatPassword.getText().toString();

        if (p1.equals(p2)) {

            editText = findViewById(R.id.edit_FirstName);
            user.setFirstName(editText.getText().toString());

            editText = findViewById(R.id.edit_surname);
            user.setSurName(editText.getText().toString());

            editText = findViewById(R.id.edit_SecondName);
            user.setSecondName(editText.getText().toString());

            user.setPassw_hash(p1);
            user.setRole_id(1);

            editText = findViewById(R.id.edit_login);
            user.setLogin(editText.getText().toString());

            String result;
            TaskPostServer ts = new TaskPostServer();
            String url = ServerDescriptor.serverIpAdress + "/user_add";

            Gson g = new Gson();
            String user_data = g.toJson(user);
            ts.execute(url, user_data);

            try {
                result = ts.get();
                UserResponse ur = g.fromJson(result, UserResponse.class);
                if (ur.getStatus().equals(ServerDescriptor.SUCCESS)) {
                    infText.setText("");
                    
                    Toast.makeText(this, "Вы зарегестрированы", Toast.LENGTH_LONG).show();
                    SharedPreferences sPref = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor ed = sPref.edit();
                    ed.putString(LOGIN_TEXT, ur.getLogin());
                    ed.putString(ROLE_TEXT, ur.getRole_id().toString());
                    ed.putBoolean(IS_KNOWN_BOOL, true);
                    ed.commit();

                    Intent mStartActivity = new Intent(Dima3.this, MainActivity.class);
                    int mPendingIntentId = 123456;
                    PendingIntent mPendingIntent = PendingIntent.getActivity(Dima3.this, mPendingIntentId, mStartActivity,
                            PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager mgr = (AlarmManager) Dima3.this.getSystemService(Context.ALARM_SERVICE);
                    mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                    System.exit(0);

                } else if (ur.getStatus().equals(ServerDescriptor.LOGIN_ALREADY_EXISTS_ERROR)){
                    Toast.makeText(this, "Введенный логин уже используется", Toast.LENGTH_LONG).show();
                    //infText.setText("Введенный логин уже используется");
                } else if (ur.getStatus().equals(ServerDescriptor.INTERNET_ERROR)){
                    Toast.makeText(this, "Не удается получить доступ к серверу", Toast.LENGTH_LONG).show();
                    //infText.setText("Не удается получить доступ к серверу");
                } else {
                    Toast.makeText(this, "Не удается получить доступ к серверу", Toast.LENGTH_LONG).show();
                    //infText.setText("Не удается получить доступ к серверу");
                }
            } catch (InterruptedException e) {
                infText.setText("error");
                e.printStackTrace();
            } catch (ExecutionException e) {
                infText.setText("error");
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
        }
    }
}