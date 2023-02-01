package com.sping.mobileplayer.util;

import com.sping.mobileplayer.bean.LyricBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LyricLoader {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // * 歌词文件名和音乐文件名相同
        // * 把音乐的文件名拼接 lrc,txt
        // * 读取这些文件
        // * 解析一行一行的歌词

        String musicPath = "E:\\Android资料\\Android全套\\手机影音项目\\资料\\视频和音乐资源\\test\\audio\\TongHua.mp3";

        ArrayList<LyricBean> lyrics = loadLyric(musicPath);

        for (LyricBean lyricBean : lyrics) {

            System.out.println(lyricBean);
        }

    }

    public static ArrayList<LyricBean> loadLyric(String musicPath) {
        ArrayList<LyricBean> lyrics = null;

        // 删除.mp3 后缀
        String prefix = musicPath.substring(0, musicPath.lastIndexOf("."));

        // 拼接 歌词文件
        File lrcFile = new File(prefix + ".lrc");
        File LRCFile = new File(prefix + ".LRC");
        File txtFile = new File(prefix + ".txt");

        if (lrcFile.exists()) {
            lyrics = readFile(lrcFile);
        } else if (LRCFile.exists()) {
            lyrics = readFile(LRCFile);
        } else if (txtFile.exists()) {
            lyrics = readFile(txtFile);
        }

        if (lyrics == null) {
            return null;
        }

        // 按时间的先后顺序排序
        Collections.sort(lyrics, new Comparator<LyricBean>() {

            @Override
            public int compare(LyricBean o1, LyricBean o2) {

                return Integer.valueOf(o1.getStartShowPosition()).compareTo(
                        o2.getStartShowPosition());
            }
        });

        return lyrics;
    }

    /**
     * 读取歌词文件
     *
     * @param lrcFile
     * @return
     */
    private static ArrayList<LyricBean> readFile(File lrcFile) {
        ArrayList<LyricBean> lyrics = new ArrayList<LyricBean>();
        BufferedReader reader = null;
        try {
            InputStream in = new FileInputStream(lrcFile);
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            String line;
            while ((line = reader.readLine()) != null) {
                // 读到一行歌词 [03:34.32][03:06.21][02:37.98]幸福和快乐是结局
                String[] strings = line.split("]");
                // 读取歌词文本
                String lyricText = strings[strings.length - 1];
                // 遍历所有时间
                for (int i = 0; i < strings.length - 1; i++) {
                    int startShowPosition = parseTime(strings[i]);
                    lyrics.add(new LyricBean(startShowPosition, lyricText));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return lyrics;
    }

    // 解析 [03:34.32 这样的时间,解析成毫秒值
    private static int parseTime(String time) {
        String minuteStr = time.substring(1, time.indexOf(":"));
        String secondStr = time.substring(time.indexOf(":") + 1,
                time.indexOf(":") + 3);
        String millisStr = time.substring(time.indexOf(".") + 1,
                time.indexOf(".") + 3);

        int minuteMillis = Integer.parseInt(minuteStr) * 60 * 1000;
        int secondMillis = Integer.parseInt(secondStr) * 1000;
        int mMillis = Integer.parseInt(millisStr) * 10;

        return minuteMillis + secondMillis + mMillis;
    }

}
