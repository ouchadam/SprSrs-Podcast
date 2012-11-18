package com.ouchadam.podcast.service;

import android.app.IntentService;
import android.content.Intent;
import com.ouchadam.podcast.database.FeedDatabaseUtil;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.helper.RSSFeedHelper;

import java.util.List;

public class XmlFetcherService extends IntentService {

    private String channel;

    public XmlFetcherService(String name) {
        super(name);
    }

    public XmlFetcherService() {
        super("RSS");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            channel = intent.getStringExtra("channel");
            if (FeedDatabaseUtil.getFeedCount(channel) < 1) {
                if (intent.getAction().equals("parse")) {
                    parseXml(intent.getStringExtra("url"));
                }
            } else {
                sendBroadcast(IntentFactory.getParseFinished(channel));
            }
        }
    }

    private void parseXml(String url) {
        List<FeedItem> messages = new RSSFeedHelper(getApplicationContext()).getArticle(url);
        for (FeedItem message : messages) {
            FeedDatabaseUtil.setItem(channel, message);
        }
        sendBroadcast(IntentFactory.getParseFinished(channel));
    }

}
