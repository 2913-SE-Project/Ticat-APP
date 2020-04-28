package com.temp.ticat2.ui.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
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

    private int crt_id;

    public int screenNum = 0;

    private String[] hallType = new String[]{
            "","","","","","IMAX","IMAX","VIP"
    };

    public List<Screen> screenList = new ArrayList<>();

    private View root;
    public RecyclerView recyclerView;
    public LinearLayoutManager layoutManager;
    public ScreenAdapter adapter;

    private Connection conn;

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_today, container, false);

        initScreens();

        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview2);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ScreenAdapter(screenList);
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return root;
    }

    public void initScreens(){
        screenList.clear();
        final Thread thread = new Thread((Runnable) () -> {
            // TODO Auto-generated method stub
            try {
                // 加载MySQL驱动
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("成功加载驱动！");
                // 连接到数据库
                conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
                String sql;
                Statement statement;
                ResultSet result;

                crt_id = DashboardFragment.crt_id;
                int time = 120;
                Timestamp dateTime;
                String language = "English";
                String dType = "2D";
                double price = 4.99;
                int hallId = 3;

                if (conn != null){// connection不为null表示与数据库建立了连接
                    System.out.println("成功连接today数据库！");
                    try {
                        // 获取当前影片的runningTime，dType，language
                        sql = "select * from movie_info where Mid="+crt_id;
                        statement = conn.createStatement();
                        result = statement.executeQuery(sql);
                        while(result.next()){
                            time = result.getInt("RunningTime");
                            language = result.getString("Language");
                            dType = result.getString("Dtype");
                        }

                        // 获取该影片下的排片以及每场的dateTime，price，hall
                        sql = "select * from screenings where Mid="+crt_id+" and DATE_FORMAT(DateTime,'%Y%m%d') = '20200305'";
                        System.out.println(sql);
                        statement = conn.createStatement();
                        result = statement.executeQuery(sql);
                        while(result.next()){
                            dateTime = result.getTimestamp("DateTime");
                            price = result.getDouble("Price");
                            hallId = result.getInt("Hid");
                            System.out.println(price+" "+hallId);
                            // 新建screen对象并加入队列中
                            Screen scr = new Screen(time,dateTime,language,dType,hallId,hallType[hallId-1],price);
                            screenList.add(scr);
                        }
                        adapter.notifyDataSetChanged();
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
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
