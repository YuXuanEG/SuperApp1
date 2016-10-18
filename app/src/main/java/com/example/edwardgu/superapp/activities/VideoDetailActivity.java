package com.example.edwardgu.superapp.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.adapters.CommonFragmentAdapter;
import com.example.edwardgu.superapp.client.ClientAPI;
import com.example.edwardgu.superapp.fragments.BaseFragment;
import com.example.edwardgu.superapp.fragments.VideoDetail.CommentFragment;
import com.example.edwardgu.superapp.fragments.VideoDetail.IntroFragment;
import com.example.edwardgu.superapp.model.PlayUrl;
import com.example.edwardgu.superapp.model.RecommendBodyItem;
import com.example.edwardgu.superapp.model.VideoDetail;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class VideoDetailActivity extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback, IMediaPlayer.OnPreparedListener, IMediaPlayer.OnBufferingUpdateListener, View.OnTouchListener {

    /**
     * 从推荐，分区 点击进入视频详情部分中，视频的基本对象
     * 通常可以使用 RecommendBodyItem即可
     */
    public static final String EXTRA_VIDEO_ITEM = "video_item";

    private RecommendBodyItem mRecommendBodyItem;

    /**
     * 视频详情数据
     */
    private VideoDetail mVideoDetail;

    private SurfaceView mSurfaceView;
    private IjkMediaPlayer mPlayer;
    private GestureDetectorCompat mCompat;

    private View mMediaController;

    /**
     * 如果发生屏幕切换，进行播放
     */
    private boolean hasSwitchOrientation;
    private FloatingActionButton mFab;

    //控制播放控制进度条的布局显示和隐藏
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //注意设置状态栏要写在setContentView(R.layout.activity_main);之前
        //沉浸式状态栏，隐藏状态栏

//        if (Build.VERSION.SDK_INT >= 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }else {
//            View decorView = getWindow().getDecorView();
//            int uiOption = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOption);
//        }

        //设置状态栏为透明的
        final int sdk = Build.VERSION.SDK_INT;
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        if (sdk >= Build.VERSION_CODES.KITKAT) {
            int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            // 设置透明状态栏
            if ((params.flags & bits) == 0) {
                params.flags |= bits;
                window.setAttributes(params);
            }
        }

        setContentView(R.layout.activity_video_detail);

        if (savedInstanceState != null) {
            //就说明发送了切换屏幕
            hasSwitchOrientation = true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.video_detail_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();
        mRecommendBodyItem = intent.getParcelableExtra(EXTRA_VIDEO_ITEM);

        EventBus.getDefault().register(this);


        ImageView imageView = (ImageView) findViewById(R.id.video_detail_cover);
        if (imageView != null) {
            Picasso.with(this)
                    .load(mRecommendBodyItem.getCover())
                    .config(Bitmap.Config.RGB_565)//如果使用  RGB_565 对于png 图片来说，将不支持透明
                    .resize(
                            mRecommendBodyItem.getWidth(),
                            mRecommendBodyItem.getHeight()
                    )
                    .into(imageView);
        }

        mFab = (FloatingActionButton) findViewById(R.id.video_detail_play_fab);
        if (mFab != null) {
            mFab.setOnClickListener(this);
        }

        mVideoDetail = null;

        //----------------------
        //SurfaceView 准备播放视频
        mSurfaceView = (SurfaceView) findViewById(R.id.video_detail_play_view);
        if (mSurfaceView != null) {
            mSurfaceView.getHolder().addCallback(this);
            //兼容包下边的手势识别器，实现 点击 拖拽的判断
            mCompat = new GestureDetectorCompat(this, new VidioGestrueDetector());
            //手势可以在事件里边调用进行检查
            mSurfaceView.setOnTouchListener(this);
        }

        mPlayer = new IjkMediaPlayer();
        //当播放器加载网络的视频资源时，会在内部进行网络访问，
        mPlayer.setOnPreparedListener(this);

        mPlayer.setOnBufferingUpdateListener(this);

        initView();
    }

    private void initView() {
        View btnSwitch = findViewById(R.id.video_controller_orientation_switch);
        btnSwitch.setOnClickListener(this);
        mMediaController = findViewById(R.id.video_controller);


        //-----------------------
        //设置简介和评论两个teb
        ViewPager pager= (ViewPager) findViewById(R.id.video_detail_pager);
        if (pager != null) {
            ArrayList<BaseFragment> fragments = new ArrayList<>();
            fragments.add(new IntroFragment());
            fragments.add(new CommentFragment());

            CommonFragmentAdapter adapter = new CommonFragmentAdapter(
                    getSupportFragmentManager(),
                    fragments
            );
            pager.setAdapter(adapter);
            TabLayout tabLayout = (TabLayout) findViewById(R.id.video_detail_tab_layout);
            tabLayout.setupWithViewPager(pager);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // TODO: 2016/10/12 activity 生命周期
        setIntent(intent);
        mVideoDetail = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ClientAPI.getVideoDetail("6562217");
        ClientAPI.getVideoDetail(mRecommendBodyItem.getParam());
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart("videoDetail");
    }

    public void onPause() {
        MobclickAgent.onPageEnd("videoDetail");
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 接收到详情，需要更新UI
     *
     * @param detail
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveDetail(VideoDetail detail) {
//        List<VideoDetail.Page> pages = detail.getPages();
//        if (pages != null&&!pages.isEmpty()) {
//            VideoDetail.Page page = pages.get(0);
//            long cid = page.getCid();
//            Toast.makeText(this,"cid="+cid,Toast.LENGTH_SHORT).show();
//        }
        // TODO: 2016/10/12  考虑
        mVideoDetail = detail;

        //判断是否为横屏，横屏要求自动播放视频
        if (hasSwitchOrientation) {
            startVideoPlay();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onVideoPlayClickEvent(PlayUrl playUrl) {
        mSurfaceView.setVisibility(View.VISIBLE);

        // TODO: 2016/10/12 播放视频
        List<PlayUrl.Durl> durls = playUrl.getDurls();
        if (durls != null) {
            if (!durls.isEmpty()) {
                PlayUrl.Durl durl = durls.get(0);
                String url = durl.getUrl();
                if (url != null) {
                    try {

                        //设置视频，音频 的地址 需要网络或者IO流的读取
//                        Log.d(TAG, "onVideoPlayClickEvent: ");
                        mPlayer.setDataSource(url);
                        //异步子线程方式，开始准备视频，准备好就回调接口
                        mPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.video_detail_play_fab:
                // TODO: 2016/10/12 视频播放
                startVideoPlay();
                v.setVisibility(View.INVISIBLE);
                break;
            case R.id.video_controller_orientation_switch:
                //获取当前Activity的屏幕方向
                int requestedOrientation = this.getRequestedOrientation();
                if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
                    //垂直方向，那么切换成水平方向
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
        }
    }

    private void startVideoPlay() {
        if (mVideoDetail != null) {
            List<VideoDetail.Page> pages = mVideoDetail.getPages();
            // TODO: 2016/10/12 需要支持多个pages的视频切换功能
            if (pages != null && pages.size() > 0) {
                VideoDetail.Page page = pages.get(0);
                long cid = page.getCid();
                ClientAPI.getPlayUrlAsync(Long.toString(cid), 1, "mp4");
            }
        }
    }


    //---------------------------------------------

    /**
     * 当SurfaceView准备好，并且能够显示的时候，回调
     * 使用SurfaceHolder 可以进行绘制
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化 播放器或者是 照相机
        mPlayer.setDisplay(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //释放资源，播放器停止，释放
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
        mPlayer.release();
    }

    //————————————————————————————————————
    //当视频准备好的时候，方法回调
    @Override
    public void onPrepared(IMediaPlayer player) {
        player.start();
    }
//----------------------

    /**
     * 当缓存进度发生变化的时候，进行回掉
     *
     * @param player
     * @param progress
     */
    @Override
    public void onBufferingUpdate(IMediaPlayer player, int progress) {
        Log.d("VDA", "Buffering  Update: " + progress);
    }


    //SurfaceView 事件处理器
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mCompat.onTouchEvent(event);
    }

    private class VidioGestrueDetector extends GestureDetector.SimpleOnGestureListener {
        public static final String token = "token";

        /**
         * 按下事件，返回true才可以处理手势
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        /**
         * 当快速点击 抬起的时候
         *
         * @param e
         * @return
         */
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            mHandler.removeCallbacksAndMessages(token);

            mMediaController.setVisibility(View.VISIBLE);

            long time = SystemClock.uptimeMillis() + 2000;

            mHandler.postAtTime(new Runnable() {
                @Override
                public void run() {
                    mMediaController.setVisibility(View.INVISIBLE);
                }
            }, time);

            return super.onSingleTapUp(e);
        }
    }
}
