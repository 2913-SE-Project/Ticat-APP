package com.temp.ticat2.ui.home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // 创建连接
    public static Connection openConnection(String url, String user,
                                            String password) {
        Connection conn = null;
        try {
            // 加载MySQL驱动
            // com.mysql.cj.jdbc.Driver
            // org.gjt.mm.mysql.Drivers
            // libs/com.mysql.cj.jdbc.Driver
            final String DRIVER_NAME = "new com.mysql.cj.jdbc.Driver";
            Class.forName(DRIVER_NAME);
            System.out.println("成功加载MySQL驱动！");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            conn = null;
        } catch (SQLException e) {
            conn = null;
        }
        return conn;
    }

    // 数据库查询
    public static void query(Connection conn, String sql) {

        if (conn ==  null) {
            return;
        }

        Statement statement = null;
        ResultSet result = null;

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            if (result != null && result.first()) {
                int idColumnIndex = result.findColumn("id");
                int nameColumnIndex = result.findColumn("name");
                System.out.println("id\t\t" + "name");
                while (!result.isAfterLast()) {
                    System.out.print(result.getString(idColumnIndex) + "\t\t");
                    System.out.println(result.getString(nameColumnIndex));
                    result.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {

            }
        }
    }

    // 数据库语句执行
    public static boolean execSQL(Connection conn, String sql) {
        boolean execResult = false;
        if (conn == null) {
            return execResult;
        }

        Statement statement = null;

        try {
            statement = conn.createStatement();
            if (statement != null) {
                execResult = statement.execute(sql);
            }
        } catch (SQLException e) {
            execResult = false;
        }

        return execResult;
    }

    public static void releaseResource(ResultSet result, Statement statement, Connection connection){
        try {
            if(result != null){
                result.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(statement != null){
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] queryMovies(Connection conn, String sql) {

        if (conn ==  null) {
            return null;
        }

        Statement statement = null;
        ResultSet result = null;
        String[] mNames = new String[10];

        try {
            statement = conn.createStatement();
            result = statement.executeQuery(sql);
            while(result.next()){
                /* search */
                String Mid = result.getString("Mid");
                int mid = Integer.parseInt(Mid);
                String mName = result.getString("Mname");
                mNames[mid-1] = mName;
            }
            return mNames;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (result != null) {
                    result.close();
                    result = null;
                }
                if (statement != null) {
                    statement.close();
                    statement = null;
                }

            } catch (SQLException sqle) {
                return null;
            }
        }
    }


}
