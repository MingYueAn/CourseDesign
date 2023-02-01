package com.sping.mobileplayer.interfaces;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * UI操作接口
 */
public interface UIOperation extends OnClickListener {

    int getLayoutResID();//返回一个用于显示界面的布局ID

    void initView();//初始化View

    void initListener();//初始化监听器

    void initData();//初始化数据

    /**
     * 单击事件在这个方法中处理
     *
     * @param v  单击的控件
     * @param id 单击控件的id
     */
    void onClick(View v, int id);
}
