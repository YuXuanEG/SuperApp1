package com.example.edwardgu.superapp.fragments.VideoDetail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.fragments.BaseFragment;

/**
 * 视频详情的评论页
 */
public class CommentFragment extends BaseFragment {


    public CommentFragment() {

    }

    @Override
    public String getFragmentTitle() {
        return "评论";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_comment, container, false);
        RecyclerView recyclerView = (RecyclerView) ret.findViewById(R.id.video_detail_comment_list);
        if (recyclerView != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(
                    getContext(),
                    LinearLayoutManager.VERTICAL,
                    false
            );
            recyclerView.setLayoutManager(layoutManager);
            // TODO: 2016/10/15 为recyclerView添加adapter
//            recyclerView.setAdapter();
        }
        return ret;
    }

}
