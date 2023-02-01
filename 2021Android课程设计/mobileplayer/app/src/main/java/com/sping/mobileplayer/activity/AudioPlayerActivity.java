package com.sping.mobileplayer.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.adapter.AudioPlayListAdapterRV;
import com.sping.mobileplayer.bean.AudioItem;
import com.sping.mobileplayer.interfaces.IPlayService;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.service.AudioPlayService;
import com.sping.mobileplayer.service.AudioPlayService.MyBinder;
import com.sping.mobileplayer.util.Logger;
import com.sping.mobileplayer.util.Utils;
import com.sping.mobileplayer.view.LyricView;

import java.util.ArrayList;

public class AudioPlayerActivity extends BaseActivity {

    private static final int UPDATE_PLAY_TIME = 1;
    private ServiceConnection conn;//服务连接
    private BroadcastReceiver updateUIReceiver;//
    private IPlayService playService;//
    private Button btn_play;//播放
    private TextView tv_title;//歌名
    private TextView tv_artist;//歌手
    private AnimationDrawable animationDrawable;//动态
    private TextView tv_play_time;//播放时间
    private SeekBar sb_audio;//进度条
    private AudioItem currentPlayingAudio;//当前播放音频
    private Button btn_play_mode;//播放模式
    private BroadcastReceiver audioReleaseReceiver;//释放广播接收
    private LyricView lyric_view;//歌词
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case UPDATE_PLAY_TIME:
                    updatePlayTime();
                    break;
                default:
                    break;
            }
        }
    };
    private String clickAudioTitle;//单击音频标题
    private ArrayList<AudioItem> audioList;//音频列表
    private int currentPlayIndex = -1;//当前播放索引
    private AudioPlayListAdapterRV mPlayListRVAdapter;//自定义适配器
    private RecyclerView playListRecyclerView;//列表
    private LinearLayout ll_rootView;//根界面
    private PopupWindow popupWindow;//弹出

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    public void initView() {
        ll_rootView = findView(R.id.ll_rootview);
        btn_play = findView(R.id.btn_play);
        tv_title = findView(R.id.tv_title);
        tv_artist = findView(R.id.tv_artist);
        tv_play_time = findView(R.id.tv_play_time);
        lyric_view = findView(R.id.lyric_view);
        ImageView iv_vision = findView(R.id.iv_vision);
        animationDrawable = (AnimationDrawable) iv_vision.getBackground();
        sb_audio = findView(R.id.sb_audio);
        btn_play_mode = findView(R.id.btn_play_mode);

    }

    @Override
    public void initListener() {
        sb_audio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    playService.seekTo(progress);
                }
            }
        });

    }

    @Override
    public void initData() {
        connectService();
        registerAudioReleaseReceiver();
        registerUpdateUIReceiver();
    }
    /**
     * 连接服务
     */
    @SuppressWarnings("unchecked")
    private void connectService() {
        Intent intent = getIntent();
        String action = intent.getAction();

        Intent intentService = new Intent(this, AudioPlayService.class);
        if (action != null) {
            //是从通知进来的
            updateUI(currentPlayingAudio);
        } else {
            //不是从通知进来的
            audioList = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEMS);
            currentPlayIndex = intent.getIntExtra(Keys.CURRENT_POSITION_IN_LIST, -1);
            // AudioFragment过来的数据向下传
            intentService.putExtra(Keys.ITEMS, audioList);
            intentService.putExtra(Keys.CURRENT_POSITION_IN_LIST, currentPlayIndex);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intentService);
            } else {
                startService(intentService);
            }
        }
        conn = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                playService = ((MyBinder) binder).playService;
                currentPlayingAudio = playService.getCurrentAudioItem();
                audioList = playService.getAudioItemList();
                currentPlayIndex = playService.getCurrentPlayIndex();
                boolean isClickPlaying = false;
                if (currentPlayIndex != -1 && audioList != null) {
                    clickAudioTitle = audioList.get(currentPlayIndex).getTitle();
                    if (currentPlayingAudio != null) {
                        // 点击的音频正在播放
                        isClickPlaying = clickAudioTitle.equals(currentPlayingAudio.getTitle());
                    }
                }
                if (isClickPlaying) {
                    // 正在播放的歌曲和点击的歌曲是同一首不要重新打开
                    updateUI(currentPlayingAudio);
                } else {
                    playService.openAudio();
                }
            }
        };
        bindService(intentService, conn, BIND_AUTO_CREATE);
    }
    /**
     * 注册停止更新UI的接收者
     */
    private void registerAudioReleaseReceiver() {
        audioReleaseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handler.removeMessages(UPDATE_PLAY_TIME);
                Logger.i("ReleaseUI onReceive", "暂停更新UI");
            }
        };
        registerReceiver(audioReleaseReceiver, new IntentFilter(AudioPlayService.ACTION_AUDIO_RELEASE));
    }
    /**
     * 注册更新UI的接收者
     */
    private void registerUpdateUIReceiver() {
        updateUIReceiver = new BroadcastReceiver() {
            @SuppressWarnings("unchecked")
            @Override
            public void onReceive(Context context, Intent intent) {
                audioList = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEMS);
                currentPlayIndex = (Integer) intent.getSerializableExtra(Keys.CURRENT_POSITION_IN_LIST);
                try {
                    // audios 可能为空,currentPlayIndex可能越界
                    AudioItem item = audioList.get(currentPlayIndex);
                    updateUI(item);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        registerReceiver(updateUIReceiver, new IntentFilter(AudioPlayService.ACTION_UPDATE_UI));
    }

    @Override
    public void onClick(View v, int id) {
        switch (id) {
            //播放按钮
            case R.id.btn_play:
                play();
                break;
            //上一首按钮
            case R.id.btn_pre:
                playService.pre();
                break;
            //下一首按钮
            case R.id.btn_next:
                playService.next();
                break;
            //播放模式按钮
            case R.id.btn_play_mode:
                switchPlayMode();
                break;
            //播放列表按钮
            case R.id.btn_playlist:
                showPlayListPopupWindow();
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Keys.IS_FROM__AUDIO_PLAYER, true);
                startActivity(intent);
                finish();
                break;
            default:
                // 如果单击的不是返回按钮,则还是由子类去做处理
                onClick(v, v.getId());
                break;
        }
    }
    //切换播放模式
    private void switchPlayMode() {
        int currentPlayMode = playService.switchPlayMode();
        updatePlayModeBtnBg(currentPlayMode);//更新播放模式
    }

    //播放
    private void play() {
        if (playService.isPlaying()) {
            playService.pause();
        } else {
            playService.start();
        }
        updatePlayBtnBg();//更新播放按钮
        updateAnimation();//视觉动画
    }
    //更新UI
    protected void updateUI(AudioItem item) {
        if (item == null) {
            return;
        }
        Logger.i("updateUI", "开始更新UI");
        if (mPlayListRVAdapter != null) {
            mPlayListRVAdapter.updateCurrentPlayListIndex(currentPlayIndex);
        }
        updatePlayBtnBg(); // 更新播放按钮背景
        updateAnimation(); // 更新动画
        tv_title.setText(item.getTitle()); // 显示歌曲名称
        tv_artist.setText(item.getArtist());// 显示艺术家
        updatePlayModeBtnBg(playService.getCurrentPlayMode());
        lyric_view.setMusicPath(item.getData());
        if (playService != null && playService.isPlaying()) {
            sb_audio.setMax(playService.getDuration());
            updatePlayTime();
        }
    }

    //更新播放时间
    private void updatePlayTime() {
        tv_play_time.setText(String.format("%s/%s", Utils.formatMillis(playService.getCurrentPosition()), Utils.formatMillis(playService.getDuration())));
        sb_audio.setProgress(playService.getCurrentPosition());
        lyric_view.updatePosion(playService.getCurrentPosition());
        handler.sendEmptyMessageDelayed(UPDATE_PLAY_TIME, 200);
    }

    //更新播放模式背景
    private void updatePlayModeBtnBg(int currentPlayMode) {
        int resid;
        switch (currentPlayMode) {
            case AudioPlayService.PLAY_MODE_ORDER:
                resid = R.drawable.ic_btn_audioplaymode_repeat;
                break;
            case AudioPlayService.PLAY_MODE_SINGLE:
                resid = R.drawable.ic_btn_audioplaymode_single;
                break;
            case AudioPlayService.PLAY_MODE_RANDOM:
                resid = R.drawable.ic_btn_audioplaymode_random;
                break;
            default:
                throw new RuntimeException("奇怪了,当前播放模式变成了:" + currentPlayMode);
        }
        btn_play_mode.setBackgroundResource(resid);
    }

    //更新播放按钮
    private void updatePlayBtnBg() {
        int resid;
        if (playService.isPlaying()) {
            resid = R.drawable.ic_btn_audio_pause;// 如果当前是播放,则显示暂停按钮
        } else {
            resid = R.drawable.ic_btn_audio_play;// 如果当前是暂停,则显示播放按钮
        }
        btn_play.setBackgroundResource(resid);
    }

    // 视觉动画
    private void updateAnimation() {
        if (playService.isPlaying()) {
            animationDrawable.start();// 如果当前是播放,则开始动画
        } else {
            animationDrawable.stop();// 如果当前是暂停,则停止
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        unregisterReceiver(updateUIReceiver);
        unregisterReceiver(audioReleaseReceiver);
        handler.removeCallbacksAndMessages(null);
    }

    //打开播放列表
    public void showPlayListPopupWindow() {
        initListView();
        int offsetY = (int) getResources().getDimension(R.dimen.popupwinY);
        int titleBarH = (int) getResources().getDimension(R.dimen.title_bar_height);
        // 获取屏幕宽高
        int width = Utils.getScreenWidth(this) * 2 / 3;
        int height = Utils.getScreenHeight(this) - offsetY - titleBarH * 3 / 2;
        popupWindow = new PopupWindow(playListRecyclerView, width, height, true);
        // 3 设置点击外部区域,自动隐藏
        popupWindow.setOutsideTouchable(true);
        // 3 指定popupwindow的背景(设置背景点击回退按钮才有响应)
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 4 指定popupwindow所在位置(挂载在哪个控件上,在父控件上的位置)
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.showAtLocation(ll_rootView, Gravity.BOTTOM | Gravity.END, 0, offsetY);
    }

    //播放列表
    private void initListView() {
        playListRecyclerView = (RecyclerView) View.inflate(this, R.layout.fragment_audio_playbg, null);
        if (audioList != null) {
            if (mPlayListRVAdapter == null) {
                mPlayListRVAdapter = new AudioPlayListAdapterRV(this, playService);
            }
            playListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            playListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            //自定义recyclerView分割线,V7:25的功能
            DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.recyclerview_divider));
            playListRecyclerView.addItemDecoration(divider);
            playListRecyclerView.setAdapter(mPlayListRVAdapter);
            if (currentPlayIndex >= 0 && currentPlayIndex < audioList.size()) {
                //显示正在播放的歌曲
                playListRecyclerView.scrollToPosition(currentPlayIndex);
            }
        }
    }


}
