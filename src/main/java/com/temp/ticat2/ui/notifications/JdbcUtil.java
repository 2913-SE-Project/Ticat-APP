package com.temp.ticat2.ui.notifications;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import static android.content.ContentValues.TAG;

import static android.content.ContentValues.TAG;

public class JdbcUtil {
    public static Connection openConnection(String url, String user, String password) {
        Connection conn = null;
        try {
            System.out.println("试试加载MySQL驱动！");
            final String DRIVER_NAME = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            System.out.println("成功加载MySQL驱动！");
            conn = DriverManager.getConnection(url, user, password);
            Log.i(TAG,"getConnection:Success！");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG,"getConnection:ClassNotFoundException！");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.i(TAG,"getConnection:SQLException！");
        }
        return conn;
    }
}
