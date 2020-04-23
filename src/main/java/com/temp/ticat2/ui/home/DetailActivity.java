package com.temp.ticat2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.temp.ticat2.R;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    private Connection conn;
    private int crt_id;

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
        initDetail();
        //btnBack = this.findViewById(R.id.btn_back);
        //btnBack.setOnClickListener(this::onClick);
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
