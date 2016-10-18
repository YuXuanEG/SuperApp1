package com.example.edwardgu.superapp.fragments.VideoDetail;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.adapters.IntroRelateListAdapter;
import com.example.edwardgu.superapp.fragments.BaseFragment;
import com.example.edwardgu.superapp.images.CircleTrasformation;
import com.example.edwardgu.superapp.model.VideoDetailIntro;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频详情中的简介
 */
public class IntroFragment extends BaseFragment {

    private VideoDetailIntro mVideoDetailIntro;
    private TextView mTitle;
    private TextView mDesc;
    private ImageView mOwnerPic;
    private TextView mName;
    private RecyclerView mRelateList;

    private List<VideoDetailIntro.RelateItem> mDate;
    private IntroRelateListAdapter mAdapter;

    public static final CircleTrasformation circleTransformation=new CircleTrasformation();

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public String getFragmentTitle() {
        return "简介";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate=new ArrayList<>();
        mAdapter=new IntroRelateListAdapter(mDate,getContext());
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_intro, container, false);
        mTitle = (TextView) ret.findViewById(R.id.intro_title);
        mDesc = (TextView) ret.findViewById(R.id.intro_desc);
        mOwnerPic = (ImageView) ret.findViewById(R.id.intro_owner);
        mName = (TextView) ret.findViewById(R.id.intro_owner_name);
        mRelateList = (RecyclerView) ret.findViewById(R.id.intro_relate_list);

        mRelateList.setNestedScrollingEnabled(false);
        mRelateList.setLayoutManager(
                new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );

        mRelateList.setAdapter(mAdapter);


        return ret;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveIntroDetail(VideoDetailIntro videoDetailIntro){
        //获取到简介的实体类
        mVideoDetailIntro=videoDetailIntro;

        mTitle.setText(mVideoDetailIntro.getTitle());
        mDesc.setText(mVideoDetailIntro.getDesc());
        mName.setText(mVideoDetailIntro.getOwner().getName());

        Picasso.with(mOwnerPic.getContext())
                .load(mVideoDetailIntro.getOwner().getFace())
                .config(Bitmap.Config.RGB_565)
                .transform(circleTransformation)
                .into(mOwnerPic);

        mDate.clear();
        mDate.addAll(mVideoDetailIntro.getRelateItem());
        mAdapter.notifyDataSetChanged();
    }

}
