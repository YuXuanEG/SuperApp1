package com.example.edwardgu.superapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EdwardGu on 2016/10/10.
 */

public class RecommendBodyItem implements Parcelable{
    // 必选部分
    @SerializedName("title")
    private String title;
    @SerializedName("style")
    private String style;
    @SerializedName("cover")
    private String cover;
    @SerializedName("param")
    private String param;
    @SerializedName("goto")
    private String _goto;
    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;

    //可选部分
    @SerializedName("play")
    private String play;
    @SerializedName("danmaku")
    private String danmaku;

    // 直播部分
    @SerializedName("up_face")
    private String up_face;
    @SerializedName("up")
    private String up;
    @SerializedName("online")
    private long online;
    @SerializedName("area")
    private String area;
    @SerializedName("area_id")
    private long area_id;

    //番剧部分
    @SerializedName("descl")
    private String descl;
    @SerializedName("status")
    private int status;

    protected RecommendBodyItem(Parcel in) {
        title = in.readString();
        style = in.readString();
        cover = in.readString();
        param = in.readString();
        _goto = in.readString();
        width = in.readInt();
        height = in.readInt();
        play = in.readString();
        danmaku = in.readString();
        up_face = in.readString();
        up = in.readString();
        online = in.readLong();
        area = in.readString();
        area_id = in.readLong();
        descl = in.readString();
        status = in.readInt();
    }

    public static final Creator<RecommendBodyItem> CREATOR = new Creator<RecommendBodyItem>() {
        @Override
        public RecommendBodyItem createFromParcel(Parcel in) {
            return new RecommendBodyItem(in);
        }

        @Override
        public RecommendBodyItem[] newArray(int size) {
            return new RecommendBodyItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getStyle() {
        return style;
    }

    public String getCover() {
        return cover;
    }

    public String getParam() {
        return param;
    }

    public String getGoto() {
        return _goto;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getPlay() {
        return play;
    }

    public String getDanmaku() {
        return danmaku;
    }

    public String getUpace() {
        return up_face;
    }

    public String getUp() {
        return up;
    }

    public long getOnline() {
        return online;
    }

    public String getArea() {
        return area;
    }

    public long getAreaId() {
        return area_id;
    }

    public String getDescl() {
        return descl;
    }

    public int getStatus() {
        return status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(style);
        dest.writeString(cover);
        dest.writeString(param);
        dest.writeString(_goto);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(play);
        dest.writeString(danmaku);
        dest.writeString(up_face);
        dest.writeString(up);
        dest.writeLong(online);
        dest.writeString(area);
        dest.writeLong(area_id);
        dest.writeString(descl);
        dest.writeInt(status);
    }
}
