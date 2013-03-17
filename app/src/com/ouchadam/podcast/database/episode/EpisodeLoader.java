package com.ouchadam.podcast.database.episode;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.pojo.Episode;

import java.util.List;

import static com.ouchadam.podcast.database.SprSrsProvider.URIs.EPISODE;

public class EpisodeLoader implements LoaderManager.LoaderCallbacks<Cursor> {

    private final Callback callback;
    private final String channel;
    private final Context context;
    private final LoaderManager loaderManager;
    private final EpisodeRestorer episodeRestorer;

    public interface Callback {
        void onFinish(List<Episode> channels);
    }

    public EpisodeLoader(Context context, LoaderManager manager, Callback callback, String channel) {
        this.loaderManager = manager;
        this.context = context;
        this.callback = callback;
        this.channel = channel;
        episodeRestorer = new EpisodeRestorer();
        initChannelObserver(context);
    }

    private void initChannelObserver(Context context) {
        context.getContentResolver().registerContentObserver(EPISODE.getUri(), false, new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                startWatchingData();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new ChannelAsyncTaskLoader(context, channel);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        callback.onFinish(episodeRestorer.restore(cursor));
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

        public ChannelAsyncTaskLoader(Context context, String channel) {
            super(context, EPISODE.getUri(), null, Tables.Episode.CHANNEL.name() + "=?", new String[]{ channel }, null);
            forceLoad();
        }

    }

}