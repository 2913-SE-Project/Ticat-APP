package com.temp.ticat2.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.home.DetailActivity;
import com.temp.ticat2.ui.home.Movie;

import java.sql.*;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static String username;
    public String password;
    private View root;
    private Button btnLogin;
    private Button btnLogout;
    private Button btnFavorite;
    private TextView usertext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);

        getAccount();
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        if(username.equals("username") || password.equals("password")){
            root = inflater.inflate(R.layout.fragment_account, container, false);
            btnLogin = root.findViewById(R.id.btn_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), login.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
        else{
            root = inflater.inflate(R.layout.fragment_notifications, container, false);
            usertext = root.findViewById(R.id.username);
            usertext.setText(username);
            btnFavorite = root.findViewById(R.id.favorite);
            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), FavoriteActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
            btnLogout = root.findViewById(R.id.log_out);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAccount("username", "password");
                    Intent intent = new Intent(v.getContext(), login.class);
                    v.getContext().startActivity(intent);
                }
            });
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void setAccount(String uname, String pword){
        sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //存储数据时选用对应类型的方法
        editor.putString("username",uname);
        editor.putString("password",pword);
        //提交保存数据
        editor.commit();
    }

    private void getAccount(){
        sharedPreferences = this.getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //存储数据时选用对应类型的方法
        username = sharedPreferences.getString("username", "username");
        password = sharedPreferences.getString("password", "password");
    }

}