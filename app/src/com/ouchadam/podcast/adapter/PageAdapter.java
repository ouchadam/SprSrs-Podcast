package com.ouchadam.podcast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.ouchadam.podcast.fragment.ChannelListFragment;

public class PageAdapter extends FragmentPagerAdapter {

    public PageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return ChannelListFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
