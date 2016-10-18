package com.example.edwardgu.superapp.loaders;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.edwardgu.superapp.client.ClientAPI;

import org.json.JSONObject;

/**
 * Created by EdwardGu on 2016/10/9.
 */

public class NetworkLoader extends AsyncTaskLoader<JSONObject> {


    public NetworkLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public JSONObject loadInBackground() {

        return ClientAPI.getRecommendList();
    }
}
