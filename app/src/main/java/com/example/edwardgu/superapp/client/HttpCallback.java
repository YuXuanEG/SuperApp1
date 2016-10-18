package com.example.edwardgu.superapp.client;

/**
 * Created by EdwardGu on 2016/10/10.
 */


/**
 * 接口设计的原则：
 * 1.通常一个接口尽可能少包含方法；
 * 2.
 */
public interface HttpCallback {
    /**
     * 网络访问成功，返回数据
     */
    void onSuccess(String url, int code,byte[] data);
}
