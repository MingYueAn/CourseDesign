package com.sping.mobileplayer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.interfaces.UIOperation;
import com.sping.mobileplayer.util.Utils;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * activity 的基类,其他activity应该继承这个类
 * 继承：FragmentActivity
 * 接口：UIOperation, EasyPermissions.PermissionCallbacks
 * EasyPermissions库是一个方便开发者为App高效处理危险权限的库
 */
public abstract class BaseActivity extends FragmentActivity implements UIOperation, EasyPermissions.PermissionCallbacks {

    //请求码,请求权限的唯一标示
    public static final int REQUEST_CODE = 1001;
    //普通权限和危险权限，危险权限需要用户授权，危险权限共有9个权限组，每个权限组又包括若干子项，
    public final static String[] PERMISSIONS = {//存储权限组
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏

        setContentView(getLayoutResID());// 多态
        View rootView = findViewById(android.R.id.content);// android.R.id.content这个id可以获取到activity的根view
        Utils.setButtonOnClickListener(rootView, this);//查找Button/ImageButtom并设置单击监听器
        initView();//UIOperation的初始化view
        initListener();//UIOperation的初始化监听器

        //检查权限
        if (!checkPermission(this, PERMISSIONS)) {
            //未获取权限
            requestPermission(this, "播放媒体需要读写外部存储权限", REQUEST_CODE, PERMISSIONS);
        } else {
            //已获取权限
            initData();
        }
    }

    /**
     * 减少findViewById的使用，减少强制转换
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) findViewById(id);
        return view;
    }

    /////
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:// 处理共同操作
                finish();
                break;
            default:
                // 如果单击的不是返回按钮,则还是由子类去做处理
                onClick(v, v.getId());
                break;
        }
    }

    /**
     * 从软件设置界面，返回当前程序时候
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //当从软件设置界面，返回当前程序时候
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // 执行Toast显示或者其他逻辑处理操作
            Utils.showToast(this, "感谢你的授权");
        }
    }

    /**
     * 1.检查权限
     *
     * @param context 上下文
     * @param perms   权限字符串数组
     *                return true：已获取权限
     *                return false：未获取权限
     */
    public static boolean checkPermission(Activity context, String[] perms) {
        return EasyPermissions.hasPermissions(context, perms);
    }

    /**
     * 2.请求权限
     *
     * @param context     上下文
     * @param tip         请求权限时的用户提示
     * @param requestCode 请求码,请求权限的唯一标示
     * @param perms       权限字符串数组
     */
    public static void requestPermission(Activity context, String tip, int requestCode, String[] perms) {
        EasyPermissions.requestPermissions(context, tip, requestCode, perms);
    }

    /**
     * 3.请求权限成功
     *
     * @param requestCode 请求码,请求权限的唯一标示
     * @param perms       权限list
     */
    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Utils.showToast(this, "用户授权成功");
        initData();//UIOperation的初始化数据
    }

    /**
     * 4.请求权限失败
     *
     * @param requestCode 请求码,请求权限的唯一标示
     * @param perms       权限list
     */
    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Utils.showToast(this, "用户授权失败");
        //如果在权限弹窗中，用户勾选了'NEVER ASK AGAIN.'或者'不在提示'，且拒绝权限，需要跳转到设置界面进行手动开启
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * 5.重写onRequestPermissionsResult，用于接受请求结果
     *
     * @param requestCode  请求码,请求权限的唯一标示
     * @param permissions  权限字符串数组
     * @param grantResults 授权结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
