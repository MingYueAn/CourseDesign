package com.sping.mobileplayer.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.sping.mobileplayer.R;
import com.sping.mobileplayer.bean.VideoItem;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.util.Logger;
import com.sping.mobileplayer.util.Utils;
import com.sping.mobileplayer.view.MyVideoView;

import java.util.ArrayList;

public class VideoPlayerActivity extends BaseActivity {

    private static final int SHOW_SYSTEM_TIME = 0;//显示系统时间
    private static final int UPDATE_PLAYP_ROGRESS = 1;//更新播放进度
    private static final int HIDE_CTRL_LAYOUT = 2;//隐藏控制面板

    private MyVideoView videoView;//播放界面
    OnSeekBarChangeListener mOnVideoSeekBarChangeListener = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                videoView.seekTo(progress);
            }
        }
    };
    private VideoItem currentVideo;//当前视频
    private TextView tv_title;//视频名
    private ImageView iv_battary;//电量显示
    private TextView tv_system_time;//系统时间
    private TextView tv_current_position;//当前播放位置
    private TextView tv_duration;//总时长
    private SeekBar sb_voice;//音量控制
    private SeekBar sb_video;//进度控制
    private Button btn_pre;//上一个
    private Button btn_next;//下一个
    private Button btn_play;//播放
    private Button btn_fullscreen;//全屏
    private BroadcastReceiver batteryChangeReceiver;
    private int maxVolume;//最大音量
    private AudioManager audioManager;
    OnSeekBarChangeListener mOnVoiceSeekBarChangeListener = new OnSeekBarChangeListener() {

        // 停止拖动SeekBar
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        // 开始拖动SeekBar
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 进度发生改变的时候
        // fromUser 表明是否是由用户出发的
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                setStreamVolume(progress);
            }
        }
    };
    private int currentVolume;
    private GestureDetector gestureDetector;//手势检测器
    private float currentBrightness;
    private ArrayList<VideoItem> videos;//列表
    private int currentPosition;
    private LinearLayout ll_top_ctrl;//顶部控制
    private LinearLayout ll_bottom_ctrl;//底部控制
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_SYSTEM_TIME:
                    showSystemTime();//显示系统时间
                    break;
                case UPDATE_PLAYP_ROGRESS:
                    updatePlayProgress();//更新进度条
                    break;
                case HIDE_CTRL_LAYOUT:
                    showOrHideCtrlLayout();
                    break;
            }

        }

        ;
    };
    SimpleOnGestureListener mOnGestureListener = new SimpleOnGestureListener() {

        //是否是在屏幕左边按下的
        private boolean isDownLeft;

        @Override
        public void onLongPress(MotionEvent e) {
            toggleFullScreen();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //计算在屏幕y方向滑动的距离
            float distanceYY = e1.getY() - e2.getY();
            if (isDownLeft) {
                changeBrightness(distanceYY);// 如果在屏幕左边按下,则改变屏幕亮度
            } else {
                changeVolume(distanceYY);// 如果在屏幕右边按下,则改变音量
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            currentVolume = getStreamVolume();
            isDownLeft = e.getX() < Utils.getScreenWidth(VideoPlayerActivity.this) / 2;
            // 当前的activity按下时亮度
            currentBrightness = getActivityBrightness(VideoPlayerActivity.this);
            return super.onDown(e);
        }
        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            play();
            return true;
        }
        //单击
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            showOrHideCtrlLayout();
            return true;
        }

    };
    private Uri videoUri;
    private ProgressBar pb_loading;
    OnPreparedListener mOnPreparedListener = new OnPreparedListener() {

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoView.start();
            updatePlayBtnBg(); // 更新播放按钮背景图片
            if (videoUri != null) {
                tv_title.setText(videoUri.getPath());// 第三方应用跳转过来
            } else {
                tv_title.setText(currentVideo.getTitle());// 显示视频标题
            }
            tv_duration.setText(Utils.formatMillis(videoView.getDuration()));// 视频总时长
            sb_video.setMax(videoView.getDuration());// 设置seekbar最大值
            updatePlayProgress();
            hideLoading();
            mp.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {

                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    updateVideoSecondProgress(percent);
                }
            });
        }

    };
    /**
     * 视屏播放完会回调这个监听器
     */
    OnCompletionListener mOnCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            videoView.seekTo(0);
            tv_current_position.setText(Utils.formatMillis(0));
            sb_video.setProgress(0);
            updatePlayBtnBg();
            next();
        }
    };
    OnInfoListener onInfoListener = new OnInfoListener() {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {

            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    pb_loading.setVisibility(View.VISIBLE);
                    return true;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    pb_loading.setVisibility(View.GONE);
                    return true;
            }

            return false;
        }
    };
    //返回一个用于显示界面的布局ID
    @Override
    public int getLayoutResID() {
        return R.layout.activity_video_player;
    }
    //初始化View
    @Override
    public void initView() {
        videoView = findView(R.id.video_view);
        tv_title = findView(R.id.tv_title);
        iv_battary = findView(R.id.iv_battery);
        tv_system_time = findView(R.id.tv_system_time);
        tv_current_position = findView(R.id.tv_current_position);
        tv_duration = findView(R.id.tv_duration);
        sb_voice = findView(R.id.sb_voice);
        sb_video = findView(R.id.sb_video);
        btn_pre = findView(R.id.btn_pre);
        btn_next = findView(R.id.btn_next);
        btn_play = findView(R.id.btn_play);
        btn_fullscreen = findView(R.id.btn_fullscreen);
        pb_loading = findView(R.id.pb_loading);
        showSystemTime();
        initCtrlLayout();
    }

    //初始化控制面板
    private void initCtrlLayout() {
        ll_top_ctrl = findView(R.id.ll_top_ctrl);
        ll_bottom_ctrl = findView(R.id.ll_bottom_ctrl);
        // 顶部控制栏的隐藏: Y方向移动控件的高度的负数
        ll_top_ctrl.measure(0, 0);
        ViewHelper.setTranslationY(ll_top_ctrl, -ll_top_ctrl.getMeasuredHeight());
        // 底部控制栏的隐藏,Y方向移动控件的高度
        ll_bottom_ctrl.measure(0, 0);
        ViewHelper.setTranslationY(ll_bottom_ctrl, ll_bottom_ctrl.getMeasuredHeight());
    }
    //显示系统时间
    private void showSystemTime() {
        tv_system_time.setText(DateFormat.format("kk:mm:ss", System.currentTimeMillis()));
        handler.sendEmptyMessageDelayed(SHOW_SYSTEM_TIME, 1000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        unregisterReceiver(batteryChangeReceiver);
        super.onDestroy();
    }
    //初始化监听器
    @Override
    public void initListener() {
        videoView.setOnPreparedListener(mOnPreparedListener);
        videoView.setOnCompletionListener(mOnCompletionListener);
        videoView.setOnInfoListener(onInfoListener);
        sb_voice.setOnSeekBarChangeListener(mOnVoiceSeekBarChangeListener);
        sb_video.setOnSeekBarChangeListener(mOnVideoSeekBarChangeListener);
        gestureDetector = new GestureDetector(this, mOnGestureListener);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initData() {
        videoUri = getIntent().getData();
        if (videoUri != null) {
            // 从第三方跳转过来
            pb_loading.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            btn_next.setEnabled(false);
            btn_pre.setEnabled(false);
        } else {
            // 从视频列表跳转过来的
            videos = (ArrayList<VideoItem>) getIntent().getSerializableExtra(Keys.ITEMS);
            currentPosition = getIntent().getIntExtra(Keys.CURRENT_POSITION_IN_LIST, -1);
            openVideo();
        }
        registerBatteryChangeReceiver();
        initVoice();
    }

    /**
     * 打开一个视屏
     */
    private void openVideo() {
        if (videos == null || videos.isEmpty() || currentPosition == -1) {
            return;
        }
        btn_pre.setEnabled(currentPosition != 0);
        btn_next.setEnabled(currentPosition != videos.size() - 1);
        currentVideo = videos.get(currentPosition);
        String path = currentVideo.getData();
        pb_loading.setVisibility(View.VISIBLE);
        videoView.setVideoPath(path);
    }

    /**
     * 初始化音量
     */
    private void initVoice() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = getStreamVolume();
        sb_voice.setMax(maxVolume);
        sb_voice.setProgress(currentVolume);
    }

    /**
     * 获取当前音量
     */
    private int getStreamVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 设置音量
     *
     * @param value 音量值
     */
    private void setStreamVolume(int value) {
        int flags = 0;// 1-显示系统的音量调节面板,0-不显示系统的音量调节面板
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, value, flags);
    }

    /**
     * 注册电量改变广播接收者
     */
    private void registerBatteryChangeReceiver() {
        batteryChangeReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // 获取电量等级
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                updateBatteryBg(level);
            }
        };
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryChangeReceiver, filter);
    }

    /**
     * 更新电量显示图片
     *
     * @param level 当前的电量级别
     */
    protected void updateBatteryBg(int level) {
        Logger.i(this, "Batterylevel:" + level);
        int resid = R.drawable.ic_battery_0;
        if (level == 0) {
            resid = R.drawable.ic_battery_0;
        } else if (level >= 100) {
            resid = R.drawable.ic_battery_100;
        } else if (level >= 80) {
            resid = R.drawable.ic_battery_80;
        } else if (level >= 60) {
            resid = R.drawable.ic_battery_60;
        } else if (level >= 40) {
            resid = R.drawable.ic_battery_40;
        } else if (level >= 20) {
            resid = R.drawable.ic_battery_20;
        } else if (level >= 10) {
            resid = R.drawable.ic_battery_10;
        }
        iv_battary.setBackgroundResource(resid);
    }

    @Override
    public void onClick(View v, int id) {
        switch (id) {
            case R.id.btn_voice: // 静音按钮
                mute();
                break;
            case R.id.btn_exit: // 退出按钮
                finish();
                break;
            case R.id.btn_pre: // 上一首按钮
                pre();
                break;
            case R.id.btn_play: // 播放按钮
                play();
                break;
            case R.id.btn_next: // 下一首按钮
                next();
                break;
            case R.id.btn_fullscreen:// 全屏按钮
                toggleFullScreen();
                break;
            default:
                break;
        }
    }

    /**
     * 在全屏和默认大小之间切换
     */
    private void toggleFullScreen() {
        videoView.switchFullScreen();
        updateFullScreenBtnBg();
    }

    /**
     * 更新全屏按钮的背景
     */
    private void updateFullScreenBtnBg() {
        int resid;
        if (videoView.isFullScreen()) {
            resid = R.drawable.ic_btn_video_small;// 显示恢复默认大小背景
        } else {
            resid = R.drawable.ic_btn_video_big;// 显示全屏按钮
        }
        btn_fullscreen.setBackgroundResource(resid);
    }

    /**
     * 播放或暂停
     */
    private void play() {
        if (videoView.isPlaying()) {
            videoView.pause();// 如果当前是正在播放的,则暂停
        } else {
            videoView.start(); // 如果当前是暂停的,则播放
        }
        updatePlayBtnBg();
    }

    private void updatePlayBtnBg() {
        int resid;
        if (videoView.isPlaying()) {
            resid = R.drawable.ic_btn_video_pause;// 如果当前是正在播放的,则显示暂停按钮
        } else {
            resid = R.drawable.ic_btn_video_play;// 如果当前是暂停的,则显示播放按钮
        }
        btn_play.setBackgroundResource(resid);
    }

    /**
     * 上一首
     */
    private void pre() {
        if (currentPosition != 0) {
            currentPosition--;
            openVideo();
        }
    }

    /**
     * 下一首
     */
    private void next() {
        if (videos == null) {
            finish();
        }
        if (currentPosition != videos.size() - 1) {
            currentPosition++;
            openVideo();
        }
    }

    /**
     * 静音或者恢复原来的音量
     */
    private void mute() {
        if (getStreamVolume() > 0) {
            // 如果当前音量大于0,则保存一下这个音量然后设置为0
            currentVolume = getStreamVolume();
            setStreamVolume(0);
            sb_voice.setProgress(0);
        } else {
            // 如果当前音量为0,则恢复原来保存的音量
            setStreamVolume(currentVolume);
            sb_voice.setProgress(currentVolume);
        }
    }

    /**
     * 更新视屏缓冲进度
     *
     * @param percent 缓冲进度百分比
     */
    private void updateVideoSecondProgress(int percent) {
        float percentFloat = percent / 100.0f;
        int secondaryProgress = (int) (videoView.getDuration() * percentFloat);
        sb_video.setSecondaryProgress(secondaryProgress);
    }

    ;

    /**
     * 使用渐变的方式慢慢隐藏Loading界面
     */
    protected void hideLoading() {
        pb_loading.setVisibility(View.GONE);
    }

    /**
     * 跟新播放进度
     */
    protected void updatePlayProgress() {
        // 显示当前播放位置
        tv_current_position.setText(Utils.formatMillis(videoView.getCurrentPosition()));
        // 设置当前播放进度
        sb_video.setProgress(videoView.getCurrentPosition());
        handler.sendEmptyMessageDelayed(UPDATE_PLAYP_ROGRESS, 300);
    }

    public boolean onTouchEvent(MotionEvent event) {
        // 把触摸事件传递给手势监听器
        boolean result = gestureDetector.onTouchEvent(event);
        return result;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelHideCtrlLayoutMessage();
                break;
            case MotionEvent.ACTION_UP:
                sendHideCtrlLayoutMessage();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据滑动屏幕的距离改变音量
     *
     * @param distanceY Y方向移动屏幕的距离
     */
    private void changeVolume(float distanceY) {
        // 4.计算滑动的距离等于多少对应的音量
        // a.音量最大值与屏幕高的比例
        float scale = ((float) maxVolume) / Utils.getScreenHeight(VideoPlayerActivity.this);
        // b.计算滑动的距离等于多少对应的音量值
        int moveVolume = (int) (distanceY * scale * 2);
        // 5.在原来的音量的基础上加上计算出来的对应音量值
        int resultVolume = currentVolume + moveVolume;

        if (resultVolume > maxVolume) {
            resultVolume = maxVolume;
        } else if (resultVolume < 0) {
            resultVolume = 0;
        }
        Logger.i(this, "resultVolume:" + resultVolume + "******" + distanceY);
        // 6.使用这个音量值
        setStreamVolume(resultVolume);
        sb_voice.setProgress(resultVolume);
    }

    /**
     * 改变屏幕亮度
     */
    protected void changeBrightness(float distanceY) {
        // 4.计算滑动的距离等于多少对应的亮度
        // a.亮度最大值与屏幕高的比例
        float scale = (1.0f) / Utils.getScreenHeight(VideoPlayerActivity.this);
        // b.计算滑动的距离等于多少对应的亮度值
        float moveBrightness = distanceY * scale * 2;
        // 5.在原来的亮度的基础上加上计算出来的对应亮度值
        float resultBrightness = currentBrightness + moveBrightness;

        if (resultBrightness > 1.0f) {
            resultBrightness = 1.0f;
        } else if (resultBrightness < 0) {
            resultBrightness = 0;
        }
        Logger.i(this, "resultBrightness:" + resultBrightness + "******" + distanceY);
        // 6.使用这个亮度值
        setActivityBrightness(resultBrightness, this);
    }

    /**
     * 显示或隐藏控制面板
     */
    protected void showOrHideCtrlLayout() {
        if (ViewHelper.getTranslationY(ll_top_ctrl) == 0) {
            // 如果控制面板原来是显示的,则隐藏
            // 顶部控制栏的隐藏: Y方向移动控件的高度的负数
            ViewPropertyAnimator.animate(ll_top_ctrl).translationY(-ll_top_ctrl.getHeight());
            // 底部控制栏的隐藏,Y方向移动控件的高度
            ViewPropertyAnimator.animate(ll_bottom_ctrl).translationY(ll_bottom_ctrl.getHeight());
        } else {
            // 如果控制面板原来是隐藏的,则显示
            ViewPropertyAnimator.animate(ll_top_ctrl).translationY(0);
            ViewPropertyAnimator.animate(ll_bottom_ctrl).translationY(0);
            sendHideCtrlLayoutMessage();
        }

    }

    /**
     * 发送隐藏控制面板的消息,5秒后执行
     */
    private void sendHideCtrlLayoutMessage() {
        cancelHideCtrlLayoutMessage();
        handler.sendEmptyMessageDelayed(HIDE_CTRL_LAYOUT, 3000);
    }

    /**
     * 取消隐藏控制面板消息
     */
    private void cancelHideCtrlLayoutMessage() {
        handler.removeMessages(HIDE_CTRL_LAYOUT);
    }

    /**
     * 获取当前activity的屏幕亮度
     *
     * @param activity 当前的activity对象
     * @return 亮度值范围为0-1.0f，如果为-1.0，则亮度与全局同步,没设置亮度默认返回-1。
     */
    public float getActivityBrightness(Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        if (params.screenBrightness < 0) {
            return getScreenBrightness() / 255.0f;
        }
        return params.screenBrightness;
    }

    /**
     * 设置当前activity的屏幕亮度
     *
     * @param paramFloat 0-1.0f,当传入参数-1.0f，当前的activity就又会重新使用全局的亮度属性
     * @param activity   需要调整亮度的activity
     */
    public void setActivityBrightness(float paramFloat, Activity activity) {
        Window localWindow = activity.getWindow();
        WindowManager.LayoutParams params = localWindow.getAttributes();
        params.screenBrightness = paramFloat;
        localWindow.setAttributes(params);
    }

    /**
     * 获得当前屏幕亮度值
     *
     * @return 0--255
     */
    public int getScreenBrightness() {
        int screenBrightness = -1;
        try {
            screenBrightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

}
