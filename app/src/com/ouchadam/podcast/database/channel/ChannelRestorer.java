package com.ouchadam.podcast.database.channel;

import android.database.Cursor;

import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.pojo.Channel;

public class ChannelRestorer implements CursorRestorer<Channel> {

    @Override
    public Channel restore(Cursor cursor) {
        return createChannelFromCursor(cursor);
    }

    private Channel createChannelFromCursor(Cursor cursor) {
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.TITLE.name()));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.CATEGORY.name()));
        String link = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.LINK.name()));
        String rssLink = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.RSS_LINK.name()));
        Channel.Image image = restoreImage(cursor);
        return new Channel(title, link, rssLink, category, image);
    }

    private Channel.Image restoreImage(Cursor cursor) {
        String link = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.IMAGE_URL.name()));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.IMAGE_TITLE.name()));
        String url = cursor.getString(cursor.getColumnIndexOrThrow(Tables.Channel.IMAGE_URL.name()));
        return new Channel.Image(url, title, link);
    }

}
