package com.ouchadam.podcast.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.fragment.ItemListFragment;

public class FeedListActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_feed_list);
        initFragment(getIntent().getStringExtra("channel"));
    }

    private void initFragment(String channel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.item_fragment_container, ItemListFragment.newInstance(channel));
        ft.commit();
    }

}


