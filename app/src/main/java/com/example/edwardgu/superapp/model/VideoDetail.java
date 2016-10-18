package com.example.edwardgu.superapp.model;

/**
 * Created by EdwardGu on 2016/10/12.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 视频详情数据
 */
public class VideoDetail {
    @SerializedName("aid")
    private long aid;
    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String desc;
    @SerializedName("pages")
    private List<Page> mPages;

    public static class Page{
        @SerializedName("cid")
        private long cid;
        @SerializedName("part")
        private String part;
        @SerializedName("vid")
        private String vid;

        public long getCid() {
            return cid;
        }

        public String getPart() {
            return part;
        }

        public String getVid() {
            return vid;
        }
    }

    public long getAid() {
        return aid;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public List<Page> getPages() {
        return mPages;
    }
}
