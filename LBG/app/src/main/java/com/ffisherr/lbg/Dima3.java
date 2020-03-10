package com.ffisherr.lbg;


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
                } else if (ur.getStatus().equals(ServerDescriptor.LOGIN_ALREADY_EXISTS_ERROR)){
                    infText.setText("Введенный логин уже используется");
                } else if (ur.getStatus().equals(ServerDescriptor.INTERNET_ERROR)){
                    infText.setText("Не удается получить доступ к серверу");
                } else {
                    infText.setText("Не удается получить доступ к серверу");
                }
            } catch (InterruptedException e) {
                infText.setText("error");
                e.printStackTrace();
            } catch (ExecutionException e) {
                infText.setText("error");
                e.printStackTrace();
            }

        } else {
            infText.setText("Пароли не совпадают");
        }
    }
}