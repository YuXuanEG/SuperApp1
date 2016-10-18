package com.example.edwardgu.superapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by EdwardGu on 2016/10/15.
 */

public class VideoDetailIntro {
    @SerializedName("title")
    private String title;
    @SerializedName("desc")
    private String desc;
    @SerializedName("owner")
    private Owner owner;
    @SerializedName("relates")
    private List<RelateItem> relateItem;
    public static class Owner{
        @SerializedName("face")
        private String face;
        @SerializedName("mid")
        private long mid;
        @SerializedName("name")
        private String name;

        public String getFace() {
            return face;
        }

        public long getMid() {
            return mid;
        }

        public String getName() {
            return name;
        }
    }
    public static class RelateItem{
        @SerializedName("aid")
        private long aid;
        @SerializedName("title")
        private String title;
        @SerializedName("owner")
        private Owner owner;
        @SerializedName("pic")
        private String pic;
        @SerializedName("stat")
        private Stat stat;
        public static class Stat{
            @SerializedName("danmaku")
            private int danmaku;
            @SerializedName("view")
            private int view;

            public int getDanmaku() {
                return danmaku;
            }

            public int getView() {
                return view;
            }
        }

        public long getAid() {
            return aid;
        }

        public String getTitle() {
            return title;
        }

        public Owner getOwner() {
            return owner;
        }

        public String getPic() {
            return pic;
        }

        public Stat getStat() {
            return stat;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public Owner getOwner() {
        return owner;
    }

    public List<RelateItem> getRelateItem() {
        return relateItem;
    }
}
