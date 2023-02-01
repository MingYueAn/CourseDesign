package com.sping.mobileplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.sping.mobileplayer.R;
import com.sping.mobileplayer.adapter.MainAdapter;
import com.sping.mobileplayer.fragment.AudioFragment;
import com.sping.mobileplayer.fragment.VideoFragment;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.util.Utils;

import java.util.ArrayList;

/**
 * MainActivity，主界面
 * 继承：BaseActivity
 */
public class MainActivity extends BaseActivity {

    //定义是否退出的标志
    private boolean isExit = false;
    //定义接收用户发送信息的handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //标记用户不退出状态
            isExit = false;
        }
    };

    private TextView tv_video;//视频
    private TextView tv_audio;//音乐
    private ViewPager view_pager;//视图翻页工具，提供了多页面切换的效果
    private View view_indicator;//指示器
    private int indicatorWidth;//指示器宽度

    /**
     * 监听手机物理按键单击事件（退出时需要按两次返回按钮）
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //当isExit=false时，提示用户再次按键
            if (!isExit) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //当用户没有在2秒内再次按返回键时，发送消息标记用户为不退出状态
                handler.sendEmptyMessageDelayed(0, 2000);
            } else {
                //当isExit=true时，退出程序
                finish();
                System.exit(0);
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //解决按home，再次点击图标重新启动的问题 !=0 已经存在任务栈, == 0 不存在Activity实例
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) == 0) {
            //进入闪屏页
            Intent intent = new Intent(this, SplashActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
    }

    //返回一个用于显示界面的布局ID
    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    //初始化View
    @Override
    public void initView() {
        tv_video = findView(R.id.tv_video);
        tv_audio = findView(R.id.tv_audio);
        view_indicator = findView(R.id.view_indicator);
        view_pager = findView(R.id.view_pager);
        //初始化指示器
        int screenWidth = Utils.getScreenWidth(this);
        indicatorWidth = screenWidth / 2;
        view_indicator.getLayoutParams().width = indicatorWidth;
        view_indicator.requestLayout();//通知这个view去更新它的布局参数
    }

    //初始化监听器
    @Override
    public void initListener() {
        tv_video.setOnClickListener(this);
        tv_audio.setOnClickListener(this);
        view_pager.setOnPageChangeListener(new OnPageChangeListener() {

            // 当页面被选择的时候
            @Override
            public void onPageSelected(int position) {
                changeTitleTextState(position == 0);
            }

            // 当页面滚动的时候
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滚动指示线
                float translationX = indicatorWidth * position + indicatorWidth * positionOffset;
                ViewHelper.setTranslationX(view_indicator, translationX);
            }

            // 当页面滚动状态发生改变的时候
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    //初始化数据
    @Override
    public void initData() {
        // 默认选中视频
        changeTitleTextState(true);
        // 初始化ViewPager
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new AudioFragment());//音频
        fragments.add(new VideoFragment());//视频
        //设置adapter
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(adapter);
        //从音乐界面回来,返回到音乐fragment
        if (getIntent().getBooleanExtra(Keys.IS_FROM__AUDIO_PLAYER, false)) {
            view_pager.setCurrentItem(0);
        }
    }

    //单击事件
    @Override
    public void onClick(View v, int id) {
        switch (id) {
            case R.id.tv_video:
                view_pager.setCurrentItem(0);
                break;
            case R.id.tv_audio:
                view_pager.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

    /**
     * 改变标题状态
     *
     * @param isSelectVideo 是否选择了视频
     */
    private void changeTitleTextState(boolean isSelectVideo) {
        // 改变文本大小
        scaleTitle(isSelectVideo ? 1.3f : 1.0f, tv_video);
        scaleTitle(!isSelectVideo ? 1.3f : 1.0f, tv_audio);
    }

    /**
     * 缩放标题
     *
     * @param scale   缩放的比例
     * @param textViw
     */
    private void scaleTitle(float scale, TextView textViw) {
        ViewPropertyAnimator.animate(textViw).scaleX(scale).scaleY(scale).setDuration(200);
    }

}
