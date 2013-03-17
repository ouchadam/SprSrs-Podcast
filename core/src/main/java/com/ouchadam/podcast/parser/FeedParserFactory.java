package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;

import java.util.List;

import org.w3c.dom.Document;

public abstract class FeedParserFactory {

	public static List<Episode> getItemParser(Document doc){
        return new FeedItemFactory().parse(doc);
	}

    public static Channel getChannelParser(Document doc){
        return new FeedItemFactory().parseChannel(doc);
    }

}
