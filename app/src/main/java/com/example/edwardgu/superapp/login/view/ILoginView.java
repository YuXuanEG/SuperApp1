package com.example.edwardgu.superapp.login.view;

import com.example.edwardgu.superapp.login.model.UserModel;

/**
 * Created by EdwardGu on 2016/10/13.
 */

public interface ILoginView {
    public void onClearText();
    public void onLoginResult(Boolean result, int code, UserModel user);
    public void onSetProgressBarVisibility(int visibility);

}
