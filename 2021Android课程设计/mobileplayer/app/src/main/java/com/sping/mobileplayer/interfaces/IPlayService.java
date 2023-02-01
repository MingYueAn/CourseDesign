package com.sping.mobileplayer.interfaces;

import com.sping.mobileplayer.bean.AudioItem;

import java.util.ArrayList;

/**
 * 音乐播放服务接口
 */
public interface IPlayService {

    void start();//播放

    void pause();//暂停

    void pre();//上一首

    void next();//下一首

    void openAudio();//打开一个音频

    boolean isPlaying();//是否正在播放

    int getCurrentPosition();//获取当前播放位置

    int getDuration();//获取音频总时长

    void seekTo(int msec);//跳转

    int switchPlayMode();//切换播放模式

    int getCurrentPlayMode();//获取当前播放模式

    int getCurrentPlayIndex();//获取当前播放的音频在列表中的索引

    AudioItem getCurrentAudioItem();//获取当前播放的音频JavaBean

    ArrayList<AudioItem> getAudioItemList();//获取当音频JavaBeanList

    void updatePlayList(ArrayList<AudioItem> audios);//更新歌单数据

    void updatePlayList(int currentPlayIndex);//更新播放歌单索引
}
