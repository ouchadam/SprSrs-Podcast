package com.ouchadam.podcast.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ouchadam.podcast.presentation.activity.EpisodeDetailsActivity;
import com.ouchadam.podcast.presentation.activity.EpisodeListActivity;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;

public class Navigator {

    private final Context context;
    private final IntentBuilder intentBuilder;

    public Navigator(Context context) {
        this.context = context;
        intentBuilder = new IntentBuilder(context);
    }

    public void toEpisodeDetails(Episode episode) {
        Intent intent = intentBuilder.start(EpisodeDetailsActivity.class)
                .with("episode", episode)
                .with(Intent.FLAG_ACTIVITY_NEW_TASK)
                .build();
        start(intent);
    }

    public void toEpisodeList(Channel channel) {
        Intent intent = intentBuilder.start(EpisodeListActivity.class)
                .with("channel", channel)
                .with(Intent.FLAG_ACTIVITY_NEW_TASK)
                .build();
        start(intent);
    }

    private void navigateTo(Class<? extends Activity> klass) {
        Intent activity = intentBuilder.start(klass).build();
        start(activity);
    }

    private void start(Intent intent) {
        context.startActivity(intent);
    }

}
