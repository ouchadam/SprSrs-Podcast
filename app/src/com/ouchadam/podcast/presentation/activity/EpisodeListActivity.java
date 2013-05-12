package com.ouchadam.podcast.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ouchadam.podcast.R;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.presentation.episode.EpisodeListFragment;
import com.ouchadam.podcast.pojo.Channel;

public class EpisodeListActivity extends AbstractSprSrsActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_feed_list);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        ft.add(R.id.item_fragment_container, EpisodeListFragment.newInstance(channel));
        ft.commit();
    }

}


