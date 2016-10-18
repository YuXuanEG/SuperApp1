package com.example.butterknifedemo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelloFragment extends Fragment {

    @BindView(R.id.txt_hello)
    TextView mTextView;

    public HelloFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_hello, container, false);
        //使用butterKnife 加载控件，并且注入到Fragment
        ButterKnife.bind(this,ret);//针对Fragment的
        mTextView.setText("今天天气不错");
        return ret;
    }

    @OnClick({R.id.f_btn,R.id.f_btn2})
    public void btnOnClick(View view){
        Toast.makeText(getContext(), "点了一下", Toast.LENGTH_SHORT).show();
    }

}
