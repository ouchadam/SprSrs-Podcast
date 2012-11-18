package com.ouchadam.podcast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.fragment.ItemListFragment;

public class FeedListActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_feed_list);
        initFragment(getIntent());
    }

    private void initFragment(Intent intent) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.item_fragment_container, ItemListFragment.newInstance(getIntent().getStringExtra("channel"), getIntent().getStringExtra("url")));
        ft.commit();
    }

}


