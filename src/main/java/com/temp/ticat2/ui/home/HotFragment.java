package com.temp.ticat2.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
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
import java.util.HashMap;
import java.util.List;

public class HotFragment extends Fragment {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat?serverTimezone=Shanghai&?useUnicode=true&characterEncoding=utf8&useSSL=false";
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
    public String[] mNames = new String[10];
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
        /*RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        MovieAdapter adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);*/
        // Inflate the layout for this fragment
        return root;
    }

    private void initMovies(){
        new Thread(){
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    conn = Util.openConnection(URL, USER, PASSWORD);
                    System.out.println("成功连接到数据库！");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    System.out.println("连接数据库失败！");
                    e.printStackTrace();
                }
            }
        }.start();

        /*String sql;
        sql = "SELECT * From movie_info";
        mNames = Util.queryMovies(conn, sql);

        for(int i=0; i<10; i++){
            Movie m = new Movie(mNames[i], posterIds[i]);
            movieList.add(m);
        }*/


    }
}
