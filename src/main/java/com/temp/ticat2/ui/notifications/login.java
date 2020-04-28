package com.temp.ticat2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Looper;
import android.os.Trace;
import android.util.Log;
import com.temp.ticat2.MainActivity;
import com.temp.ticat2.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_login = findViewById(R.id.loginButton);
        Button btn_to_register = findViewById(R.id.goToRegisterButton);
        final EditText login_usr = findViewById(R.id.loginUsername);
        final EditText login_pwd = findViewById(R.id.loginPassword);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str_usr = login_usr.getText().toString();
                        String str_pwd = login_pwd.getText().toString();

                        if (str_usr.equals("") || str_pwd.equals("")) {
                            Looper.prepare();
                            Toast.makeText(login.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        UserDao ud = new UserDao();
                        Boolean result = ud.login(str_usr, str_pwd);
                        if (result) {
                            Looper.prepare();
                            Toast.makeText(login.this, "Login Success!", Toast.LENGTH_SHORT).show();
                            sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            //存储数据时选用对应类型的方法
                            editor.putString("username",str_usr);
                            editor.putString("password",str_pwd);
                            //提交保存数据
                            editor.commit();
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            Looper.loop();
                        }
                        else {
                            Looper.prepare();
                            Toast.makeText(login.this, "Login Fail!", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });

        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });
    }
}
