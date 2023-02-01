package com.sping.mobileplayer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.sping.mobileplayer.R;

/**
 * SplashActivity，闪屏
 * 继承：BaseActivity
 */
public class SplashActivity extends BaseActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //返回一个用于显示界面的布局ID
    @Override
    public int getLayoutResID() {
        return R.layout.activity_splash;
    }

    //初始化View
    @Override
    public void initView() {
    }

    //初始化监听器
    @Override
    public void initListener() {
    }

    //初始化数据
    @Override
    public void initData() {
        delayEnterHome();
    }

    //延迟0.5秒进入主页
    private void delayEnterHome() {
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
                enterHome();
            }

        }, 2000);
    }

    //进入首页
    protected void enterHome() {
        finish();
    }

    //单击事件在这个方法中处理
    @Override
    public void onClick(View v, int id) {

    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mHandler.removeCallbacksAndMessages(null);
                enterHome();//进入首页
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
