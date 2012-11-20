package com.ouchadam.podcast.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.adapter.PageAdapter;

public class MainActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViewPager();
    }

    private void initViewPager() {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
    }
}
