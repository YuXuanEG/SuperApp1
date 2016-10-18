package com.example.edwardgu.superapp;

import android.app.Application;
import android.app.usage.UsageEvents;
import android.content.res.Configuration;

import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.EventBusBuilder;

/**
 * Created by EdwardGu on 2016/10/10.
 */

/**
 * 全局初始化
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        1.1 默认初始化EventBus实例
//        EventBus.getDefault();

        //1.2 自定义初始化
        EventBusBuilder builder = EventBus.builder();
        builder.installDefaultEventBus();

        PlatformConfig.setSinaWeibo("3685512391","399f8c5a8d14096306f952b5a0fed58e");


    }
}
