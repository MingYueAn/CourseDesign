package com.sping.mobileplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore.Video.Media;

import java.io.Serializable;


public class AudioItem implements Serializable {
    // Media._ID,Media.TITLE,Media.ARTIST,Media.DATA
    private static final long serialVersionUID = -4842070562966592323L;
    private String title;
    private String artist;
    private String data;

    /**
     * 把一个Cursor对象封装成一个AudioItem对象
     *
     * @param cursor
     * @return
     */
    public static AudioItem fromCursor(Cursor cursor) {
        AudioItem item = new AudioItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(Media.TITLE)));
        item.setArtist(cursor.getString(cursor.getColumnIndex(Media.ARTIST)));
        item.setData(cursor.getString(cursor.getColumnIndex(Media.DATA)));
        return item;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
