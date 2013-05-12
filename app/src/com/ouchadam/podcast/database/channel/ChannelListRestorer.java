package com.ouchadam.podcast.database.channel;

import android.database.Cursor;

import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.pojo.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelListRestorer implements CursorRestorer<List<Channel>> {

    private final ChannelRestorer channelRestorer;

    public ChannelListRestorer() {
        channelRestorer = new ChannelRestorer();
    }

    @Override
    public List<Channel> restore(Cursor cursor) {
        List<Channel> channels = new ArrayList<Channel>();
        while (cursor.moveToNext()) {
            channels.add(channelRestorer.restore(cursor));
        }
        return channels;
    }

}
