package com.ouchadam.podcast.base;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.ouchadam.podcast.navigation.Navigator;

public abstract class AbstractSprSrsActivity extends SherlockFragmentActivity {

    private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator(this);
    }

    public Navigator getNavigator() {
        return navigator;
    }
}
