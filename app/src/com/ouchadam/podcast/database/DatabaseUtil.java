package com.ouchadam.podcast.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.provider.FeedProvider;
import com.ouchadam.podcast.util.Message;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private final static String[] PROJECTION = {
            BaseTable.COLUMN_ID,
            BaseTable.COLUMN_ITEM_IMAGE_URL,
            BaseTable.COLUMN_ITEM_TITLE,
            BaseTable.COLUMN_ITEM_AUDIO_URL,
            BaseTable.COLUMN_ITEM_DATE,
            BaseTable.COLUMN_ITEM_DETAILS };

    public static List<Message> getAllFeeds() {
        int feedCount = getFeedCount();
        List<Message> messageList = new ArrayList<Message>(feedCount);
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_URI, PROJECTION,null, null,null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < 10; i ++) {
                messageList.add(i, createMessageFromCursor(cursor));
                Log.e("TEST", messageList.get(i).getTitle());
                cursor.moveToNext();
            }
        }
        cursor.close();
        return messageList;
    }

    public static int getFeedCount() {
        String [] countProjection = { BaseTable.COLUMN_ID };
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_URI, countProjection, null, null,null);
        int feedCount = cursor.getCount();
        cursor.close();
        return feedCount;
    }

    public static Message getFeedItem(String channel, String title) {
        String mSelectionClause = BaseTable.COLUMN_ITEM_TITLE + "=?";
        Cursor cursor = RSS.getContext().getContentResolver().query(FeedProvider.CONTENT_URI, PROJECTION,
                mSelectionClause, new String[]{title},null);
        if (cursor.moveToFirst()) {
            Message message = createMessageFromCursor(cursor);
            cursor.close();
            return message;
        }
        cursor.close();
        return null;
    }

    private static Message createMessageFromCursor(Cursor cursor) {
        Message message = new Message();
        message.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(BaseTable.COLUMN_ITEM_TITLE)));
        message.setDate(cursor.getString(cursor.getColumnIndexOrThrow(BaseTable.COLUMN_ITEM_DATE)));
        message.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(BaseTable.COLUMN_ITEM_DETAILS)));
        message.setLink(cursor.getString(cursor.getColumnIndexOrThrow(BaseTable.COLUMN_ITEM_AUDIO_URL)));
        message.setImageLink(cursor.getString(cursor.getColumnIndexOrThrow(BaseTable.COLUMN_ITEM_IMAGE_URL)));
        return message;
    }

    public static void setItem(String channel, Message message) {
        RSS.getContext().getContentResolver().insert(FeedProvider.CONTENT_URI, createValuesFromMessage(message));
    }

    private static ContentValues createValuesFromMessage(Message message) {
        ContentValues values = new ContentValues();
        values.put(BaseTable.COLUMN_ITEM_TITLE, message.getTitle());
        values.put(BaseTable.COLUMN_ITEM_DETAILS, message.getDescription());
        values.put(BaseTable.COLUMN_ITEM_DATE, message.getDate());
        values.put(BaseTable.COLUMN_ITEM_AUDIO_URL, message.getLink().toString());
        values.put(BaseTable.COLUMN_ITEM_IMAGE_URL, message.getImageLink().toString());
        return values;
    }

}