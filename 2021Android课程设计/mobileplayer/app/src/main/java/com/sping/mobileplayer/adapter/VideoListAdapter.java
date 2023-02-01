package com.sping.mobileplayer.adapter;


import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.bean.VideoItem;
import com.sping.mobileplayer.util.Utils;
/**
 * VideoListAdapter
 * 继承：CursorAdapter
 * 内部类：ViewHolder
 * 导入类：
 * .bean.VideoItem
 * .util.Utils
 * 解释：
 * CursorAdapter类是继承于BaseAdapter，是一个虚类，为Cursor和ListView连接提供了桥梁
 * CursorAdapter是继承了BaseAdapter后，覆盖了getView方法，在getView方法中调用了newView和bindView方法
 * 使用CursorAdapter时必须实现它的两个方法
 * (1)newView：不是每次都被调用，只在实例化的时候调用，数据增加的时候也会调用，但是在重绘(比如修改条目里的TextView的内容)的时候不会被调用
 * (2)bindView：在绘制Item之前一定会调用bindView方法，在重绘的时候也同样被调用
 */
public class VideoListAdapter extends CursorAdapter {

    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    // 创建一个View
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // 填充出一个View
        View view = View.inflate(context, R.layout.adapter_video_list, null);
        // 用一个ViewHolder保存View中的子控件
        ViewHolder holder = new ViewHolder();
        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        holder.tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        holder.tv_size = (TextView) view.findViewById(R.id.tv_size);
        // 把ViewHolder保存到View中
        view.setTag(holder);
        return view;
    }

    // 把cursor中的数据绑定到View上进行显示
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        VideoItem item = VideoItem.fromCursor(cursor);
        holder.tv_title.setText(item.getTitle());
        holder.tv_size.setText(Formatter.formatFileSize(context, item.getSize()));
        CharSequence time = Utils.formatMillis(item.getDuration());
        holder.tv_duration.setText(time);
    }

    class ViewHolder {
        TextView tv_title;
        TextView tv_size;
        TextView tv_duration;
    }

}
