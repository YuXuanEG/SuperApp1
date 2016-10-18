package com.example.edwardgu.superapp.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EdwardGu on 2016/10/10.
 */


public class RecommendItem implements IRecomendItemModel {

    private String type;
    private String headParam;
    private String headGoto;
    private String headStyle;
    private String headTitle;
    private long headCount;

    private List<RecommendBodyItem> body;

    public static RecommendItem createFromJson(JSONObject json) throws JSONException {
        RecommendItem ret=null;
        if (json != null) {
            ret=new RecommendItem();
            ret.type=json.optString("type");
            JSONObject head = json.getJSONObject("head");
            ret.headParam=head.getString("param");
            ret.headGoto=head.getString("goto");
            ret.headStyle=head.getString("style");
            ret.headTitle=head.getString("title");

            ret.headCount= head.optLong("count");

            JSONArray body = json.getJSONArray("body");
            int size = body.length();
            if (size>0) {
                ret.body=new ArrayList<>();

                Gson gson = new Gson();

                for (int i = 0; i < size; i++) {
//                    RecommendBodyItem bodyItem = new RecommendBodyItem();
                    JSONObject bi = body.getJSONObject(i);
                    RecommendBodyItem bodyItem=
                            gson.fromJson(
                                    bi.toString(),
                                    RecommendBodyItem.class
                            );
                    // TODO: 2016/10/10 解析Body内部内容
                    ret.body.add(bodyItem);
                }
            }
        }

        return ret;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public List<RecommendBodyItem> getBody() {
        return body;
    }

    public void setBody(List<RecommendBodyItem> body) {
        this.body = body;
    }
}
