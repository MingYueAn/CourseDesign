package com.sping.mobileplayer.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.sping.mobileplayer.bean.AudioItem;
import com.sping.mobileplayer.interfaces.IPlayService;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.util.Logger;

import java.util.ArrayList;
import java.util.Random;

public class AudioPlayService extends Service implements IPlayService {

    public static final int PLAY_MODE_ORDER = 1;// 播放模式:顺序播放
    public static final int PLAY_MODE_SINGLE = 2;//播放模式:单曲播放
    public static final int PLAY_MODE_RANDOM = 3;//播放模式:随机播放
    public static final String ACTION_AUDIO_RELEASE = "actionAudioRelease";//音频释放广播
    //通知播放的Action
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_PAUSE = "pause";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREV = "previous";
    public static final String ACTION_NOTIFY = "notify";
    private static final String SESSION_TAG = "com.sping.mobileplayer";
    public static String ACTION_UPDATE_UI = "updateUI";//更新UI的广播Action
    private int currentPlayMode;//当前播放模式
    /**
     * 配置文件
     */
    private SharedPreferences sp;
    private ArrayList<AudioItem> audioList;//当前音频列表
    private int currentInListPosition;//当前输入列表位置
    private AudioItem currentAudio;//当前音频
    private MediaPlayer mMediaPlayer;//播放器，使用MediaPlayer播放音频
    private Random random;//随机
    private AudioNotification mAudioNotification;//音频通知
    //音频焦点变化的监听器
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS://不明种类失去焦点
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT://短时失去焦点
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK://短时但可以据需播放的失去焦点
                    pause();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN:
                    //start();应该用状态控制,否则手动暂停后别的音乐播放器关闭时会自动开始播放
                    break;
            }

        }
    };
    private MediaSession mMediaSession;//MediaSession 框架是Google推出的专门解决媒体播放时界面和服务的通讯问题
    private BecomingNoisyReceiver mNoisyReceiver;//广播接收者
    private IntentFilter mNoisyIntentFilter;//意图过滤器
    private OnPreparedListener mPreparedListener = new OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            start();// 开始播放
        }
    };
    private AudioManager mAudioManager;//音频管理
    //播放完成
    OnCompletionListener mCompletionListener = new OnCompletionListener() {

        @Override
        public void onCompletion(MediaPlayer mp) {
            notifyUIRelease();
            if (currentPlayMode == PLAY_MODE_SINGLE) {
                openAudio();//单曲循环播放模式
            } else {
                next();//列表循环播放模式
            }

        }
    };
    private MediaSession.Callback mSessionCallback = new MediaSession.Callback() {

        @Override
        public void onPlay() {
            super.onPlay();
            start();
        }

        @Override
        public void onPause() {
            super.onPause();
            pause();
        }

        @Override
        public void onSkipToNext() {
            super.onSkipToNext();
            next();
        }

        @Override
        public void onSkipToPrevious() {
            super.onSkipToPrevious();
            pre();
        }

        @Override
        public void onSeekTo(long pos) {
            super.onSeekTo(pos);
            seekTo((int) pos);
        }

        @Override
        public boolean onMediaButtonEvent(@NonNull Intent mediaButtonIntent) {
            return super.onMediaButtonEvent(mediaButtonIntent);
        }
    };

    @Override
    public void onCreate() {
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        random = new Random();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMediaSession = new MediaSession(this, AudioPlayService.SESSION_TAG);
        //设置媒体播放回调
        mMediaSession.setCallback(mSessionCallback);
        //设置可接受媒体控制
        mMediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mNoisyIntentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        mNoisyReceiver = new BecomingNoisyReceiver();
        mAudioNotification = new AudioNotification(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentPlayMode = sp.getInt(Keys.CURRENT_PLAY_MODE, PLAY_MODE_ORDER);
        if (intent != null) {
            String action = intent.getAction();
            if (action != null) {
                switch (action) {
                    case ACTION_PLAY:
                        start();
                        break;
                    case ACTION_PAUSE:
                        pause();
                        break;
                    case ACTION_NEXT:
                        next();
                        break;
                    case ACTION_PREV:
                        pre();
                        break;
                    case ACTION_NOTIFY:
                        break;//从通知启动Activity
                }
            } else {
                try {
                    audioList = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEMS);
                    currentInListPosition = intent.getIntExtra(Keys.CURRENT_POSITION_IN_LIST, -1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        MyBinder binder = new MyBinder();
        binder.playService = this;
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    // 此方法 没调用onUnbind()和 stopService(),不会被执行
    @Override
    public void onDestroy() {
        quitService();
    }

    // 通过RecentsApp滑动关闭应用程序
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        quitService();
    }

    private void quitService() {
        if (mNoisyReceiver != null) {
            unregisterReceiver(mNoisyReceiver);
            mNoisyReceiver = null;
        }
        mAudioNotification.stopNotify(this);
        release();
        mAudioManager = null;
        stopSelf();
    }

    /**
     * 打开一个音频
     */
    @Override
    public void openAudio() {
        if (audioList == null || audioList.isEmpty() || currentInListPosition == -1) {
            return;
        }
        notifyUIRelease();
        currentAudio = audioList.get(currentInListPosition);
        String path = currentAudio.getData();
        Logger.i("openAudio", "release()");
        release();
        try {
            if (mAudioManager != null) {
                mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 释放
     */
    private void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
            if (mAudioManager != null) {
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }
    }

    /**
     * 将当前播放的音乐Item转化为MediaMetadata
     *
     * @return MediaMetadata对象
     */
    private MediaMetadata getMediaData(AudioItem audioItem) {
        MediaMetadata.Builder builder = new MediaMetadata.Builder();
        builder.putString(MediaMetadata.METADATA_KEY_TITLE, audioItem.getTitle())
                .putString(MediaMetadata.METADATA_KEY_ARTIST, audioItem.getArtist())
                .putLong(MediaMetadata.METADATA_KEY_DURATION, getDuration());
        return builder.build();
    }

    /**
     * 通知即将释放音频资源,不要获取进度
     */
    private void notifyUIRelease() {
        sendBroadcast(new Intent(ACTION_AUDIO_RELEASE));
    }

    @Override
    public void start() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            notifyUpdateUI();
            // 1.填充数据
            mMediaSession.setMetadata(getMediaData(currentAudio));
            // 2.要先填充数据,否则通知发不出来
            mAudioNotification.notifyStart(this);
            registerReceiver(mNoisyReceiver, mNoisyIntentFilter);
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            notifyUpdateUI();
            mAudioNotification.notifyPause(this);
            notifyUIRelease();
        }
    }

    @Override
    public void pre() {
        if (mMediaPlayer == null) {
            return;
        }
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER:
                if (currentInListPosition != 0) {
                    currentInListPosition--;
                } else {
                    currentInListPosition = audioList.size() - 1;
                }
                break;
            case PLAY_MODE_SINGLE:
                break;
            case PLAY_MODE_RANDOM:
                currentInListPosition = random.nextInt(audioList.size());
                break;
            default:
                throw new RuntimeException("奇怪了,当前播放模式变成了:" + currentPlayMode);
        }
        openAudio();
    }

    @Override
    public void next() {
        if (mMediaPlayer == null) {
            return;
        }
        switch (currentPlayMode) {
            case PLAY_MODE_SINGLE:// 单曲循环播放模式,只在自动播放完后才体现
            case PLAY_MODE_ORDER:
                if (audioList != null) {
                    if (currentInListPosition != audioList.size() - 1) {
                        currentInListPosition++;
                    } else {
                        currentInListPosition = 0;
                    }
                }
                break;
            case PLAY_MODE_RANDOM:
                currentInListPosition = random.nextInt(audioList.size());
                break;
            default:
                throw new RuntimeException("奇怪了,当前播放模式变成了:" + currentPlayMode);
        }
        openAudio();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    /**
     * 通知界面更新
     */
    protected void notifyUpdateUI() {
        Intent intent = new Intent(ACTION_UPDATE_UI);
        intent.putExtra(Keys.ITEMS, audioList);
        intent.putExtra(Keys.CURRENT_POSITION_IN_LIST, currentInListPosition);
        sendBroadcast(intent);
    }

    @Override
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public void seekTo(int msec) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(msec);
        }
    }

    @Override
    public int switchPlayMode() {
        switch (currentPlayMode) {
            case PLAY_MODE_ORDER: // 如果当前是顺序播放,则切换成单曲播放
                currentPlayMode = PLAY_MODE_SINGLE;
                break;
            case PLAY_MODE_SINGLE: // 如果当前是单曲播放,则切换成随机播放
                currentPlayMode = PLAY_MODE_RANDOM;
                break;
            case PLAY_MODE_RANDOM: // 如果当前是随机播放,则切换成顺序播放
                currentPlayMode = PLAY_MODE_ORDER;
                break;
            default:
                throw new RuntimeException("奇怪了,当前播放模式变成了:" + currentPlayMode);
        }
        sp.edit().putInt(Keys.CURRENT_PLAY_MODE, currentPlayMode).apply();
        return currentPlayMode;
    }

    /**
     * 返回当前播放模式
     */
    @Override
    public int getCurrentPlayMode() {
        return currentPlayMode;
    }

    @Override
    public ArrayList<AudioItem> getAudioItemList() {
        return audioList;
    }

    @Override
    public AudioItem getCurrentAudioItem() {
        return currentAudio;
    }

    @Override
    public int getCurrentPlayIndex() {
        return currentInListPosition;
    }

    @Override
    public void updatePlayList(ArrayList<AudioItem> audios) {
        if (audios != null) {
            audioList = audios;
            notifyUpdateUI();
        }
    }

    @Override
    public void updatePlayList(int currentPlayIndex) {
        // 播放列表没变,仅播放索引改变
        if (audioList != null && audioList.size() > 0) {
            if (currentPlayIndex < 0) {
                currentPlayIndex = 0;
            } else if (currentPlayIndex > audioList.size() - 1) {
                currentPlayIndex = audioList.size() - 1;
            }
            currentInListPosition = currentPlayIndex;
        }
    }

    public MediaSession getMediaSession() {
        return mMediaSession;
    }

    public class MyBinder extends Binder {
        public IPlayService playService;
    }

    private class BecomingNoisyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            switch (action) {
                case AudioManager.ACTION_AUDIO_BECOMING_NOISY:
                    pause();
                    break;
            }
        }
    }
}
