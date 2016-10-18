package com.example.edwardgu.superapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edwardgu.superapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment{


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public String getFragmentTitle() {
        return "番剧";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

}
