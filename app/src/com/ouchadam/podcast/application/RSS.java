package com.ouchadam.podcast.application;

import android.app.Application;
import android.content.Context;
import com.ouchadam.podcast.pojo.Message;

import java.util.List;

public class RSS extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this;
    }

    public static Context getContext() {
        return context;
    }
}
