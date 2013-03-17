package com.ouchadam.podcast.database.episode;

import android.database.Cursor;

import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeRestorer implements CursorRestorer<List<Episode>> {

    @Override
    public List<Episode> restore(Cursor cursor) {
        List<Episode> channels = new ArrayList<Episode>();
        while (cursor.moveToNext()) {
            channels.add(createEpisodeFromCursor(cursor));
        }
        cursor.close();
        return channels;
    }

    private Episode createEpisodeFromCursor(Cursor cursor) {
        Episode episode = new Episode();
        episode.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Episode.TITLE.name())));
        episode.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Episode.DETAILS.name())));
        episode.setDate(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Episode.DATE.name())));
        episode.setLink(cursor.getString(cursor.getColumnIndexOrThrow(Tables.Episode.AUDIO_URL.name())));
        return episode;
    }

}
