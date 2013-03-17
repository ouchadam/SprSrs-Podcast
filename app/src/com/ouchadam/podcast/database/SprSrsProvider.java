package com.ouchadam.podcast.database;

import android.net.Uri;

import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;

public class SprSrsProvider extends SQLiteContentProviderImpl {

    public static final Uri AUTHORITY = Uri.parse("content://com.ouchadam.podcast/");

    public enum URIs {

        CHANNEL,
        EPISODE;

        public Uri getUri() {
            return AUTHORITY.buildUpon().appendPath(name()).build();
        }
    }

}
