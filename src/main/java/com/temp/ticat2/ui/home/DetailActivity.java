package com.temp.ticat2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.notifications.login;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String username;

    private Connection conn;
    private int crt_id;
    private boolean flag;

    public int[] posterIds = new int[]{
            R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4, R.drawable.pic_5,
            R.drawable.pic_6, R.drawable.pic_7, R.drawable.pic_8, R.drawable.pic_9, R.drawable.pic_10,
    };

    View root;
    ImageView poster;
    TextView title;
    TextView type;
    TextView release;
    TextView director;
    TextView actor;
    TextView info;
    ImageButton favorite;


    //Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        Intent intent = getIntent();
        crt_id = intent.getIntExtra("crt_id",4);

        setContentView(R.layout.activity_detail);
        poster = this.findViewById(R.id.d_poster);
        title = this.findViewById(R.id.d_title);
        type = this.findViewById(R.id.d_time_type);
        release = this.findViewById(R.id.d_release);
        director = this.findViewById(R.id.d_director);
        actor = this.findViewById(R.id.d_actor);
        info = this.findViewById(R.id.d_info);
        favorite = this.findViewById(R.id.btn_d_heart);

        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //存储数据时选用对应类型的方法
        username = sharedPreferences.getString("username", "username");

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(favorite.getContentDescription().equals("heart")){

                    if(!username.equals("username")){
                        MovieAdapter.ViewHolder.putFavorite(crt_id,username);
                        favorite.setBackgroundResource(R.drawable.favorite);
                        favorite.setContentDescription("favorite");
                    }
                    else{
                        Intent intent = new Intent(v.getContext(), login.class);
                        v.getContext().startActivity(intent);
                    }
                }
                else{
                    if(!username.equals("username")){
                        MovieAdapter.ViewHolder.removeFavorite(crt_id,username);
                        favorite.setBackgroundResource(R.drawable.heart);
                        favorite.setContentDescription("heart");
                    }
                    else{
                        Intent intent = new Intent(v.getContext(), login.class);
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

        initDetail();
    }

    private void initDetail(){
        System.out.println("crt_id: "+crt_id);
        poster.setImageResource(posterIds[crt_id-1]);
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
                    Statement statement;
                    ResultSet result;
                    if (conn != null){// connection不为null表示与数据库建立了连接
                        try {
                            sql = "select * from movie_info where Mid="+crt_id;
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            while(result.next()){
                                String mName = result.getString("Mname");
                                String mdirector = result.getString("Director");
                                Date mreleaseDate = result.getDate("ReleaseDate");
                                String mintro = result.getString("Intro");
                                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                                String mrelease = sdf.format(mreleaseDate);
                                title.setText(mName);
                                director.setText("Director: "+mdirector);
                                release.setText("Release: "+mrelease);
                                info.setText(mintro);
                            }
                            sql = "select * from played_by where Mid="+crt_id;
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            String actors = "Actors: ";
                            while(result.next()){
                                String mactor = result.getString("Actor");
                                actors = actors + mactor + ",\n";
                            }
                            actor.setText(actors);
                            sql = "select * from likes where Mid="+crt_id+" and username='"+username+"'";
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            flag = false;
                            while(result.next()){
                                favorite.setBackgroundResource(R.drawable.favorite);
                                favorite.setContentDescription("favorite");
                            }
                            result.close();
                            statement.close();
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
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
