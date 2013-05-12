package com.ouchadam.podcast.database.episode;

import android.database.Cursor;

import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeListRestorer implements CursorRestorer<List<Episode>> {

    private final EpisodeRestorer episodeRestorer;

    public EpisodeListRestorer() {
        episodeRestorer = new EpisodeRestorer();
    }

    @Override
    public List<Episode> restore(Cursor cursor) {
        List<Episode> channels = new ArrayList<Episode>();
        while (cursor.moveToNext()) {
            channels.add(episodeRestorer.restore(cursor));
        }
        return channels;
    }

}
