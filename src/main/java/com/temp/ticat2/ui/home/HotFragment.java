package com.temp.ticat2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class HotFragment extends Fragment {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    public int[] ids = new int[]{
            1,2,3,4,5,6,7,8,9,10
    };

    public int[] posterIds = new int[]{
            R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4, R.drawable.pic_5,
            R.drawable.pic_6, R.drawable.pic_7, R.drawable.pic_8, R.drawable.pic_9, R.drawable.pic_10,
    };

    public String[] mNames = new String[]{
            "name1", "name2", "name3", "name4", "name5",
            "name6", "name7", "name8", "name9", "name10"
    };

    public String[] directors = new String[]{
            "Director","Director","Director","Director","Director",
            "Director","Director","Director","Director","Director"
    };

    /*public String[] mTypes = new String[]{
            "Comedy","Comedy","Comedy","Comedy","Comedy",
            "Comedy","Comedy","Comedy","Comedy","Comedy"
    };

    public String[] languages = new String[]{
            "English", "English", "English", "English", "English",
            "English", "English", "English", "English", "English"
    };

    public String[] countires = new String[]{
            "USA","USA","USA","USA","USA",
            "USA","USA","USA","USA","USA"
    };

    public int[] runningTimes = new int[]{
            120,120,120,120,120,
            120,120,120,120,120
    };

    public String[] dTypes = new String[]{
            "2D","2D","2D","2D","2D",
            "2D","2D","2D","2D","2D"
    };

    public String[] intros = new String[]{
            "Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx",
            "Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx","Movie profile: xxxxxx"
    };
    */

    SimpleDateFormat simFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date d1;
    {
        try {
            d1= simFormat.parse("2020-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    public Date[] releaseDates = new Date[]{
            d1,d1,d1,d1,d1,d1,
            d1,d1,d1,d1,d1
    };


    public List<Movie> movieList = new ArrayList<>();

    private View root;

    private Connection conn;


    public HotFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_hot, container, false);
        initMovies();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());

        recyclerView.setLayoutManager(layoutManager);
        MovieAdapter adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return root;
    }


    public void initMovies(){
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
                    sql = "select * from movie_info";
                    Statement statement;
                    ResultSet result;
                    if (conn != null){// connection不为null表示与数据库建立了连接
                        try {
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            while(result.next()){
                                String Mid = result.getString("Mid");
                                String mName = result.getString("Mname");
                                String director = result.getString("Director");;
                                //String mType = result.getString("MType");;
                                //String language = result.getString("Lanuage");;
                                //String country = result.getString("Country");;
                                //int runningTime = result.getInt("RunningTime");
                                Date releaseDate = result.getDate("ReleaseDate");
                                //String dType = result.getString("Dtype");;
                                //String intro = result.getString("Intro");;
                                int mid = Integer.parseInt(Mid);
                                //System.out.println("准备中的mName："+mName);
                                /*mTypes[mid] = mType;
                                languages[mid] = language;
                                countires[mid] = country;
                                runningTimes[mid] = runningTime;
                                dTypes[mid] = dType;
                                intros[mid] = intro;
                                releaseDates[mid] = releaseDate;*/
                                if(mid<=10){
                                    mNames[mid-1] = mName;
                                    directors[mid-1] = director;
                                    releaseDates[mid-1] = releaseDate;
                                }

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
                    for(int i=0; i<10; i++){
                        Movie m = new Movie(ids[i],mNames[i],directors[i],releaseDates[i],posterIds[i]);
                        //System.out.println("mName:"+m.getName());
                        movieList.add(m);
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
