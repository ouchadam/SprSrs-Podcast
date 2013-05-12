package com.ouchadam.podcast.presentation.download;

import com.ouchadam.podcast.base.BaseListFragment;

public class DownloadsFragment extends BaseListFragment {

    public DownloadsFragment() {}

    public static DownloadsFragment newInstance() {
        return new DownloadsFragment();
    }

    @Override
    public CharSequence getFragmentPageTitle() {
        return "Downloads";
    }

}
