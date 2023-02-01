package com.sping.mobileplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.bean.LyricBean;
import com.sping.mobileplayer.util.LyricLoader;

import java.util.ArrayList;

public class LyricView extends View {

    //高亮歌词的颜色
    private int highlightColor = Color.RED;
    //默认歌词的颜色
    private int defaultColor = Color.BLACK;
    //高亮歌词的Text大小
    private float highlightSize;
    //默认歌词的大小
    private float defaultSize;
    //歌词数据
    private ArrayList<LyricBean> lyrics;
    private Paint paint;
    //高亮行索引
    private int highlightIndex;
    //高亮行歌词的Y坐标
    private float highlighttY;
    //行高
    private int rowHeight;
    //当前已经播放时间
    private int currentPosition;

    public LyricView(Context context) {
        super(context);
        init();
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        highlightSize = getResources().getDimension(R.dimen.highlight_size);//高亮大小
        defaultSize = getResources().getDimension(R.dimen.default_size);//默认大小
        paint = new Paint();//画笔
        paint.setTextSize(defaultSize);//默认大小
        paint.setColor(defaultColor);//默认颜色
        paint.setAntiAlias(true); // 抗锯齿()
        highlightIndex = 0;//高亮索引
        rowHeight = (getTextHeight("行高") + (int) getResources().getDimension(R.dimen.line_space));
        // 模拟歌词数据
        lyrics = new ArrayList<>();
        lyrics.add(new LyricBean(0, "正在加载歌词"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (lyrics == null) {
            drawCenterText(canvas, "找不到对应的歌词");
            return;
        }
        // 取出高亮行歌词
        LyricBean highLightLyric = lyrics.get(highlightIndex);
        if (highlightIndex != lyrics.size() - 1) {
            // 比例 = 高亮行歌词已近显示的时间/总显示时间
            // 高亮行歌词已经显示时间 = 当前播放时间 - 高亮行的开始显示时间
            int showedTime = currentPosition - highLightLyric.getStartShowPosition();
            // 高亮行总显示时间 = 下一行开始开始显示时间   - 高亮行开始显示时间
            int totalTime = lyrics.get(highlightIndex + 1).getStartShowPosition() - highLightLyric.getStartShowPosition();
            // 要移动的距离 = 比例 *-行高
            float scale = ((float) showedTime) / totalTime;
            float translateY = scale * rowHeight;
            canvas.translate(0, -translateY);
        }
        // 画高亮行歌词
        drawCenterText(canvas, highLightLyric.getLyric());
        // 画高亮行上面的歌词
        for (int i = 0; i < highlightIndex; i++) {
            float y = highlighttY - (highlightIndex - i) * rowHeight;
            drawHorizotalText(canvas, lyrics.get(i).getLyric(), y, false);
        }
        // 画高亮行下面的歌词
        for (int i = highlightIndex + 1; i < lyrics.size(); i++) {
            float y = highlighttY - (highlightIndex - i) * rowHeight;
            drawHorizotalText(canvas, lyrics.get(i).getLyric(), y, false);
        }
    }

    /**
     * 画水平和垂直都居中的文本
     *
     * @param canvas 画布
     * @param text   要画的文本内容
     */
    private void drawCenterText(Canvas canvas, String text) {
        int textHeight = getTextHeight(text);
        highlighttY = getHeight() / 2 + textHeight / 2;
        drawHorizotalText(canvas, text, highlighttY, true);
    }

    /**
     * 画水平居中的文本
     *
     * @param canvas      画布
     * @param text        内容
     * @param y           画在y轴什么位置
     * @param isHighLight 是否是高亮行
     */
    private void drawHorizotalText(Canvas canvas, String text, float y, boolean isHighLight) {
        paint.setColor(isHighLight ? highlightColor : defaultColor);
        paint.setTextSize(isHighLight ? highlightSize : defaultSize);
        int textWidth = getTextWidth(text);
        float x = getWidth() / 2 - textWidth / 2; // 指定把文本画在X轴什么位置
        canvas.drawText(text, x, y, paint);
    }

    /**
     * 获取文本的宽度
     */
    private int getTextWidth(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 获取文本的高度
     */
    private int getTextHeight(String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /**
     * 更新当前播放位置,查找高亮行,重绘
     *
     * @param currentPosition 更新播放位置
     */
    public void updatePosion(int currentPosition) {
        if (lyrics == null) {
            invalidate(); //清除上一首的歌词
            return;
        }
        this.currentPosition = currentPosition;
        // 高亮行: 下一行歌词的开始显示时间> 当前播放的位置 > 歌词开始显示时间
        // if 当前播放位置>歌词显示时间
        // if 是最后一行,当前就是高亮行
        // else if 当前播放的位置< 下一行歌词的开始显示时间,当前就是高亮行
        for (int i = 0; i < lyrics.size(); i++) {
            int startShowPosition = lyrics.get(i).getStartShowPosition();
            if (currentPosition > startShowPosition) {
                if (i == lyrics.size() - 1) {
                    highlightIndex = i;
                    break;
                } else if (currentPosition < lyrics.get(i + 1).getStartShowPosition()) {
                    highlightIndex = i;
                    break;
                }
            }
        }
        invalidate();
    }

    /**
     * 设置音乐路径,这个方法会加载这个音乐路径下的相应的歌词,并解析显示出来
     *
     * @param musicPath data
     */
    public void setMusicPath(String musicPath) {
        highlightIndex = 0;
        lyrics = LyricLoader.loadLyric(musicPath);
    }
}
