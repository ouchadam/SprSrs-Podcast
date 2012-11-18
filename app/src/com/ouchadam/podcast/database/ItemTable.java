package com.ouchadam.podcast.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.ouchadam.podcast.application.RSS;
import com.ouchadam.podcast.provider.FeedProvider;

public class ItemTable {

    public static final String TABLE_FEED = "ItemTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM_TITLE = "title";
    public static final String COLUMN_ITEM_AUDIO_URL = "audioUrl";
    public static final String COLUMN_ITEM_DETAILS = "details";
    public static final String COLUMN_CHANNEL = "channel";

    public static final String COLUMN_ITEM_DATE = "date";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_FEED
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_ITEM_TITLE + " text,"
            + COLUMN_ITEM_DETAILS + " text,"
            + COLUMN_ITEM_AUDIO_URL + " text,"
            + COLUMN_ITEM_DATE + " text,"
            + COLUMN_CHANNEL + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(ItemTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_FEED);
        onCreate(database);
    }

    public static void dropTable() {
        RSS.getContext().getContentResolver().delete(FeedProvider.CONTENT_ITEM_URI, null, null);
    }
}