package com.ouchadam.podcast.service;

import android.app.IntentService;
import android.content.Intent;
import uk.co.ouch.adam.builder.FeedParserFactory;
import uk.co.ouch.adam.builder.IntentFactory;
import uk.co.ouch.adam.database.DatabaseUtil;
import uk.co.ouch.adam.util.Message;

import java.util.List;

public class XmlFetcherService extends IntentService{

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
        List<Message> messages = FeedParserFactory.getParser().parse();
        for (Message message : messages) {
            DatabaseUtil.setItem(null, message);
        }
        sendBroadcast(IntentFactory.getParseFinished());
    }

}
