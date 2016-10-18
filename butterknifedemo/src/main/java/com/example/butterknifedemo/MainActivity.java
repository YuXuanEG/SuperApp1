package com.example.butterknifedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    //1.使用ButterKnife 不能够使用private和static修饰成员变量
    @BindView(R.id.txt_name)
    TextView mTextView;
    @BindView(R.id.img_icon)
    ImageView mImageView;
    @BindView(R.id.btn)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //2.使用ButterKnife需要手动注入，把当前类中所有对应注解反射注入进来
        ButterKnife.bind(this);//注入activity
        mTextView.setText("hello world by Gu sir");
        mButton.setText("good");
        mImageView.setImageResource(R.mipmap.ic_launcher);

        if (BuildConfig.DEBUG) {
            Log.d("ddddddddd", "onCreate: this is a debug log");
        }
    }


}
