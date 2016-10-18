package com.example.edwardgu.superapp.client;

import android.icu.text.LocaleDisplayNames;
import android.util.Log;



import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by EdwardGu on 2016/10/9.
 */

//public class MyOkHttpClient extends AbstractHttpUtil {
//
//    @Override
//    public byte[] doGetData(String url) {
//        byte[] ret=null;
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        Call call = client.newCall(request);
////        call.enqueue(new Callback() {
////            @Override
////            public void onFailure(Request request, IOException e) {
////                Log.d("ddddddddddddd", " OKHttp onFailure: "+Thread.currentThread());
////                Log.d("ddddddddddddd", " OKHttp onFailure: "+"请求失败"+request.url());
////                Log.d("ddddddddddddd", " OKHttp onFailure: "+request.body());
////
////                e.printStackTrace();
////            }
////
////            @Override
////            public void onResponse(Response response) throws IOException {
////                Log.d("ddddddddddddd", "OKHttp onResponse: "+"有响应："+Thread.currentThread());
//////                Log.d("ddddddddddddd", "OKHttp onResponse: "+"请求有响应:" + call.request().url());
////                Log.d("ddddddddddddd", "OKHttp onResponse: "+response.body().string());
////            }
////        });
//        try {
//            Response response = call.execute();
//
//            Log.d("dddddddddddd", "doGetData: ");
//            if (response.isSuccessful()) {
//                ResponseBody body = response.body();
//                ret= body.bytes();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return ret;
//    }
//
//}
