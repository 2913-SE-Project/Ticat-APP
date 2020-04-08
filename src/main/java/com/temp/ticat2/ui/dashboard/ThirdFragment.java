package com.temp.ticat2.ui.dashboard;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.temp.ticat2.R;

import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment {

    private String URL = DashboardFragment.URL;
    private String USER = DashboardFragment.USER;
    private String PASSWORD = DashboardFragment.PASSWORD;

    public int screenNum = 0;

    public List<Screen> screenList = new ArrayList<>();

    private View root;

    public ThirdFragment() {
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
        /*Date time = null;
        try {
            time = new SimpleDateFormat("HH:mm:ss").parse("10:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        Screen scr = new Screen("Enilish","3D","Hall 3",4.99);
        screenList.add(scr);
        screenList.add(scr);
        screenList.add(scr);
    }
}
