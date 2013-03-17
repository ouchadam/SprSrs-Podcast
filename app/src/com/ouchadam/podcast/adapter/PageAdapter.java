package com.ouchadam.podcast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ouchadam.podcast.fragment.ChannelListFragment;
import com.ouchadam.podcast.fragment.DownloadsFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private final Fragment[] fragments = {
            ChannelListFragment.newInstance(),
            DownloadsFragment.newInstance(),
            ChannelListFragment.newInstance()
    };

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

}
