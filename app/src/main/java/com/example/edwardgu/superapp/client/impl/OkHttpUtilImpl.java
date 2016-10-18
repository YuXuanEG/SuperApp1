package com.example.edwardgu.superapp.client.impl;

import com.example.edwardgu.superapp.client.AbstractHttpUtil;
import com.example.edwardgu.superapp.client.HttpCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by EdwardGu on 2016/10/10.
 */

public class OkHttpUtilImpl extends AbstractHttpUtil {

    private OkHttpClient mOkHttpClient;

    public OkHttpUtilImpl(){
        mOkHttpClient = new OkHttpClient();
    }
    @Override
    public byte[] doGetData(String url) {
        byte[] ret =null;
        if(url!=null){
            Request.Builder builder=new Request.Builder();
            builder.url(url).get().addHeader(
                    "User-Agent",
                    "BiLiBiLi WP Client/4.20.0 (orz@loli.my)"
            );
            Request request = builder.build();
            try {
                Response response = mOkHttpClient.newCall(request).execute();
                //Response 获取操作，会直接读取网络数据并且关闭；
                ret=response.body().bytes();
                response.close();
                response=null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }


    @Override
    public void doGetDataAsync(String url, final HttpCallback callback) {
        if (url != null) {
            Request.Builder builder = new Request.Builder();
            builder
                    .url(url)
                    .get()
                    .addHeader(
                            "User-Agent",
                            "BiLiBiLi WP Client/4.20.0 (orz@loli.my)"
                    );
            Request request = builder.build();
            mOkHttpClient.newCall(request)
                    .enqueue(
                    new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            int code = response.code();

                                byte[] data = response.body().bytes();
                                HttpUrl reqUrl = call.request().url();

                            if (callback != null) {
                                callback.onSuccess(reqUrl.toString(),code,data);
                            }

                        }
                    }
            );
        }
    }
}
