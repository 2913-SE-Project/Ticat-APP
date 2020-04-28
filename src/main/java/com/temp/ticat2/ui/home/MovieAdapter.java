package com.temp.ticat2.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.notifications.NotificationsFragment;
import com.temp.ticat2.ui.notifications.login;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Movie> mMovieList;
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    private static Connection conn;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String username;
    private boolean flag;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout movieItem;
        ImageView movieImage;
        TextView movieName;
        TextView movieDirct;
        TextView movieDate;
        ImageButton favorite;
        int Mid;

        public ViewHolder (View view){
            super(view);
            movieItem = view.findViewById(R.id.linear_movie_item);
            movieImage = (ImageView) view.findViewById(R.id.poster);
            movieName = (TextView) view.findViewById(R.id.movie_title);
            movieDirct = (TextView) view.findViewById(R.id.movie_dirct);
            movieDate = (TextView) view.findViewById(R.id.movie_date);
            favorite = view.findViewById(R.id.favorite);

            sharedPreferences = view.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            //存储数据时选用对应类型的方法
            username = sharedPreferences.getString("username", "username");

            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(favorite.getContentDescription().equals("heart")){

                        if(!username.equals("username")){
                            putFavorite(Mid,username);
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
                            removeFavorite(Mid,username);
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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"jump!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    // (当前Activity，目标Activity)
                    //intent.setClass(v.getContext(), DetailActivity.class);
                    intent.putExtra("crt_id", Mid);
                    System.out.println("Mid: "+Mid);
                    v.getContext().startActivity(intent);
                    //v.getContext().startActivityForResult(intent, requestCode);
                }
            });
        }

        public static void putFavorite(int mid, String username){
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
                        sql = "insert into likes values("+mid+",\""+username+"\")";
                        System.out.println(sql);
                        Statement statement;
                        ResultSet result;
                        if (conn != null){// connection不为null表示与数据库建立了连接
                            try {
                                statement = conn.createStatement();
                                //result = statement.executeQuery(sql);
                                statement.execute(sql);
                                //result.close();
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

        public static void removeFavorite(int mid, String username){
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
                        sql = "delete from likes where Mid="+mid+" and username='"+username+"'";
                        System.out.println(sql);
                        Statement statement;
                        ResultSet result;
                        if (conn != null){// connection不为null表示与数据库建立了连接
                            try {
                                statement = conn.createStatement();
                                //result = statement.executeQuery(sql);
                                statement.execute(sql);
                                //result.close();
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

    public  MovieAdapter (List <Movie> movieList){
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
        Movie movie = mMovieList.get(position);
        holder.movieImage.setImageResource(movie.getPoster());
        holder.movieName.setText(movie.getName());
        holder.movieDirct.setText("Director:"+movie.getDirector());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(movie.getReleaseDate());
        holder.movieDate.setText(date);
        holder.Mid = movie.getMid();
        isLiked(holder.Mid, username);
        System.out.println("username: "+username);
        if(flag==false){
            holder.favorite.setBackgroundResource(R.drawable.heart);
            holder.favorite.setContentDescription("heart");
        }else{
            holder.favorite.setBackgroundResource(R.drawable.favorite);
            holder.favorite.setContentDescription("favorite");
        }

    }

    private void isLiked(int Mid, String uname){
        flag = false;
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
                    sql = "select * from likes where Mid="+Mid+" and username='"+uname+"'";
                    System.out.println(sql);
                    Statement statement;
                    ResultSet result;
                    if (conn != null){// connection不为null表示与数据库建立了连接
                        try {
                            statement = conn.createStatement();
                            result = statement.executeQuery(sql);
                            while(result.next()){
                                flag = true;
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

    @Override
    public int getItemCount() {
        // 返回RecyclerView的子项数目
        return mMovieList.size();
    }


}
