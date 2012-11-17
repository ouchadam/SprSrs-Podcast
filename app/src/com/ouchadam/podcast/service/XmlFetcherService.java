package com.ouchadam.podcast.service;

import android.app.IntentService;
import android.content.Intent;
import com.ouchadam.podcast.Message;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.database.DatabaseUtil;
import com.ouchadam.podcast.helper.RSSFeedHelper;

import java.util.List;

public class XmlFetcherService extends IntentService {

    public XmlFetcherService(String name) {
        super(name);
    }

    public XmlFetcherService() {
        super("RSS");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (DatabaseUtil.getFeedCount() < 1) {
                if (intent.getAction().equals("parse")) {
                    parseXml();
                }
            } else {
                sendBroadcast(IntentFactory.getParseFinished());
            }
        }
    }

    private void parseXml() {
        List<Message> messages = new RSSFeedHelper(getApplicationContext()).getArticle();
        for (Message message : messages) {
            DatabaseUtil.setItem(null, message);
        }
        sendBroadcast(IntentFactory.getParseFinished());
    }

}
