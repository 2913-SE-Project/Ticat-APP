package com.temp.ticat2.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.temp.ticat2.R;

public class DetailActivity extends AppCompatActivity {

    //Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //btnBack = this.findViewById(R.id.btn_back);
        //btnBack.setOnClickListener(this::onClick);
    }

    //@Override
    /*public void onClick(View view) {
        this.finish();
    }*/
}
