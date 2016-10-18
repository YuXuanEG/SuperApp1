package com.example.edwardgu.superapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.edwardgu.superapp.R;
import com.example.edwardgu.superapp.model.RecommendBodyItem;
import com.umeng.analytics.MobclickAgent;

public class WebLinkDetailActivity extends AppCompatActivity {

    public static final String EXTRA_WEB_ITEM = "web_item";
    private RecommendBodyItem mRecommendBodyItem;
    private WebView mWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_link_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.web_detail_tool_bar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        // TODO: 2016/10/14 为toolbar设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("webLink");
        mWebView = (WebView) findViewById(R.id.activity_web_view);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient()
            {
                @Override
                public void onReceivedTitle(WebView view, String title) {
                    setTitle(title);
                }
            }
        );

        Intent intent = getIntent();
        mRecommendBodyItem = intent.getParcelableExtra(EXTRA_WEB_ITEM);
        String detailUrl = mRecommendBodyItem.getParam();
        mWebView.loadUrl(detailUrl);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_web_share:
                // TODO: 2016/10/14 分享
                break;
            case R.id.action_web_open_chrome:
                // TODO: 2016/10/14 用浏览器打开
                break;
            case R.id.action_web_copy_link:
                // TODO: 2016/10/14 复制链接
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
