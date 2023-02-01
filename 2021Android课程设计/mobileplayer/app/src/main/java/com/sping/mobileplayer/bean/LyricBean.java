package com.sping.mobileplayer.bean;

public class LyricBean {

    private int startShowPosition;//歌词开始显示时间
    private String lyric;//歌词内容

    public LyricBean(int startShowPosition, String lyric) {
        super();
        this.startShowPosition = startShowPosition;
        this.lyric = lyric;
    }

    public int getStartShowPosition() {
        return startShowPosition;
    }

    public void setStartShowPosition(int startShowPosition) {
        this.startShowPosition = startShowPosition;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    @Override
    public String toString() {
        return "LyricBean [startShowPosition=" + startShowPosition + ", lyric=" + lyric + "]";
    }

}
