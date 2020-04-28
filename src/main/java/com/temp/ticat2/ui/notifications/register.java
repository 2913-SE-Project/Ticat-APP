package com.temp.ticat2.ui.notifications;

import android.content.Intent;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.temp.ticat2.R;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn_to_login = findViewById(R.id.goToLoginButton);
        Button btn_register = findViewById(R.id.registerButton);
        final EditText register_usr = findViewById(R.id.registerUsername);
        final EditText register_pwd = findViewById(R.id.registerPassword);
        final EditText register_pwd_confirm = findViewById(R.id.registerPasswordConfirm);
        final EditText register_email = findViewById(R.id.registerEmail);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str_usr = register_usr.getText().toString().trim();
                        String str_pwd = register_pwd.getText().toString().trim();
                        String str_pwd_confirm = register_pwd_confirm.getText().toString().trim();
                        String str_email = register_email.getText().toString().trim();
                        if (str_usr.equals("") || str_pwd.equals("") || str_pwd_confirm.equals("") || str_email.equals("")) {
                            Looper.prepare();
                            Toast.makeText(register.this, "Input cannot be empty!", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        if (!str_pwd.equals(str_pwd_confirm)) {
                            Looper.prepare();
                            Toast.makeText(register.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                        UserDao ud = new UserDao();
                        boolean result =ud.register(str_usr,str_pwd,str_email);
                        if (!result){
                            Looper.prepare();
                            Toast toast = Toast.makeText(register.this,"Register Success！",Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(register.this, login.class);
                            startActivity(intent);
                            Looper.loop();
                        }
                        else{
                            Looper.prepare();
                            Toast toast = Toast.makeText(register.this,"Register Fail！",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });

        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }
}
