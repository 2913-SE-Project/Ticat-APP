package com.temp.ticat2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotFragment extends Fragment {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    // jdbc:mysql://101.200.167.221:3306/Ticat
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    public int[] posterIds = new int[]{
            R.drawable.pic_1,
            R.drawable.pic_2,
            R.drawable.pic_3,
            R.drawable.pic_4,
            R.drawable.pic_5,
            R.drawable.pic_6,
            R.drawable.pic_7,
            R.drawable.pic_8,
            R.drawable.pic_9,
            R.drawable.pic_10,
    };
    /*public String[] mNames = new String[11];*/
    public String[] mNames = new String[]{
            "name0",
            "name1",
            "name2",
            "name3",
            "name4",
            "name5",
            "name6",
            "name7",
            "name8",
            "name9",
            "name10"
    };
    private List<Movie> movieList = new ArrayList<>();

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


    private void initMovies(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("成功加载MySQL驱动！");
                    conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("成功连接到数据库！");
                    String sql;
                    sql = "select * from movie_info";
                    Statement statement;
                    ResultSet result;
                    if (conn != null){// connection不为null表示与数据库建立了连接
                        try {
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            while(result.next()){
                                String mName = result.getString("Mname");
                                String Mid = result.getString("Mid");
                                int mid = Integer.parseInt(Mid);
                                //System.out.println("准备中的mName："+mName);
                                mNames[mid] = mName;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }else{
                        System.out.println("失败了！空的！");
                    }
                    for(int i=0; i<10; i++){
                        Movie m = new Movie(mNames[i+1], posterIds[i]);
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
