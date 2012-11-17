package com.ouchadam.podcast;

import com.ouchadam.podcast.util.FeedParserHelper;
import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.assertNotNull;

public class PodcastParserShould {

    @Test
    public void shouldReadFileFromRes() {
        Document doc = new FeedParserHelper().getDocFromRes();

        assertNotNull(doc);
    }

}
