package com.example.edwardgu.superapp.model;

/**
 * Created by EdwardGu on 2016/10/12.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 视频播放时获取到的视频网址
 * @see com.example.edwardgu.superapp.client.ClientAPI#getPlayUrlAsync(String, int, String)
 */
public class PlayUrl {
    @SerializedName("from")
    private String from;
    @SerializedName("timelength")
    private long timeLength;//json timelength
    @SerializedName("format")
    private String format;
    @SerializedName("durl")
    private List<Durl> mDurls;

    public String getFrom() {
        return from;
    }

    public long getTimeLength() {
        return timeLength;
    }

    public String getFormat() {
        return format;
    }

    public List<Durl> getDurls() {
        return mDurls;
    }

    public static class Durl{
        @SerializedName("order")
        private int order;
        @SerializedName("length")
        private long length;
        @SerializedName("size")
        private long size;//视频文件长度

        /**
         * 视频网址，直接播放
         */
        @SerializedName("url")
        private String url;
        @SerializedName("backup_url")
        private List<String> backupUrls;

        public int getOrder() {
            return order;
        }

        public long getLength() {
            return length;
        }

        public long getSize() {
            return size;
        }

        public String getUrl() {
            return url;
        }

        public List<String> getBackupUrls() {
            return backupUrls;
        }
    }
}
