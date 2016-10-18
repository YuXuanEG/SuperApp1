package com.example.edwardgu.superapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwardgu.superapp.adapters.CommonFragmentAdapter;
import com.example.edwardgu.superapp.fragments.BaseFragment;
import com.example.edwardgu.superapp.fragments.CategoryFragment;
import com.example.edwardgu.superapp.fragments.DiscoveryFragment;
import com.example.edwardgu.superapp.fragments.FollowFragment;
import com.example.edwardgu.superapp.fragments.LiveFragment;
import com.example.edwardgu.superapp.fragments.MovieFragment;
import com.example.edwardgu.superapp.fragments.RecommendFragment;
import com.example.edwardgu.superapp.login.LoginActivity;
import com.example.edwardgu.superapp.login.model.UserModel;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private TextView mHTxt;
    private ImageView mHImg;
    private NavigationView mNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注意设置状态栏要写在setContentView(R.layout.activity_main);之前
        //设置状态栏为透明的
        final int sdk = Build.VERSION.SDK_INT;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        if (sdk >= Build.VERSION_CODES.KITKAT) {
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            // 设置透明状态栏
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                window.setAttributes(params);

                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintResource(R.color.colorAccent);
            }
//
////            //实现全屏
////            window.getDecorView().setSystemUiVisibility(
////                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


        setContentView(R.layout.activity_main);

        //设置是否捕捉未捕获的异常
        MobclickAgent.setCatchUncaughtExceptions(true);

        //demo   要改app:headerLayout="@layout/header_main"
//        mNavigationView=(NavigationView)findViewById(R.id.main_navigation_view);
//        ImageView imageView = (ImageView) mNavigationView.findViewById(R.id.main_header_icon);
//        imageView=(ImageView)mNavigationView.getHeaderView(0).findViewById(R.id.main_header_icon);
//        imageView.setOnClickListener(this);
//------------------------------------------------------------------------------------


//        init();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.main_drawer_layout);

        // TODO: 2016/10/13 ActionBarDrawerToggle抽屉的触发器，不会用
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this,
//                mDrawerLayout,
//                R.mipmap.ic_launcher,
//                R.string.navigation_drawer_open,
//                R.string.navigation_drawer_close);
//        mDrawerLayout.setDrawerListener(toggle);
//        toggle.syncState();



        NavigationView navigationView =(NavigationView)findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewPager pager= (ViewPager) findViewById(R.id.main_pager);
        if (pager != null) {
            ArrayList<BaseFragment> fragments = new ArrayList<>();
            fragments.add(new LiveFragment());
            fragments.add(new RecommendFragment());
            fragments.add(new MovieFragment());
            fragments.add(new CategoryFragment());
            fragments.add(new FollowFragment());
            fragments.add(new DiscoveryFragment());

            CommonFragmentAdapter adapter = new CommonFragmentAdapter(
                    getSupportFragmentManager(),
                    fragments
            );
            pager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
            tabLayout.setupWithViewPager(pager);
        }

        Intent intent = getIntent();
        if (intent != null) {
            UserModel user =  intent.getParcelableExtra("user");
            if (user != null) {
                 String name = user.getName();
            }
        }


//        TelephonyManager manager=(TelephonyManager)
//                getSystemService(TELEPHONY_SERVICE);
//        //IMEI
//        String deviceId = manager.getDeviceId();
//
//        //这个方法可以在线程意外终止的情况下，进行处理，接口回掉
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread t, Throwable e) {
//
//            }
//        });


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(this, "onNewIntent", Toast.LENGTH_SHORT).show();

        UserModel user = intent.getParcelableExtra("user");
        String name = user.getName();

        mHTxt = (TextView) findViewById(R.id.main_h_fragment_icon_txt);
        mHImg = (ImageView) findViewById(R.id.main_h_fragment_icon);
        if (mHImg != null) {

        }
        if (mHTxt != null) {
            mHTxt.setText(name);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("main");
    }

    public void onPause() {
        MobclickAgent.onPageEnd("main");
        MobclickAgent.onPause(this);
        super.onPause();
    }

    public void init(){
        // TODO: 2016/10/13 找不到对应头像fragment的控件
        mHTxt = (TextView) findViewById(R.id.main_h_fragment_icon_txt);
        mHImg = (ImageView) findViewById(R.id.main_h_fragment_icon);
//        mHTxt = (TextView) findViewById(R.id.main_header_icon_txt);
//        mHImg = (ImageView) findViewById(R.id.main_header_icon);
    }





    //NavigationView条目点击的监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_main_houepage:
                // TODO: 2016/10/9 需要切换当前界面为 首页
                Toast.makeText(this,"点击首页",Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_main_offline:
                // TODO: 2016/10/9 打开新的Activity 
                break;
            case R.id.action_main_fav:
                // TODO: 2016/10/9 打开新的Activity 
                break;
//            case R.id.action_main_header:
//                Toast.makeText(this, "main点击头像", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                break;
            // TODO: 2016/10/13 测试没效果
//            case R.id.main_header_icon:
//            case R.id.main_header_icon_txt:
////                Toast.makeText(this,"点击头像",Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.main_header:
//                Toast.makeText(this,"点击头像",Toast.LENGTH_SHORT).show();
//                break;
        }
        // TODO: 2016/10/9 考虑那些是替换fragment还是打开activity

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        menu.add(0, R.id.action_search, 0, R.string.search);
//        menu.add(0, R.id.action_download, 0, R.string.download);
//        menu.add(0, R.id.action_play, 0, R.string.play);
// TODO: 2016/10/13 为什么用代码添加optionMenu都在三个点中
        getMenuInflater().inflate(R.menu.option_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //点击首页左上角返回按钮，弹出侧滑菜单
                mDrawerLayout.openDrawer(GravityCompat.START,true);

                break;
            case R.id.action_search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_download:
                break;
            case R.id.action_play:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "dian", Toast.LENGTH_SHORT).show();
    }
}
