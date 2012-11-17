package com.ouchadam.podcast.builder;

import com.ouchadam.podcast.util.FeedItemFactory;
import com.ouchadam.podcast.util.Message;
import org.w3c.dom.Document;

import java.util.List;

public abstract class FeedParserFactory {
	static String feedUrl = "http://www.howstuffworks.com/podcasts/stuff-you-should-know.rss";

	public static List<Message> getParser(Document doc){
        return new FeedItemFactory().parse(doc);
//        return new AndroidSaxFeedParser(feedUrl);
	}

}
