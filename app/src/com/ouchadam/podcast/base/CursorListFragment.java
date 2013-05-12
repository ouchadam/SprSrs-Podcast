package com.ouchadam.podcast.base;

import android.app.Activity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.database.DataUpdater;

public abstract class CursorListFragment<T> extends SherlockListFragment implements DataUpdater.DataUpdatedListener<T> {

    private DataUpdater<T> dataUpdater;
    private T data;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        startWatchingList();
    }

    private void startWatchingList() {
        if (dataUpdater != null) {
            dataUpdater.stopWatchingData();
        }
        dataUpdater = new DataUpdater<T>(getActivity(), getQuery(), getRestorer(), this, getLoaderManager());
        dataUpdater.startWatchingData();
    }

    protected abstract DataUpdater.Query getQuery();

    protected abstract CursorRestorer<T> getRestorer();

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        super.onListItemClick(listView, v, position, id);
        onListItemClick(listView.getItemAtPosition(position));
    }

    protected abstract void onListItemClick(Object itemAtPosition);

    @Override
    public void onDataUpdated(T data) {
        this.data = data;
        BaseAdapter adapter = getAdapter(data);
        getListView().setAdapter(adapter);
    }

    protected abstract BaseAdapter getAdapter(T data);

    protected T get() {
        return data;
    }

}
