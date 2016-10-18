package com.example.edwardgu.superapp.login.presenter;

/**
 * Created by EdwardGu on 2016/10/13.
 */

public interface ILoginPresenter {
    void clear();
    void doLogin(String name, String passwd);
    void setProgressBarVisiblity(int visiblity);

}
