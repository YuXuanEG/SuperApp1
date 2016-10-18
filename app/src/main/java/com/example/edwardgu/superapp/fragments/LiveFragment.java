package com.example.edwardgu.superapp.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.client.ClientAPI;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveFragment extends BaseFragment
//        implements SurfaceHolder.Callback, IMediaPlayer.OnPreparedListener
{

//    private IjkMediaPlayer mPlayer;

    public LiveFragment() {
        // Required empty public constructor
    }

    @Override
    public String getFragmentTitle() {
        return "直播";
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mPlayer=new IjkMediaPlayer();
//        mPlayer.setOnPreparedListener(this);
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        mPlayer.reset();
//        mPlayer.start();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        mPlayer.release();
//        mPlayer=null;
//        super.onDestroy();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View ret = inflater.inflate(R.layout.fragment_live, container, false);
//        SurfaceView surfaceView = (SurfaceView) ret.findViewById(R.id.surface_view);
//        if (surfaceView != null) {
//            surfaceView.getHolder().addCallback(this);
//        }
//
////        ClientAPI.getPlayUrlAsync("10648434",1,"mp4");
//
//        return ret;
//    }
//
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//        mPlayer.setDisplay(holder);
////        try {
////            mPlayer.setDataSource("http://cn-tj2-dx.acgvideo.com/vg2/f/7b/10648434-1-hd.mp4?expires=1476191100&ssig=CoZaNUE5JxlZJ9xDFMwN6Q&oi=2034570579&rate=0");
////            mPlayer.prepareAsync();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//    }
//
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//
//    }
//
//    //m
//    @Override
//    public void onPrepared(IMediaPlayer player) {
//        player.start();
//    }
}
