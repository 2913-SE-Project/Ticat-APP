package com.temp.ticat2.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Movie> mMovieList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout movieItem;
        ImageView movieImage;
        TextView movieName;
        TextView movieDirct;
        TextView movieDate;

        public ViewHolder (View view){
            super(view);
            movieItem = view.findViewById(R.id.linear_movie_item);
            movieImage = (ImageView) view.findViewById(R.id.poster);
            movieName = (TextView) view.findViewById(R.id.movie_title);
            movieDirct = (TextView) view.findViewById(R.id.movie_dirct);
            movieDate = (TextView) view.findViewById(R.id.movie_date);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"jump!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    // (当前Activity，目标Activity)
                    intent.setClass(v.getContext(), DetailActivity.class);
                    v.getContext().startActivity(intent);
                }
            });
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
    }

    @Override
    public int getItemCount() {
        // 返回RecyclerView的子项数目
        return mMovieList.size();
    }
}
