package com.ouchadam.podcast;

import android.os.AsyncTask;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.database.ChannelDatabaseUtil;
import com.ouchadam.podcast.helper.RSSFeedHelper;

public class GetChannelTask extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... strings) {
        ChannelDatabaseUtil.addChannel(new RSSFeedHelper(RSS.getContext()).getChannel(strings[0]));
        return null;
    }

}
