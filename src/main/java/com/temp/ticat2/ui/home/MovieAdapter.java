package com.temp.ticat2.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Movie> mMovieList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView movieName;

        public ViewHolder (View view){
            super(view);
            movieImage = (ImageView) view.findViewById(R.id.poster);
            movieName = (TextView) view.findViewById(R.id.title);
        }

    }

    public  MovieAdapter (List <Movie> fruitList){
        mMovieList = fruitList;
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
    }

    @Override
    public int getItemCount() {
        // 返回RecyclerView的子项数目
        return mMovieList.size();
    }
}
