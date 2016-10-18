package com.example.edwardgu.superapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.activities.VideoDetailActivity;
import com.example.edwardgu.superapp.activities.WebLinkDetailActivity;
import com.example.edwardgu.superapp.adapters.PicturePageAdapter;
import com.example.edwardgu.superapp.adapters.RecommendListAdapter;
import com.example.edwardgu.superapp.client.ClientAPI;
import com.example.edwardgu.superapp.loaders.NetworkLoader;
import com.example.edwardgu.superapp.model.RecommendBodyItem;
import com.example.edwardgu.superapp.model.RecommendItem;
import com.example.edwardgu.superapp.presenters.RecommendPresenter;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends BaseFragment implements IRecomendView, SwipeRefreshLayout.OnRefreshListener {

    private RecommendPresenter mRecommendPresenter;
    private List<RecommendItem> mItems;
    private RecommendListAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private Handler mHandler;

    public RecommendFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems= new ArrayList<>();

        mAdapter=new RecommendListAdapter(getContext(),mItems);

        //注册EventBus事件
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("recommend");
    }

    @Override
    public void onDestroy() {
        MobclickAgent.onPageEnd("recommend");
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Override
    public String getFragmentTitle() {
        return "推荐";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View ret=inflater.inflate(R.layout.fragment_recommend, container, false);

        RecyclerView recyclerView=(RecyclerView)ret.findViewById(R.id.recomment_list);
        if (recyclerView != null) {
            //布局管理器，能够对Item进行排版
            RecyclerView.LayoutManager lm=
                    new LinearLayoutManager(
                            getContext(),
                            LinearLayoutManager.VERTICAL,
                            false);

            recyclerView.setLayoutManager(lm);

            // TODO: 2016/10/10 创建设置RecyclerView 的 adapter
            recyclerView.setAdapter(mAdapter);
        }

        //--------------------
        //为下拉刷新添加监听事件
        mSwipeRefreshLayout=(SwipeRefreshLayout) ret.findViewById(R.id.recomment_refresh_layout);
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(this);
        }


        //Demo
        ClientAPI.getRecommendListAsync();

        return ret;
    }


    private static class AdHandler extends android.os.Handler{

        private ViewPager mPager;

        public AdHandler(ViewPager pager) {
            mPager = pager;
        }

        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if(what == 998){
                int index = msg.arg1;
                mPager.setCurrentItem(index);
            }
        }
    }



    @Override
    public void setTypeText(String type) {
//        mTextView.setText(type);
    }

    ///////////////////////////////////////////////////////////////////////////
    // 下拉刷新接口回调
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onRefresh() {
        ClientAPI.getRecommendListAsync();
    }

    ///////////////////////////////////////////////////////////////////////////
    // EventBus接收
    ///////////////////////////////////////////////////////////////////////////
    //根据官方文档写出这个方法

    /**
     * EventBus当事件发送过来的时候，调用
     * 根据 threadMode 来设置这个方法在那个线程中执行
     * @param items
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(List<RecommendItem> items){
        if (items != null) {
//            Toast.makeText(getContext(),"收到推荐",Toast.LENGTH_SHORT).show();
//            for(RecommendItem item:items){
//                String type = item.getType();
//                Log.d("RF", "Type: "+type);
//            }
            mSwipeRefreshLayout.setRefreshing(false);
            mItems.clear();
            RecommendItem adItem=new RecommendItem();
            adItem.setType("ad");
            mItems.add(0,adItem);
            mItems.addAll(items);

            mAdapter.notifyDataSetChanged();
        }
    }


    @Subscribe
    public void onRecommendBodyClickEvent(RecommendBodyItem item){
//        Toast.makeText(getContext(), "点击了"+item.getTitle(), Toast.LENGTH_SHORT).show();
        //根据goto来进行显示不同的界面内容
        String aGoto = item.getGoto();
        // av视频详情   推荐  区域
        // live直播
        // weblink网页 weblink，activity
        // bangumi_list 番剧#

        Intent intent=null;

        switch (aGoto) {
            case "av":
                intent=new Intent(getContext(), VideoDetailActivity.class);
                intent.putExtra(VideoDetailActivity.EXTRA_VIDEO_ITEM,item);
                break;
            case "live":
                break;
            case "weblink":
                intent=new Intent(getContext(), WebLinkDetailActivity.class);
                intent.putExtra(WebLinkDetailActivity.EXTRA_WEB_ITEM,item);
                break;
            case "bangumi_list":
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
