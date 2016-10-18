package com.example.edwardgu.superapp.login.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.edwardgu.superapp.login.model.IUser;
import com.example.edwardgu.superapp.login.model.UserModel;
import com.example.edwardgu.superapp.login.view.ILoginView;

/**
 * Created by EdwardGu on 2016/10/13.
 */

public class LoginPresenter implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        this.handler = new Handler(Looper.getMainLooper());
        initUser();
    }

    /**
     * 设置一个默认用户
     */
    private void initUser() {
        user = new UserModel("mvp","mvp");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwd) {
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name,passwd);
        if (code!=0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        final UserModel user=new UserModel(name,passwd);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code,user);
            }
        }, 1000);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }
}
