package com.ouchadam.podcast;

import com.ouchadam.podcast.parser.EpisodeParser;
import com.ouchadam.podcast.pojo.Episode;
import com.ouchadam.podcast.util.FeedParserHelper;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import static org.fest.assertions.Assertions.assertThat;

public class EpisodeParserShould {

    private final FeedParserHelper feedParserHelper;

    private EpisodeParser episodeParser;

    public EpisodeParserShould() throws ParserConfigurationException {
        feedParserHelper = new FeedParserHelper();
    }

    @Before
    public void setUp() throws Exception {
        episodeParser = new EpisodeParser();
    }

    @Test
    public void parse_title_tags() throws Exception {
        final String xml = "<item><title>test</title></item>";
        Document feed = feedParserHelper.createDocFromString(xml);
        Episode episode = episodeParser.parse(feed.getElementsByTagName("item").item(0).getChildNodes());

        assertThat(episode.getTitle()).isEqualTo("test");
    }

    @Test
    public void parse_date_tags() throws Exception {
        final String xml = "<item><date>test</date></item>";
        Document feed = feedParserHelper.createDocFromString(xml);
        Episode episode = episodeParser.parse(feed.getElementsByTagName("item").item(0).getChildNodes());

        assertThat(episode.getDate()).isEqualTo("test");
    }
}
