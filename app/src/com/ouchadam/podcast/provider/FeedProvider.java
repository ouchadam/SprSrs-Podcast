package com.ouchadam.podcast.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import com.ouchadam.podcast.database.ChannelTable;
import com.ouchadam.podcast.database.DatabaseHelper;
import com.ouchadam.podcast.database.ItemTable;

import java.util.Arrays;
import java.util.HashSet;

public class FeedProvider extends ContentProvider {

    private DatabaseHelper database;

    private static final int FEEDS = 10;
    private static final int CHANNELS = 11;
    private static final int FEED_TABLE_ID = 20;
    private static final int CHANNEL_TABLE_ID = 21;

    private static final String AUTHORITY = "com.ouchadam.podcast.provider.FeedProvider";

    public static final String ITEM_BASE_PATH = "feeds";
    public static final String CHANNEL_BASE_PATH = "channels";
    public static final Uri CONTENT_ITEM_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEM_BASE_PATH);
    public static final Uri CONTENT_CHANNEL_URI = Uri.parse("content://" + AUTHORITY + "/" + CHANNEL_BASE_PATH);

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH, FEEDS);
        sURIMatcher.addURI(AUTHORITY, ITEM_BASE_PATH + "/#", FEED_TABLE_ID);
        sURIMatcher.addURI(AUTHORITY, CHANNEL_BASE_PATH, CHANNELS);
        sURIMatcher.addURI(AUTHORITY, CHANNEL_BASE_PATH + "/#", CHANNEL_TABLE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (sURIMatcher.match(uri)) {
            case FEEDS:
                checkIfFeedColumnsExist(projection);
                queryBuilder.setTables(ItemTable.TABLE_FEED);
                break;
            case CHANNELS:
                checkIfChannelColumnsExist(projection);
                queryBuilder.setTables(ChannelTable.TABLE_FEED);
        }
        addIdToQuery(uri, queryBuilder);

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    private void addIdToQuery(Uri uri, SQLiteQueryBuilder queryBuilder) {
        switch (sURIMatcher.match(uri)) {
            case FEEDS:
                break;
            case FEED_TABLE_ID:
                queryBuilder.appendWhere(ItemTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CHANNELS:
                break;
            case CHANNEL_TABLE_ID:
                queryBuilder.appendWhere(ChannelTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        Uri _uri = null;
        switch (uriType) {
            case FEEDS:
                id = sqlDB.insert(ItemTable.TABLE_FEED, null, values);
                _uri = ContentUris.withAppendedId(CONTENT_ITEM_URI, id);
                break;
            case CHANNELS:
                id = sqlDB.insert(ChannelTable.TABLE_FEED, null, values);
                _uri = ContentUris.withAppendedId(CONTENT_CHANNEL_URI, id);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return _uri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        String id = uri.getLastPathSegment();
        switch (uriType) {
            case FEEDS:
                rowsDeleted = sqlDB.delete(ItemTable.TABLE_FEED, selection, selectionArgs);
                break;
            case FEED_TABLE_ID:
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ItemTable.TABLE_FEED, ItemTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(ItemTable.TABLE_FEED, ItemTable.COLUMN_ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;
            case CHANNELS:
                rowsDeleted = sqlDB.delete(ChannelTable.TABLE_FEED, selection, selectionArgs);
                break;
            case CHANNEL_TABLE_ID:
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(ChannelTable.TABLE_FEED, ChannelTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsDeleted = sqlDB.delete(ChannelTable.TABLE_FEED, ChannelTable.COLUMN_ID + "=" + id + " and " + selection,
                            selectionArgs);
                }
                break;


            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        String id = uri.getLastPathSegment();
        switch (uriType) {
            case FEEDS:
                rowsUpdated = sqlDB.update(ItemTable.TABLE_FEED, values, selection, selectionArgs);
                break;
            case FEED_TABLE_ID:
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ItemTable.TABLE_FEED, values, ItemTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(ItemTable.TABLE_FEED, values, ItemTable.COLUMN_ID + "=" + id + " and "
                            + selection, selectionArgs);
                }
                break;
            case CHANNELS:
                rowsUpdated = sqlDB.update(ItemTable.TABLE_FEED, values, selection, selectionArgs);
                break;
            case CHANNEL_TABLE_ID:
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(ChannelTable.TABLE_FEED, values, ChannelTable.COLUMN_ID + "=" + id, null);
                } else {
                    rowsUpdated = sqlDB.update(ChannelTable.TABLE_FEED, values, ChannelTable.COLUMN_ID + "=" + id + " and "
                            + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkIfFeedColumnsExist(String[] projection) {
        String[] available = {
                ItemTable.COLUMN_ID,
                ItemTable.COLUMN_ITEM_TITLE,
                ItemTable.COLUMN_ITEM_AUDIO_URL,
                ItemTable.COLUMN_ITEM_DATE,
                ItemTable.COLUMN_ITEM_DETAILS,
                ItemTable.COLUMN_CHANNEL };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    private void checkIfChannelColumnsExist(String[] projection) {
        String[] available = {
                ChannelTable.COLUMN_ID,
                ChannelTable.COLUMN_TITLE,
                ChannelTable.COLUMN_LINK,
                ChannelTable.COLUMN_RSS_LINK,
                ChannelTable.COLUMN_CATEGORY,
                ChannelTable.COLUMN_IMAGE_URL,
                ChannelTable.COLUMN_IMAGE_TITLE,
                ChannelTable.COLUMN_IMAGE_LINK };

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));

            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}