package com.sping.mobileplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.activity.VideoPlayerActivity;
import com.sping.mobileplayer.bean.VideoItem;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.util.Utils;

import java.util.ArrayList;
/**
 * VideoListAdapterRV
 * 继承：RecyclerViewCursorAdapter<VideoListAdapterRV.AudioListViewHolder>
 * 解释：
 * 在使用listview的时候，如果数据源来自数据库，我们可能会使用到cursorAdapter
 * listiew.setAdapter(cursorAdapter) 是常见的用法
 * 但是，当我们使用RecyclerView的时候，Adapter为cursorAdapter是会报错的。
 * 到目前为止，RecyclerView是不支持cursorAdapter的，但在github上有网友通过改写，已经实现了支持
 */
public class VideoListAdapterRV extends RecyclerViewCursorAdapter<VideoListAdapterRV.VideoListViewHolder> {


    private final LayoutInflater inflater;

    public VideoListAdapterRV(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public void onBindViewHolder(VideoListViewHolder holder, Cursor cursor) {

        VideoItem item = VideoItem.fromCursor(cursor);
        holder.tvTitle.setText(item.getTitle());
        holder.tvSize.setText(Formatter.formatFileSize(mContext, item.getSize()));
        CharSequence time = Utils.formatMillis(item.getDuration());
        holder.tvDuration.setText(time);
    }

    @Override
    protected void onContentChanged() {
    }

    @Override
    public VideoListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // 填充出一个View
        View view = inflater.inflate(R.layout.adapter_video_list, parent, false);

        final VideoListViewHolder videoListViewHolder = new VideoListViewHolder(view);
        videoListViewHolder.videoItemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = videoListViewHolder.getAdapterPosition();

                        ArrayList<VideoItem> videoItems = getVideoItems(getCursor());
                        startVideoPlayerActivity(videoItems, position);
                    }
                }
        );

        return videoListViewHolder;
    }

    /***
     * 把Cursor里面的所有数据封装到一个ArrayList中
     * @param cursor 所有数据
     * @return 数据集合形式
     */
    private ArrayList<VideoItem> getVideoItems(Cursor cursor) {
        ArrayList<VideoItem> videos = new ArrayList<VideoItem>();
        cursor.moveToFirst();
        do {
            videos.add(VideoItem.fromCursor(cursor));
        } while (cursor.moveToNext());
        return videos;
    }

    /**
     * 进入视屏播放界面
     *
     * @param videos   视频数据
     * @param position 索引位置
     */
    private void startVideoPlayerActivity(ArrayList<VideoItem> videos, int position) {
        Intent intent = new Intent(mContext, VideoPlayerActivity.class);
        intent.putExtra(Keys.ITEMS, videos);
        intent.putExtra(Keys.CURRENT_POSITION_IN_LIST, position);
        mContext.startActivity(intent);
    }

    class VideoListViewHolder extends RecyclerView.ViewHolder {
        View videoItemView;
        TextView tvTitle;
        TextView tvDuration;
        TextView tvSize;

        private VideoListViewHolder(View itemView) {
            super(itemView);
            videoItemView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDuration = (TextView) itemView.findViewById(R.id.tv_duration);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
        }
    }

}
