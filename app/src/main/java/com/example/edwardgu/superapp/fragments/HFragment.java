package com.example.edwardgu.superapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HFragment extends BaseFragment implements View.OnClickListener {


    public HFragment() {
        // Required empty public constructor
    }

    @Override
    public String getFragmentTitle() {
        return "头像";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_h, container, false);

        TextView hTxt = (TextView) ret.findViewById(R.id.main_h_fragment_icon_txt);
        ImageView hImg = (ImageView) ret.findViewById(R.id.main_h_fragment_icon);
        hTxt.setOnClickListener(this);
        hImg.setOnClickListener(this);


        return ret;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_h_fragment_icon:
            case R.id.main_h_fragment_icon_txt:
                Toast.makeText(getContext(),"点击头像",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
