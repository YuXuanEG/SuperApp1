package com.example.edwardgu.superapp.client;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by EdwardGu on 2016/10/9.
 */

public abstract class AbstractHttpUtil {

    public JSONObject doGet(String url){
        JSONObject ret=null;
        byte[] bytes = doGetData(url);


        if (bytes != null) {
            try {
                String str = new String(bytes, "utf-8");
                ret=new JSONObject(str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    public abstract byte[] doGetData(String url);

    /**
     *
     * 异步的Get请求，请求在子线程中
     * @param url
     * @return
     */
    public abstract void doGetDataAsync(String url,HttpCallback callback);
}
