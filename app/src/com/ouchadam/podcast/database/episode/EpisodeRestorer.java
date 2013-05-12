package com.ouchadam.podcast.database.episode;

import android.database.Cursor;

import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.pojo.Episode;

public class EpisodeRestorer implements CursorRestorer<Episode> {

    @Override
    public Episode restore(Cursor cursor) {
        return createEpisodeFromCursor(cursor);
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
