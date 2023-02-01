package com.sping.mobileplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.activity.AudioPlayerActivity;
import com.sping.mobileplayer.bean.AudioItem;
import com.sping.mobileplayer.interfaces.Keys;

import java.util.ArrayList;
/**
 * AudioListAdapterRV
 * 继承：RecyclerViewCursorAdapter<AudioListAdapterRV.AudioListViewHolder>
 * 解释：
 * 在使用listview的时候，如果数据源来自数据库，我们可能会使用到cursorAdapter
 * listiew.setAdapter(cursorAdapter) 是常见的用法
 * 但是，当我们使用RecyclerView的时候，Adapter为cursorAdapter是会报错的。
 * 到目前为止，RecyclerView是不支持cursorAdapter的，但在github上有网友通过改写，已经实现了支持
 */
public class AudioListAdapterRV extends RecyclerViewCursorAdapter<AudioListAdapterRV.AudioListViewHolder> {

    private final LayoutInflater inflater;//布局填充器
    public AudioListAdapterRV(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = LayoutInflater.from(context);//获取填充器实例
    }
    /**
     * 最初，您将获得新的未使用的视图持有人，并且必须用要显示的数据填充它们。
     * 但是，当您滚动时，将开始获得用于离开屏幕的行的视图持有人，并且您必须用新数据替换它们持有的旧数据
     * @param holder
     * @param cursor
     */
    @Override
    public void onBindViewHolder(AudioListViewHolder holder, Cursor cursor) {
        AudioItem item = AudioItem.fromCursor(cursor);
        holder.tvTitle.setText(item.getTitle());
        holder.tvArtist.setText(item.getArtist());
    }

    @Override
    protected void onContentChanged() {
    }

    @Override
    public AudioListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // 填充出一个View
        View view = inflater.inflate(R.layout.adapter_audio_list, parent, false);

        final AudioListViewHolder audioListViewHolder = new AudioListViewHolder(view);
        audioListViewHolder.audioItemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = audioListViewHolder.getAdapterPosition();
                        ArrayList<AudioItem> audios = getAudios(getCursor());
                        startAudioPlayerActivity(audios, position);
                    }
                }
        );

        return audioListViewHolder;
    }

    /**
     * 开启音频播放器界面
     *
     * @param audios   音频数据列表
     * @param position 当前点击的音频位置
     */
    protected void startAudioPlayerActivity(ArrayList<AudioItem> audios, int position) {
        Intent intent = new Intent(mContext, AudioPlayerActivity.class);
        intent.putExtra(Keys.ITEMS, audios);
        intent.putExtra(Keys.CURRENT_POSITION_IN_LIST, position);
        mContext.startActivity(intent);
    }

    /**
     * 把Cursor里面的数据取出来封装到集合中
     *
     * @param cursor
     * @return
     */
    protected ArrayList<AudioItem> getAudios(Cursor cursor) {
        ArrayList<AudioItem> audios = new ArrayList<AudioItem>();
        cursor.moveToFirst();
        do {
            audios.add(AudioItem.fromCursor(cursor));
        } while (cursor.moveToNext());
        return audios;
    }

    public class AudioListViewHolder extends RecyclerView.ViewHolder {
        View audioItemView;
        TextView tvTitle;
        TextView tvArtist;

        public AudioListViewHolder(View itemView) {
            super(itemView);
            audioItemView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvArtist = (TextView) itemView.findViewById(R.id.tv_artist);
        }
    }

}
