package com.sping.mobileplayer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.sping.mobileplayer.util.Utils;

public class MyVideoView extends VideoView {

    private boolean isFullScreen = false;
    private int screenWidth = Utils.getScreenWidth(getContext());
    private int screenHeight = Utils.getScreenHeight(getContext());

    public MyVideoView(Context context) {
        super(context);
    }
    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isFullScreen) {
//			Logger.i(this, "onMeasure:w" + screenWidth + ",h:" + screenHeight);
            setMeasuredDimension(screenWidth, screenHeight);
        } else {
//			Logger.i(this, "onMeasure:w" + widthMeasureSpec + ",h:" + heightMeasureSpec);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
    /**
     * 切换全屏和默认大小
     */
    public void switchFullScreen() {
        if (isFullScreen) {
            // 切换为默认比例显示
            isFullScreen = false;
        } else {
            // 切换为全屏显示
            isFullScreen = true;
        }
        requestLayout();
    }
    public boolean isFullScreen() {
        return isFullScreen;
    }

}
