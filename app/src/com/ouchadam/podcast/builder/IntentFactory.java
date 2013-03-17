package com.ouchadam.podcast.builder;

import android.content.Intent;

import com.ouchadam.podcast.activity.DetailsActivity;
import com.ouchadam.podcast.activity.FeedListActivity;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;

public class IntentFactory {

    public static Intent getMessageDetails(Episode episode) {
        Intent intent = new Intent(RSS.getContext(), DetailsActivity.class);
        intent.putExtra("episode", episode);
        return intent;
    }

    public static Intent getSubscriptionFeed(Channel channel) {
        Intent intent = new Intent(RSS.getContext(), FeedListActivity.class);
        intent.putExtra("channel", channel.getTitle());
        intent.putExtra("url", channel.getRssLink());
        return intent;
    }

}
