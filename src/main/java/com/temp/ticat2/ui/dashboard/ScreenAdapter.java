package com.temp.ticat2.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;
import com.temp.ticat2.ui.home.Movie;
import com.temp.ticat2.ui.home.MovieAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScreenAdapter extends RecyclerView.Adapter<ScreenAdapter.ViewHolder> {

    private List<Screen> mScreenList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        //TextView movieTimeS;
        //TextView movieTimeF;
        TextView language;
        TextView hallName;
        TextView price;

        public ViewHolder (View view){
            super(view);
            //movieTimeS = (TextView) view.findViewById(R.id.timeS);
            //movieTimeF = (TextView) view.findViewById(R.id.timeF);
            language = (TextView) view.findViewById(R.id.language);
            hallName = (TextView) view.findViewById(R.id.hall_name);
            price = (TextView) view.findViewById(R.id.price);
        }

    }

    public  ScreenAdapter (List<Screen> screenList){
        mScreenList = screenList;
    }

    @NonNull
    @Override
    public ScreenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 用于创建ViewHolder实例,并把加载的布局传入到构造函数去,再把ViewHolder实例返回。
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_item,parent,false);
        ScreenAdapter.ViewHolder holder = new ScreenAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScreenAdapter.ViewHolder holder, int position) {
        // 用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行
        Screen screen = mScreenList.get(position);
        //SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //String times = formatter.format(screen.getTime());
        //Calendar c = Calendar.getInstance();
        //c.setTime(screen.getTime());
        //c.add(Calendar.MINUTE,screen.getDuration());
        //String timef = formatter.format(c.getTime());
        //holder.movieTimeS.setText("times");
        //holder.movieTimeF.setText("Finish at "+"timef");
        holder.language.setText(screen.getLanguage()+" "+screen.getDType());
        holder.hallName.setText(screen.getHall());
        holder.price.setText("￡"+screen.getPrice());
    }

    @Override
    public int getItemCount() {
        // 返回RecyclerView的子项数目
        return mScreenList.size();
    }
}
