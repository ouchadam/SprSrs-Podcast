package com.ouchadam.podcast.database;

import android.content.ContentValues;
import android.database.Cursor;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.provider.FeedProvider;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private final static String[] PROJECTION = {
            ItemTable.COLUMN_ID,
            ItemTable.COLUMN_ITEM_TITLE,
            ItemTable.COLUMN_ITEM_AUDIO_URL,
            ItemTable.COLUMN_ITEM_DATE,
            ItemTable.COLUMN_ITEM_DETAILS };

    private final static String[] CHANNEL_PROJECTION = {
            ChannelTable.COLUMN_ID,
            ChannelTable.COLUMN_TITLE,
            ChannelTable.COLUMN_LINK,
            ChannelTable.COLUMN_IMAGE_URL
    };

    public static List<Channel> getAllChannels() {
        List<Channel> channelList = new ArrayList<Channel>(getChannelCount());
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_CHANNEL_URI, CHANNEL_PROJECTION,null, null,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < channelList.size(); i ++) {
                channelList.add(i, createChannelFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return channelList;
    }

    private static Channel createChannelFromCursor(Cursor cursor) {
        Channel message = new Channel();
        message.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_TITLE)));
        message.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_CATEGORY)));
        message.setLink(cursor.getString(cursor.getColumnIndexOrThrow(ChannelTable.COLUMN_LINK)));
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
        String [] countProjection = { ItemTable.COLUMN_ID };
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, countProjection, null, null,null);
        int feedCount = cursor.getCount();
        cursor.close();
        return feedCount;
    }

    public static List<FeedItem> getAllFeeds() {
        int feedCount = getFeedCount();
        List<FeedItem> messageList = new ArrayList<FeedItem>(feedCount);
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, PROJECTION,null, null,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < 10; i ++) {
                messageList.add(i, createFeedItemFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return messageList;
    }

    public static int getFeedCount() {
        String [] countProjection = { ItemTable.COLUMN_ID };
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, countProjection, null, null,null);
        int feedCount = cursor.getCount();
        cursor.close();
        return feedCount;
    }

    public static FeedItem getFeedItem(String channel, String title) {
        String mSelectionClause = ItemTable.COLUMN_ITEM_TITLE + "=?";
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, PROJECTION,
                mSelectionClause, new String[]{title},null);
        if (cursor.moveToFirst()) {
            FeedItem message = createFeedItemFromCursor(cursor);
            cursor.close();
            return message;
        }
        cursor.close();
        return null;
    }

    private static FeedItem createFeedItemFromCursor(Cursor cursor) {
        FeedItem message = new FeedItem();
        message.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_TITLE)));
        message.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_DATE)));
        message.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_DETAILS)));
        message.setLink(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_AUDIO_URL)));
        return message;
    }

    public static void setItem(String channel, FeedItem message) {
        RSS.getContext().getContentResolver().insert(FeedProvider.CONTENT_ITEM_URI, createValuesFromMessage(message));
    }

    private static ContentValues createValuesFromMessage(FeedItem message) {
        ContentValues values = new ContentValues();
        values.put(ItemTable.COLUMN_ITEM_TITLE, message.getTitle());
        values.put(ItemTable.COLUMN_ITEM_DETAILS, message.getDescription());
        values.put(ItemTable.COLUMN_ITEM_DATE, message.getDate());
        values.put(ItemTable.COLUMN_ITEM_AUDIO_URL, message.getLink().toString());
        return values;
    }

}