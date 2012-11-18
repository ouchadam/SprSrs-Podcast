package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.FeedItem;
import org.w3c.dom.Document;

import java.util.List;

public abstract class FeedParserFactory {

	public static List<FeedItem> getItemParser(Document doc){
        return new FeedItemFactory().parse(doc);
	}

    public static Channel getChannelParser(Document doc){
        return new FeedItemFactory().parseChannel(doc);
    }

}
