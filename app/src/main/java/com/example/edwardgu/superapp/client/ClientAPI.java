package com.example.edwardgu.superapp.client;

/**
 * Created by EdwardGu on 2016/10/9.
 */

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.edwardgu.superapp.client.impl.OkHttpUtilImpl;
import com.example.edwardgu.superapp.model.PlayUrl;
import com.example.edwardgu.superapp.model.RecommendItem;
import com.example.edwardgu.superapp.model.VideoDetail;
import com.example.edwardgu.superapp.model.VideoDetailIntro;
import com.google.gson.Gson;

import org.apache.commons.codec.digest.DigestUtils;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * 所有的接口请求，处理地址，参数
 */
public class ClientAPI {
    private static AbstractHttpUtil sHttpUtil;
    public static final String APP_KEY="1d8b6e7d45233436";
    private static final String APP_SECRET="FamGR6I1OwSutCZ2CelYQTJznz42c7sBbJcC0YxGXToV8j14uk1+3VvTFEbyBZeW";

    public static final String PLAYURL_APP_KEY="4ebafd7c4951b366";
    public static final String PLAYURL_APP_SECRET="8cb98205e9b2ad3669aad0fce12a4c13";

    static {
        // TODO: 2016/10/10 创建特定的网络类库的支持
//        sHttpUtil=new MyOkHttpClient();
        sHttpUtil=new OkHttpUtilImpl();
    }

    private ClientAPI(){
    }
    public static void getPlayUrlAsync(String cid,int quality,String videoType){
        String url="http://interface.bilibili.com/playurl";
        TreeMap<String,String> params=new TreeMap<>();
        params.put("cid",cid);
        params.put("quality",Integer.toString(quality));
        params.put("otype","json");
        params.put("type",videoType);
        // TODO: 2016/10/12  之前的appendParamsWithSign   有问题
        url=appendParamsWithSign(url,params,PLAYURL_APP_KEY,PLAYURL_APP_SECRET);
        Log.d("Vurl", "getPlayUrlAsync: url333333333="+url);

        sHttpUtil.doGetDataAsync(url, new HttpCallback() {
            @Override
            public void onSuccess(String url, int code, byte[] data) {
                if (code==200) {
                    try {
                        String str=new String(data,"utf-8");
//                        JSONObject json = new JSONObject(str);
//                        Log.d("Vurl", "onSuccess: url= "+json.toString(4));
                        Gson gson = new Gson();
                        PlayUrl playUrl = gson.fromJson(str, PlayUrl.class);

                        EventBus.getDefault().post(playUrl);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private static String appendParamsWithSign(
            String url,
            TreeMap<String, String> params,
            String appKey,
            String appSecret) {
        String ret=url;
        if (url != null&&params!=null&&appKey!=null&&appSecret!=null) {
            params.put("appkey",appKey);
            params.put("build","420000");
            params.put("channel","bili");
            params.put("mobi_app","android");
            params.put("platform","android");
            // TODO: 2016/10/11 机型适配
            params.put("screen","xxhdpi");
            params.put("ts",Long.toString(System.currentTimeMillis()));//时间戳

            String sign = sign(params, appSecret);
            StringBuilder sb=new StringBuilder(url);
            sb.append('?');
            Set<String> keySet = params.keySet();
            try {
                for (String key : keySet) {
                    sb.append(key).append('=')
                            .append(URLEncoder.encode(params.get(key),"utf-8"))
                            .append('&');
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (sb.length()>0) {
                sb.append("sign=").append(sign);

            }
            ret=sb.toString();
        }

        return ret;
    }


    /**
     * 获取首页推荐列表
     * @return
     */
    public static JSONObject getRecommendList(){
        String url="http://app.bilibili.com/x/show/old?appkey=1d8b6e7d45233436";
        JSONObject object = sHttpUtil.doGet(url);
        Log.d("dddddddddd", "getRecommendList: "+object.toString());
        return object;
    }

    public static void getRecommendListAsync(){
        String url="http://app.bilibili.com/x/show/old?appkey=1d8b6e7d45233436";
        sHttpUtil.doGetDataAsync(url, new HttpCallback() {
            @Override
            public void onSuccess(String url, int code, byte[] data) {

                if (code==200&&data!=null) {
                    try {

                        String str = new String(data, "utf-8");
                        Log.d("ddddddddd", "ClientAPI getRecommendListAsync onSuccess: "+str);
                        Log.d("ddddddddd", "ClientAPI getRecommendListAsync onSuccess: "+url);
                        JSONObject json = new JSONObject(str);
                        //解析Json
                        code = json.getInt("code");
                        if (code==0) {
                            JSONArray array = json.getJSONArray("result");
                            int length = array.length();
                            ArrayList<RecommendItem> items = new ArrayList<>();
                            for (int i = 0; i < length; i++) {
                                JSONObject obj = array.getJSONObject(i);
                                RecommendItem item = RecommendItem.createFromJson(obj);
                                items.add(item);
                            }

                            //TODO: 2016/10/10 把数据发送给 上层UI
                            EventBus.getDefault().post(items);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 获取视频详情
     * @param aid
     */
    public static void getVideoDetail(String aid){
        String url="https://app.bilibili.com/x/view";
        // TODO: 2016/10/11 进行参数数字签名，访问网络，获取数据
        TreeMap<String,String> params=new TreeMap<>();
        params.put("aid",aid);
        params.put("plat","0");
        url=appendParamsWithSign(url,params);


        Log.d("ddddddddd", "getVideoDetail: url="+url);
        sHttpUtil.doGetDataAsync(url, new HttpCallback() {
            @Override
            public void onSuccess(String url, int code, byte[] data) {
                //处理视频详情
                if (code==200) {
                    try {
                        Log.d("ddddddVD", "url =  "  + url);
                        String str = new String(data, "utf-8");
                        JSONObject json = new JSONObject(str);

                        code=json.getInt("code");
                        if (code==0) {
                            JSONObject jsonData = json.getJSONObject("data");
                            Gson gson=new Gson();
                            VideoDetail videoDetail = gson.fromJson(jsonData.toString(), VideoDetail.class);
                            EventBus.getDefault().post(videoDetail);
                            VideoDetailIntro detailIntro = gson.fromJson(jsonData.toString(), VideoDetailIntro.class);
                            EventBus.getDefault().post(detailIntro);
                        }

//                        Log.d("VD","result = " + json.toString(4));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    /**
     * 拼一个完整的Get请求地址
     * @return
     */
    private static String appendParamsWithSign(String url,TreeMap<String,String> params){
        return appendParamsWithSign(url,params,PLAYURL_APP_KEY,PLAYURL_APP_SECRET);
    }

    /**
     * 计算sign数值，进行数字签名
     * @param params
     * @param appSecret
     * @return
     */
    private static String sign(TreeMap<String,String> params,String appSecret){
        String ret=null;
        if (params != null&&appSecret!=null) {
            StringBuilder sb=new StringBuilder();

            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                try {
                    sb.append(key).append('=')
                            .append(URLEncoder.encode(params.get(key),"utf-8"))
                            .append('&');
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (sb.length()>0) {
                sb.deleteCharAt(sb.length()-1);
            }
            sb.append(appSecret);
            String str = sb.toString();

            ret=DigestUtils.md5Hex(str);
        }
        return ret;
    }

}
