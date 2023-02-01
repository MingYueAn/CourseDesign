package com.sping.mobileplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore.Video.Media;

import java.io.Serializable;


public class VideoItem implements Serializable {
    // Media._ID,Media.TITLE, Media.DURATION, Media.SIZE, Media.DATA
    private static final long serialVersionUID = -9165728718206582949L;
    private String title;
    private long size;
    private long duration;
    private String data;

    /**
     * 把一个Cursor对象封装成一个VideoItem对象
     *
     * @param cursor
     * @return
     */
    public static VideoItem fromCursor(Cursor cursor) {
        VideoItem item = new VideoItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(Media.TITLE)));
        item.setDuration(cursor.getLong(cursor.getColumnIndex(Media.DURATION)));
        item.setSize(cursor.getLong(cursor.getColumnIndex(Media.SIZE)));
        item.setData(cursor.getString(cursor.getColumnIndex(Media.DATA)));
        return item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
