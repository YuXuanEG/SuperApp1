package com.example.edwardgu.superapp.model;

/**
 * Created by EdwardGu on 2016/10/10.
 */

/**
 * 针对MVP模式，
 * 把数据模型进行接口抽象，包含数据的各种操作
 */
public interface IRecomendItemModel {
    String getType();
    void setType(String type);
}
