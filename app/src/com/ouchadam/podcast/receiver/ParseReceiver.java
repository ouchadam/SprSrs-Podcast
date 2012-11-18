package com.ouchadam.podcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.ouchadam.podcast.database.FeedDatabaseUtil;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.parser.interfaces.OnParseFinished;

import java.util.List;

public class ParseReceiver extends BroadcastReceiver {

    public static final String ACTION_ON_PARSE_FINISHED = "parse_finished";
    private OnParseFinished onParseFinished;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction().equals(ACTION_ON_PARSE_FINISHED)) {
                onParseFinished.onParseFinished(getMessageListFromDb());
            }
        }
    }

    public void setOnParseListener(OnParseFinished onParseListener) {
        this.onParseFinished = onParseListener;
    }

    private List<FeedItem> getMessageListFromDb() {
        return FeedDatabaseUtil.getAllFeeds();
    }

}
