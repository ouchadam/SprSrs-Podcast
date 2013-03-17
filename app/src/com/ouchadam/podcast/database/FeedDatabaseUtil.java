package com.ouchadam.podcast.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.ouchadam.podcast.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class FeedDatabaseUtil {
//    private final static String[] PROJECTION = {
//            ItemTable.COLUMN_ID,
//            ItemTable.COLUMN_ITEM_TITLE,
//            ItemTable.COLUMN_ITEM_AUDIO_URL,
//            ItemTable.COLUMN_ITEM_DATE,
//            ItemTable.COLUMN_ITEM_DETAILS,
//            ItemTable.COLUMN_CHANNEL };

    public static List<Episode> getAllFeeds(String channel) {
        int feedCount = getFeedCount(channel);
//        String mSelectionClause = ItemTable.COLUMN_CHANNEL + "=?";
        List<Episode> messageList = new ArrayList<Episode>(feedCount);
//        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, PROJECTION,
//                mSelectionClause, new String[] { channel },null);
//        if (cursor.moveToFirst()) {
//            for (int i = 0; i < 10; i ++) {
//                messageList.add(i, createFeedItemFromCursor(cursor));
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
        return null;
    }

    public static int getFeedCount(String channel) {
//        String [] countProjection = { ItemTable.COLUMN_ID };
//        String mSelectionClause = ItemTable.COLUMN_CHANNEL + "=?";
//        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, countProjection,
//                mSelectionClause, new String[] { channel } ,null);
//        int feedCount = cursor.getCount();
//        cursor.close();
        return 0;
    }

    public static Episode getFeedItem(String channel, String title) {
//        String mSelectionClause = ItemTable.COLUMN_ITEM_TITLE + "=?";
//        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_ITEM_URI, PROJECTION,
//                mSelectionClause, new String[]{title},null);
//        if (cursor.moveToFirst()) {
//            Episode message = createFeedItemFromCursor(cursor);
//            cursor.close();
//            return message;
//        }
//        cursor.close();
        return null;
    }

    private static Episode createFeedItemFromCursor(Cursor cursor) {
        Episode message = new Episode();
//        message.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_TITLE)));
//        message.setDate(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_DATE)));
//        message.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_DETAILS)));
//        message.setLink(cursor.getString(cursor.getColumnIndexOrThrow(ItemTable.COLUMN_ITEM_AUDIO_URL)));
        return message;
    }

    public static void setItem(String channel, Episode message) {
//        RSS.getContext().getContentResolver().insert(FeedProvider.CONTENT_ITEM_URI, createValuesFromMessage(channel, message));
    }

    private static ContentValues createValuesFromMessage(String channel, Episode message) {
        ContentValues values = new ContentValues();
//        values.put(ItemTable.COLUMN_CHANNEL, channel);
//        values.put(ItemTable.COLUMN_ITEM_TITLE, message.getTitle());
//        values.put(ItemTable.COLUMN_ITEM_DETAILS, message.getDescription());
//        values.put(ItemTable.COLUMN_ITEM_DATE, message.getDate());
//        values.put(ItemTable.COLUMN_ITEM_AUDIO_URL, message.getLink().toString());
        return values;
    }

}