package com.ouchadam.podcast.database;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

public class DataUpdater<T> implements LoaderManager.LoaderCallbacks<Cursor> {

    private final Query values;
    private final Context context;
    private final DataUpdatedListener<T> listener;
    private final CursorRestorer<T> restorer;
    private final LoaderManager loaderManager;

    public interface DataUpdatedListener<T> {
        void onDataUpdated(T data);
    }

    public DataUpdater(Context context, Query values, CursorRestorer<T> restorer, DataUpdatedListener<T> listener, LoaderManager loaderManager) {
        this.values = values;
        this.context = context;
        this.listener = listener;
        this.restorer = restorer;
        this.loaderManager = loaderManager;
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(context, values.uri, values.projection, values.selection, values.selectionArgs, values.sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        Async<T> async = new Async<T>(context, cursor, restorer, listener);
        async.forceLoad();
    }

    private static class Async<T> extends AsyncTaskLoader<T> {

        private final Cursor cursor;
        private final CursorRestorer<T> restorer;
        private final DataUpdatedListener<T> listener;

        public Async(Context context, Cursor cursor, CursorRestorer<T> restorer, DataUpdatedListener<T> listener) {
            super(context);
            this.cursor = cursor;
            this.restorer = restorer;
            this.listener = listener;
        }

        @Override
        public T loadInBackground() {
            return restorer.restore(cursor);
        }

        @Override
        public void deliverResult(T data) {
            super.deliverResult(data);
            if (data != null) {
                listener.onDataUpdated(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
    }

    public static class Query {
        public Uri uri;
        public String[] projection;
        public String selection;
        public String[] selectionArgs;
        public String sortOrder;
    }

    public static class QueryBuilder {

        private Query query = new Query();

        public QueryBuilder withUri(Uri uri) {
            query.uri = uri;
            return this;
        }

        public QueryBuilder withProjection(String[] projection) {
            query.projection = projection;
            return this;
        }

        public QueryBuilder withSelection(String selection) {
            query.selection = selection;
            return this;
        }

        public QueryBuilder withSelectionArgs(String[] selectionArgs) {
            query.selectionArgs = selectionArgs;
            return this;
        }

        public QueryBuilder withSorter(String sortOrder) {
            query.sortOrder = sortOrder;
            return this;
        }

        public Query build() {
            return query;
        }
    }

}