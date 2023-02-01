package com.sping.mobileplayer.util;


import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sping.mobileplayer.interfaces.TimeConstants;

import java.util.Calendar;

public class Utils {
    /**
     * 查找Button/ImageButtom并设置单击监听器
     *
     * @param view
     * @param listener 监听器
     */
    public static void setButtonOnClickListener(View view, OnClickListener listener) {
        // 遍历view里面所有的Button和ImageButton
        if (view instanceof Button || view instanceof ImageButton) {
            view.setOnClickListener(listener);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);

                setButtonOnClickListener(child, listener);// 递归
            }
        }
    }


    /**
     * 在屏幕中间显示一个Toast
     *
     * @param text
     */
    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int screenWidth = windowManger.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        int screenHeight = windowManger.getDefaultDisplay().getHeight();
        return screenHeight;
    }


    /**
     * 遍历Cursor中的所有记录
     *
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {

        if (cursor == null) {
            return;
        }

        Logger.i(Utils.class, "共有" + cursor.getCount() + "条数据");
        // 遍历所有的行
        while (cursor.moveToNext()) {
            Logger.i(Utils.class, "----------------------------");
            // 遍历所有的列
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String value = cursor.getString(i);
                Logger.i(Utils.class, columnName + "=" + value);
            }

        }
    }


    /**
     * 格式化一个毫秒值,如果有,则格式化为时分秒,如 02:30:59,如果没有小时,则格式化为, 30:59
     *
     * @param duration
     * @return
     */
    public static CharSequence formatMillis(long duration) {
//		把duration转换为一个日期
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.add(Calendar.MILLISECOND, (int) duration);
        CharSequence inFormat = duration / TimeConstants.HOUR_MILLIS > 1 ? "kk:mm:ss" : "mm:ss";
        return DateFormat.format(inFormat, calendar.getTime());
    }
}











