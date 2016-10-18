package com.example.edwardgu.superapp.fragments;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by EdwardGu on 2016/10/9.
 */

/**
 * 首页Fragment父类
 * 定义通用的属性和行为
 */
public abstract class BaseFragment extends Fragment {
    public BaseFragment(){

    }
    public abstract String getFragmentTitle();

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getFragmentTitle());
    }

    @Override
    public void onPause() {
        MobclickAgent.onPageEnd(getFragmentTitle());
        super.onPause();
    }
}
