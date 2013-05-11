package com.ouchadam.podcast.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.ouchadam.podcast.activity.EpisodeDetailsActivity;
import com.ouchadam.podcast.activity.EpisodeListActivity;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;

import static com.ouchadam.podcast.navigation.Navigator.ActivityIntentFactory.getActivity;

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
        Intent activity = getActivity(context, klass);
        start(activity);
    }

    private void start(Intent intent) {
        context.startActivity(intent);
    }

    protected static class ActivityIntentFactory {
        public static Intent getActivity(Context context, Class<? extends Activity> klass) {
            return new Intent(context, klass);
        }

    }

}
