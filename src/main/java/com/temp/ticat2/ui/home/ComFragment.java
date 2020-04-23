package com.temp.ticat2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.temp.ticat2.R;

import java.util.Arrays;

public class ComFragment extends Fragment {
    private static final String REMOTE_IP = "101.200.167.221:3306";
    private static final String URL = "jdbc:mysql://" + REMOTE_IP + "/Ticat";
    private static final String USER = "Ticat";
    private static final String PASSWORD = "yjx3THEm5YTFnswG";

    HotFragment hf = new HotFragment();
    public String[] mNames = hf.mNames;
    private SearchView searchView;
    private ListView listView;

    private View root;

    public ComFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_coming, container, false);
        listView = (ListView)root.findViewById(R.id.listView);
        hf.initMovies();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,mNames);
        listView.setAdapter(adapter);
        //为ListView启动过滤
        listView.setTextFilterEnabled(true);
        searchView = (SearchView)root.findViewById(R.id.searchView);
        //设置SearchView自动缩小为图标
        searchView.setIconifiedByDefault(false);//设为true则搜索栏 缩小成俄日一个图标点击展开
        //设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);
        //设置默认提示文字
        searchView.setQueryHint("Search for movies here...");
        //配置监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //点击搜索按钮时触发
            @Override
            public boolean onQueryTextSubmit(String query) {
                //此处添加查询开始后的具体时间和方法
                Toast.makeText(getActivity(),"you choose:" + query,Toast.LENGTH_SHORT).show();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //如果newText长度不为0
                if (TextUtils.isEmpty(newText)){
                    listView.clearTextFilter();
                }else{
                    //listView.setFilterText(newText);
                    adapter.getFilter().filter(newText.toString());//替换成本句后消失黑框！！！
                }
                return true;
            }
        });
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object string = adapter.getItem(position);
                int Mid = searchId((String)string);
                if(Mid < 0){
                    Mid = 1;
                }
                searchView.setQuery(string.toString(),true);
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                // (当前Activity，目标Activity)
                //intent.setClass(v.getContext(), DetailActivity.class);
                intent.putExtra("crt_id", Mid+1);
                System.out.println("Mid: "+Mid+1);
                view.getContext().startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return root;
    }

    private int searchId(String s){
        for(int i=0; i<mNames.length; i++){
            if (mNames[i].equals(s)){
                return i;
            }
        }
        return 0;
    }

}
