package com.ouchadam.podcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import uk.co.ouch.adam.database.DatabaseUtil;
import uk.co.ouch.adam.interfaces.OnParseFinished;
import uk.co.ouch.adam.util.Message;

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

    private List<Message> getMessageListFromDb() {
        return DatabaseUtil.getAllFeeds();
    }

}
