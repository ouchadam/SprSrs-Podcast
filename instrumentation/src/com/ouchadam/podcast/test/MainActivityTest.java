package com.ouchadam.podcast.test;

import android.test.ActivityInstrumentationTestCase2;

import com.ouchadam.podcast.presentation.activity.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }
}

