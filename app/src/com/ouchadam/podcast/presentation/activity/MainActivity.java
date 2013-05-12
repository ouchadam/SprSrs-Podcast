package com.ouchadam.podcast.presentation.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ouchadam.podcast.R;
import com.ouchadam.podcast.TitleUpdater;
import com.ouchadam.podcast.adapter.PageAdapter;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;

public class MainActivity extends AbstractSprSrsActivity implements TitleUpdater {

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

    @Override
    public void updateTitle(CharSequence title) {
        setTitle(title);
    }
}
