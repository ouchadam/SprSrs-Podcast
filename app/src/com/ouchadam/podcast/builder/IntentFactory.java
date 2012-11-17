package com.ouchadam.podcast.builder;

import android.content.Intent;
import com.ouchadam.podcast.activity.DetailsActivity;
import com.ouchadam.podcast.activity.MessageList;
import com.ouchadam.podcast.activity.SubscriptionsActivity;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.receiver.ParseReceiver;
import com.ouchadam.podcast.service.XmlFetcherService;

public class IntentFactory {

    private static final String ACTION_START_PARSE = "parse";

    public static Intent getParseFinished() {
        Intent intent = new Intent();
        intent.setAction(ParseReceiver.ACTION_ON_PARSE_FINISHED);
        return intent;
    }

    public static Intent getParseService() {
        Intent intent = new Intent(RSS.getContext(), XmlFetcherService.class);
        intent.setAction(ACTION_START_PARSE);
        return intent;
    }

    public static Intent getMessageDetails(String messageTitle) {
        Intent intent = new Intent(RSS.getContext(), DetailsActivity.class);
        intent.putExtra("title", messageTitle);
        return intent;
    }

    public static Intent getSubscriptions() {
        Intent intent = new Intent(RSS.getContext(), SubscriptionsActivity.class);
        return intent;
    }

    public static Intent getSubscriptionFeed() {
        Intent intent = new Intent(RSS.getContext(), MessageList.class);
        return intent;
    }

}
