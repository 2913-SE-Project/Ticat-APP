package com.temp.ticat2.ui.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.MutableLiveData;
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

public class TodayFragment extends Fragment {
    private String URL = DashboardFragment.URL;
    private String USER = DashboardFragment.USER;
    private String PASSWORD = DashboardFragment.PASSWORD;

    public int screenNum = 0;

    public List<Screen> screenList = new ArrayList<>();

    private View root;

    private Connection conn;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_today, container, false);

        initScreens();

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ScreenAdapter adapter = new ScreenAdapter(screenList);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return root;
    }

    public void initScreens(){
        /*final Thread thread = new Thread((Runnable) () -> {
            // TODO Auto-generated method stub
            try {
                // 加载MySQL驱动
                Class.forName("com.mysql.jdbc.Driver");
                // 连接到数据库
                conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
                String sql;
                sql = "select * from screenings";
                Statement statement;
                ResultSet result;
                if (conn != null){// connection不为null表示与数据库建立了连接
                    try {
                        statement = conn.createStatement();
                        result = statement.executeQuery(sql);
                        while(result.next()){
                            String Mid = result.getString("Mid");
                            String mName = result.getString("Mname");
                            String director = result.getString("Director");
                            Date releaseDate = result.getDate("ReleaseDate");

                            int mid = Integer.parseInt(Mid);
                            mNames[mid-1] = mName;
                            directors[mid-1] = director;
                            releaseDates[mid-1] = releaseDate;
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
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Screen scr = new Screen("Enilish","3D","Hall 3",4.99);
        screenList.add(scr);
        screenList.add(scr);
        screenList.add(scr);
    }

}
