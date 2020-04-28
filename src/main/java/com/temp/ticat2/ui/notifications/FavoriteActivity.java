package com.temp.ticat2.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.NoCopySpan;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.home.Movie;
import com.temp.ticat2.ui.home.MovieAdapter;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static String username;


    public int[] posterIds = new int[]{
            R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4, R.drawable.pic_5,
            R.drawable.pic_6, R.drawable.pic_7, R.drawable.pic_8, R.drawable.pic_9, R.drawable.pic_10,
    };


    SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1;
    {
        try {
            d1= simFormat.parse("2020-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<Movie> movieList = new ArrayList<>();

    private Connection conn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //存储数据时选用对应类型的方法
        username = sharedPreferences.getString("username", "username");
        initMovies();

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview3);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MovieAdapter adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);
    }

    // get mid of favorited movies
    public void initMovies() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    // 加载MySQL驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    // 连接到数据库
                    conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
                    String sql;
                    sql = "select * from movie_info a inner join likes b on a.Mid = b.Mid where username='"+username+"'";
                    Statement statement;
                    ResultSet result;
                    if (conn != null) {// connection不为null表示与数据库建立了连接
                        try {
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            /*while (result.next()) {
                                String Mid = result.getString("Mid");
                                int mid = Integer.parseInt(Mid);
                                movieIds[mid] = 1;
                            }*/
                            while(result.next()){
                                String Mid = result.getString("Mid");
                                int mid = Integer.parseInt(Mid);
                                String mName = result.getString("Mname");
                                String director = result.getString("Director");
                                Date releaseDate = result.getDate("ReleaseDate");
                                if (mid <= 10) {
                                    Movie m = new Movie(mid, mName, director, releaseDate, posterIds[mid-1]);
                                    //System.out.println("mName:"+m.getName());
                                    movieList.add(m);
                                }
                            }
                            result.close();
                            statement.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("失败了！空的！");
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("连接数据库失败！");
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
