package com.sping.mobileplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.bean.AudioItem;
import com.sping.mobileplayer.interfaces.IPlayService;
import com.sping.mobileplayer.interfaces.Keys;
import com.sping.mobileplayer.service.AudioPlayService;
import com.sping.mobileplayer.util.Utils;

import java.util.ArrayList;

public class AudioPlayListAdapterRV extends RecyclerView.Adapter<AudioPlayListAdapterRV.AudioPlayListViewHolder> {

    private Context mContext;
    private IPlayService playService;
    private ArrayList<AudioItem> audioItemList;
    private int currentPlayIndex;


    public AudioPlayListAdapterRV(Context context, IPlayService playService) {
        mContext = context;
        this.playService = playService;
        audioItemList = playService.getAudioItemList();
        currentPlayIndex = playService.getCurrentPlayIndex();
    }

    @Override
    public AudioPlayListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_audio_playlist, parent, false);
        final AudioPlayListViewHolder viewHolder = new AudioPlayListViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPlayIndex != viewHolder.getAdapterPosition()) {
                    int lastPosition = currentPlayIndex;
                    // 切歌
                    currentPlayIndex = viewHolder.getAdapterPosition();
                    playService.updatePlayList(currentPlayIndex);
                    playService.openAudio();

                    notifyItemChanged(lastPosition);
                    notifyItemChanged(currentPlayIndex);
                }
            }
        });

        viewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();

                // 更新歌单
                if (position == currentPlayIndex) {
                    Utils.showToast(mContext, "不能删除正在播放的音乐哦!");
                } else {
                    if (position < currentPlayIndex) {
                        // 索引变小并刷新
                        currentPlayIndex--;
                    }
                    if (position < audioItemList.size() && position > -1) {
                        audioItemList.remove(position);
                        notifyItemRemoved(position);
                    }
                    // 更新数据
                    updateServiceData();
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AudioPlayListViewHolder holder, int position) {
        holder.tvTitle.setText(audioItemList.get(position).getTitle());
        holder.tvTitle.setTextColor(currentPlayIndex == position ? Color.RED : Color.BLACK);
    }

    @Override
    public int getItemCount() {
        if (audioItemList == null) {
            return 0;
        }
        return audioItemList.size();
    }

    private void updateServiceData() {
        // 只需要刷新歌单
        Intent intentService = new Intent(mContext, AudioPlayService.class);
        intentService.putExtra(Keys.ITEMS, audioItemList);
        intentService.putExtra(Keys.CURRENT_POSITION_IN_LIST, currentPlayIndex);
        mContext.startForegroundService(intentService);
    }

    /**
     * 更新当前播放的音频在播放列表中的索引位置
     *
     * @param position 更新到的位置
     */
    public void updateCurrentPlayListIndex(int position) {
        if (currentPlayIndex != position) {
            int lastPosition = currentPlayIndex;
            currentPlayIndex = position;
            notifyItemChanged(lastPosition);
            notifyItemChanged(currentPlayIndex);
        }
    }

    static class AudioPlayListViewHolder extends RecyclerView.ViewHolder {
        View audioItemView;
        TextView tvTitle;
        ImageView ivRemove;

        AudioPlayListViewHolder(View itemView) {
            super(itemView);
            audioItemView = itemView;
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivRemove = (ImageView) itemView.findViewById(R.id.btn_remove);
        }
    }
}
