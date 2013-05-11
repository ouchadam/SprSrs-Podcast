package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

public class EpisodeContainer implements FeedParser {

    @Override
    public List<Episode> parse(Document doc) {
        List<Episode> episodes = new ArrayList<Episode>();
        return episodes;
    }

}
