package com.ouchadam.podcast.fragment;

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
