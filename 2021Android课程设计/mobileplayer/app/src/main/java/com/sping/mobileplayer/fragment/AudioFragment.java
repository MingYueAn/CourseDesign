package com.sping.mobileplayer.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sping.mobileplayer.R;
import com.sping.mobileplayer.adapter.AudioListAdapterRV;

public class AudioFragment extends BaseFragment {

    private AudioListAdapterRV mAdapter;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_audio_bg;
    }

    @Override
    public void initView() {
        RecyclerView recyclerView = (RecyclerView) rootView;
        if (mAdapter == null) {
            Uri uri = Media.EXTERNAL_CONTENT_URI; //相当于Message.
            String[] projection = {//指定要查询那些列
                    Media._ID, Media.TITLE, Media.ARTIST, Media.DATA};
            Cursor c = getContext().getContentResolver().query(uri, projection, null, null, Media.TITLE + " ASC");
            mAdapter = new AudioListAdapterRV(getContext(), c, 1);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        //自定义recyclerView分割线,V7:25的功能
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initData() {
        //查询数据
        initLoader();
    }

    private void initLoader() {
        getLoaderManager().initLoader(1, null, new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri = Media.EXTERNAL_CONTENT_URI; //相当于Message.
                String[] projection = { //指定要查询那些列
                        Media._ID, Media.TITLE, Media.ARTIST, Media.DATA};
                String orderBy = Media.TITLE + " ASC"; // 指定为升序排序方式, " ASC"升序," DESC"降序
                return new CursorLoader(getContext(), uri, projection, null, null, orderBy);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                mAdapter.swapCursor(data);
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
    }

    @Override
    public void onClick(View v, int id) {
    }
}
