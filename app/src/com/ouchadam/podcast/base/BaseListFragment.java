package com.ouchadam.podcast.base;

import android.app.Activity;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.ouchadam.podcast.TitleUpdater;

public abstract class BaseListFragment extends SherlockListFragment {

    private TitleUpdater titleUpdater;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        titleUpdater = getTitleUpdater(activity);
    }

    private TitleUpdater getTitleUpdater(Activity activity) {
        try {
            return (TitleUpdater) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TitleUpdater");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setPageFragmentTitle(getFragmentPageTitle());
    }

    protected abstract CharSequence getFragmentPageTitle();

    private void setPageFragmentTitle(CharSequence charSequence) {
        titleUpdater.updateTitle(charSequence);
    }

}
