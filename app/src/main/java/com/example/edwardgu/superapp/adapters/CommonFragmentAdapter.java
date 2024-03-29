package com.example.edwardgu.superapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.edwardgu.superapp.fragments.BaseFragment;

import java.util.List;

/**
 * Created by EdwardGu on 2016/10/9.
 */

public class CommonFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragments;

    public CommonFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        int ret=0;
        if(mFragments!=null){
            ret=mFragments.size();
        }
        return ret;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getFragmentTitle();
    }
}
