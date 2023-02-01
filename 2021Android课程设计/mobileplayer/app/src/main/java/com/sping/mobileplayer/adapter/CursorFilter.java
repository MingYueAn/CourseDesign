package com.sping.mobileplayer.adapter;

import android.database.Cursor;
import android.widget.Filter;

/**
 * CursorFilter继承Filter，它提供了CursorFilterClient接口，CursorAdapter实现CursorFilterClient接口。
 * CursorFilter会在过滤的某些点回调CursorFilterClient接口的对应函数，我们就是通过这些被回调的函数，
 * 改变Filter的行为，以实现自己的目的。
 */
class CursorFilter extends Filter {
    private CursorFilterClient mClient;

    CursorFilter(CursorFilterClient client) {
        mClient = client;
    }

    @Override
    public CharSequence convertResultToString(Object resultValue) {
        return mClient.convertToString((Cursor) resultValue);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Cursor cursor = mClient.runQueryOnBackgroundThread(constraint);
        FilterResults results = new FilterResults();
        if (cursor != null) {
            results.count = cursor.getCount();
            results.values = cursor;
        } else {
            results.count = 0;
            results.values = null;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        Cursor oldCursor = mClient.getCursor();
        if (results.values != null && results.values != oldCursor) {
            mClient.changeCursor((Cursor) results.values);
        }
    }

    interface CursorFilterClient {
        CharSequence convertToString(Cursor cursor);
        Cursor runQueryOnBackgroundThread(CharSequence constraint);
        Cursor getCursor();
        void changeCursor(Cursor cursor);
    }
}

