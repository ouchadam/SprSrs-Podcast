package com.ouchadam.podcast.database.channel;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

import static com.ouchadam.podcast.database.SprSrsProvider.URIs.CHANNEL;

public class ChannelLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private final Callback callback;
    private final Context context;
    private final LoaderManager loaderManager;
    private final ChannelRestorer channelRestorer;

    public interface Callback {
        void onFinish(List<Channel> channels);
    }

    public ChannelLoader(Context context, LoaderManager manager, Callback callback) {
        this.loaderManager = manager;
        this.context = context;
        this.callback = callback;
        channelRestorer = new ChannelRestorer();
        initChannelObserver(context);
    }

    private void initChannelObserver(Context context) {
        context.getContentResolver().registerContentObserver(CHANNEL.getUri(), false, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                startWatchingData();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new ChannelAsyncTaskLoader(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        callback.onFinish(channelRestorer.restore(cursor));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
    }

    public void startWatchingData() {
        loaderManager.restartLoader(loaderId(), null, this);
    }

    public void stopWatchingData() {
        loaderManager.destroyLoader(loaderId());
    }

    private int loaderId() {
        return hashCode();
    }

    public Context getContext() {
        return context;
    }

    private static class ChannelAsyncTaskLoader extends CursorLoader {

        public ChannelAsyncTaskLoader(Context context) {
            super(context, CHANNEL.getUri(), null, null, null, null);
            forceLoad();
        }

    }

}