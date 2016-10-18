package com.example.edwardgu.superapp.login.model;

/**
 * Created by EdwardGu on 2016/10/13.
 */

public interface IUser {
    String getName();
    String getPasswd();
    int checkUserValidity(String name, String passwd);

}
