package com.ouchadam.podcast.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.provider.FeedProvider;

import java.util.ArrayList;
import java.util.List;

public class ChannelDatabaseUtil {

    private final static String[] CHANNEL_PROJECTION = {
            ChannelTable.COLUMN_ID,
            ChannelTable.COLUMN_TITLE,
            ChannelTable.COLUMN_LINK,
            ChannelTable.COLUMN_RSS_LINK,
            ChannelTable.COLUMN_CATEGORY,
            ChannelTable.COLUMN_IMAGE_LINK,
            ChannelTable.COLUMN_IMAGE_TITLE,
            ChannelTable.COLUMN_IMAGE_URL
    };

    public static List<Channel> getAllChannels() {
        int channelCount = getChannelCount();
        List<Channel> channelList = new ArrayList<Channel>(channelCount);
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_CHANNEL_URI, CHANNEL_PROJECTION,null, null,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < channelCount; i ++) {
                channelList.add(i, createChannelFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return channelList;
    }

    public static Channel createChannelFromCursor(Cursor cursor) {
        Channel message = new Channel();
        message.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_TITLE)));
        message.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_CATEGORY)));
        message.setLink(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_LINK)));
        message.setRssLink(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_RSS_LINK)));
        message.setImage(createImageFromCursor(cursor));
        return message;
    }

    private static Channel.Image createImageFromCursor(Cursor cursor) {
        String url = cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_IMAGE_LINK));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_IMAGE_TITLE));
        String link = cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_IMAGE_URL));
        return new Channel.Image(url, title, link);
    }

    public static int getChannelCount() {
        String [] countProjection = { ChannelTable.COLUMN_ID };
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_CHANNEL_URI, countProjection, null, null,null);
        int feedCount = cursor.getCount();
        cursor.close();
        return feedCount;
    }

    public static void addChannel(Channel channel) {
        RSS.getContext().getContentResolver().insert(FeedProvider.CONTENT_CHANNEL_URI, createValuesFromMessage(channel));
    }

    private static ContentValues createValuesFromMessage(Channel channel) {
        ContentValues values = new ContentValues();
        values.put(ChannelTable.COLUMN_TITLE, channel.getTitle());
        values.put(ChannelTable.COLUMN_LINK, channel.getLink().toString());
        values.put(ChannelTable.COLUMN_RSS_LINK, channel.getRssLink().toString());
        values.put(ChannelTable.COLUMN_CATEGORY, channel.getCategory());
        values.put(ChannelTable.COLUMN_IMAGE_TITLE,channel.getImage().imageTitle);
        values.put(ChannelTable.COLUMN_IMAGE_URL, channel.getImage().imageUrl);
        values.put(ChannelTable.COLUMN_IMAGE_LINK, channel.getImage().imageLink);
        return values;
    }

}