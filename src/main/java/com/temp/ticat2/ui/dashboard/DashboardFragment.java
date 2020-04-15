package com.temp.ticat2.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.home.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DashboardFragment extends Fragment {

    public static final String REMOTE_IP = "101.200.167.221:3306";
    public static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    public static final String USER = "Ticat";
    public static final String PASSWORD = "yjx3THEm5YTFnswG";

    private DashboardViewModel dashboardViewModel;
    private View root;
    private Connection conn;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private Button left;
    private Button right;
    private ImageView poster;
    private TextView title;
    private TextView info;
    private TodayFragment tf = new TodayFragment();
    private SecondFragment sf = new SecondFragment();

    ArrayList fragmentList = new ArrayList<Fragment>();
    String[] temp = {"Today\n03-05","Tomorrow\n03-06"};

    public int[] posterIds = new int[]{
            R.drawable.pic_1, R.drawable.pic_2, R.drawable.pic_3, R.drawable.pic_4, R.drawable.pic_5,
            R.drawable.pic_6, R.drawable.pic_7, R.drawable.pic_8, R.drawable.pic_9, R.drawable.pic_10,
    };
    public static int crt_id;

    public DashboardFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tabLayout = root.findViewById(R.id.tabLayout1);
        viewpager = root.findViewById(R.id.viewpager1);
        left = root.findViewById(R.id.left);
        right = root.findViewById(R.id.right);
        poster = root.findViewById(R.id.scr_poster);
        title = root.findViewById(R.id.src_title);
        info = root.findViewById(R.id.scr_info);
        left.setOnClickListener(this::onClick);
        right.setOnClickListener(this::onClick);
        initInfo();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment中嵌套fragment, Manager需要用(getChildFragmentManager())
        FragmentAdapter mPagerAdapter = new FragmentAdapter(getChildFragmentManager());
        initFragment();
        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(mPagerAdapter);
    }

    private void initFragment() {
        fragmentList.add(tf);
        fragmentList.add(sf);
    }

    public class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        //返回tablayout的标题文字;
        @Override
        public CharSequence getPageTitle(int position) {
            return temp[position];
        }
    }

    public void initInfo(){
        crt_id = 1;
        poster.setImageResource(posterIds[crt_id-1]);
        changeInfo(crt_id);
    }

    //@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left:
                onClickButton1(view);
                break;
            case R.id.right:
                onClickButton2(view);
                break;
            default:
                break;
        }
    }

    private void onClickButton1(View view) {
        //处理逻辑
        if(crt_id>1 && crt_id<=posterIds.length){
            crt_id--;
            poster.setImageResource(posterIds[crt_id-1]);
            changeInfo(crt_id);
            tf.initScreens();
            sf.initScreens();
        }
        else{
            Toast.makeText(getActivity(),"Almost the first movie",Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickButton2(View view) {
        //处理逻辑
        if(crt_id>=1 && crt_id<posterIds.length){
            crt_id++;
            poster.setImageResource(posterIds[crt_id-1]);
            changeInfo(crt_id);
            tf.initScreens();
            sf.initScreens();
        }
        else{
            Toast.makeText(getActivity(),"Almost the last movie",Toast.LENGTH_SHORT).show();
        }
    }

    // change the info below the movie poster
    private void changeInfo(int crt_id){
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
                    sql = "select * from movie_info where Mid="+crt_id;
                    Statement statement;
                    ResultSet result;
                    String mName = "Movie Title";
                    String mType = "Comedy";
                    int runningTime = 111;
                    if (conn != null){// connection不为null表示与数据库建立了连接
                        try {
                            System.out.println("成功连接到数据库！");
                            System.out.println("sql:"+sql);
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);

                            while(result.next()){
                                mName = result.getString("Mname");
                                runningTime = result.getInt("RunningTime");
                                mType = result.getString("MType");
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
                    title.setText(mName);
                    info.setText(runningTime+" mins | "+mType);
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