package com.temp.ticat2.ui.notifications;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static android.content.ContentValues.TAG;

public class UserDao {
    //第一个参数为数据库名称，第二个参数为数据库账号 第三个参数为数据库密码
    Connection conn = JdbcUtil.openConnection("jdbc:mysql://101.200.167.221:3306/Ticat","Ticat","yjx3THEm5YTFnswG");
    //注册
    public  boolean register(String name,String password, String email){
        if (conn==null){
            Log.i(TAG,"register:conn is null");
            return false;
        }else {
            //进行数据库操作
            String sql = "insert into users(Username,Password,Email) values(?,?,?)";
            try {
                PreparedStatement pre = conn.prepareStatement(sql);
                pre.setString(1,name);
                pre.setString(2,password);
                pre.setString(3,email);
                return pre.execute();
            } catch (SQLException e) {
                Log.i(TAG,"Register:SQLException！");
                return false;
            }finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    Log.i(TAG,"Register:SQLException！");
                    e.printStackTrace();
                }
            }
        }
    }
    //登录
    public boolean login(String name,String password){
        if (conn==null){
            Log.i(TAG,"login:conn is null");
            return false;
        }else {
            String sql = "select * from users where Username=? and Password=?";
            try {
                PreparedStatement pres = conn.prepareStatement(sql);
                pres.setString(1,name);
                pres.setString(2,password);
                ResultSet res = pres.executeQuery();
                boolean t = res.next();
                return t;
            } catch (SQLException e) {
                Log.i(TAG,"login:SQLException！");
                return false;
            }

        }
    }
}
