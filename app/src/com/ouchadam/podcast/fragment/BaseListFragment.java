package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.os.Bundle;
import com.actionbarsherlock.app.SherlockListFragment;
import com.ouchadam.podcast.TitleUpdater;

public abstract class BaseListFragment extends SherlockListFragment {

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setPageFragmentTitle(getFragmentPageTitle(), getTitleUpdater(activity));
    }

    public abstract CharSequence getFragmentPageTitle();

    private void setPageFragmentTitle(CharSequence charSequence, TitleUpdater titleUpdater) {
        titleUpdater.updateTitle(charSequence);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private TitleUpdater getTitleUpdater(Activity activity) {
        try {
            return (TitleUpdater) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement TitleUpdater");
        }
    }

}
