package com.ouchadam.podcast.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.provider.FeedProvider;

public class ChannelTable {

    public static final String TABLE_FEED = "ChannelTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LINK = "channelLink";
    public static final String COLUMN_TITLE = "channelTitle";
    public static final String COLUMN_CATEGORY = "channelCategory";
    public static final String COLUMN_IMAGE_URL = "imageUrl";
    public static final String COLUMN_IMAGE_TITLE = "imageTitle";
    public static final String COLUMN_IMAGE_LINK = "imageLink";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_FEED
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_TITLE + " text,"
            + COLUMN_LINK + " text,"
            + COLUMN_CATEGORY + " text,"
            + COLUMN_IMAGE_URL + " text,"
            + COLUMN_IMAGE_TITLE + " text,"
            + COLUMN_IMAGE_LINK + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ChannelTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        onCreate(database);
    }

    public static void dropTable() {
        RSS.getContext().getContentResolver().delete(FeedProvider.CONTENT_CHANNEL_URI, null, null);
    }
}