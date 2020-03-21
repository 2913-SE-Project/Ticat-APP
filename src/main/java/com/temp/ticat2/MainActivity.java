package com.temp.ticat2;

import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    // Activity代表用户界面
    // 可以创建一个窗口并在窗口上加载用户界面（UI），以实现交互


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 当activity创建时会自动调用onCreate，在此做初始化的操作
        super.onCreate(savedInstanceState);

        // 加载界面
        setContentView(R.layout.activity_main);

        // 引用底部导航栏控件
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // 通过id找到对应Java对象，获取控件，findViewById(R.id.name)

        // 工具条的配置
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        // 传入fragment的id
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}
