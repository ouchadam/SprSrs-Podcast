package com.ouchadam.podcast.database;

import android.database.Cursor;

public interface CursorRestorer<T> {
    T restore(Cursor cursor);
}
