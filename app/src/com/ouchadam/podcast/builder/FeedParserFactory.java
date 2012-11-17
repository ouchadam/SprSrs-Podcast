package com.ouchadam.podcast.builder;

import com.ouchadam.podcast.interfaces.FeedParser;
import com.ouchadam.podcast.util.AndroidSaxFeedParser;

public abstract class FeedParserFactory {
	static String feedUrl = "http://www.howstuffworks.com/podcasts/stuff-you-should-know.rss";

	public static FeedParser getParser(){
        return new AndroidSaxFeedParser(feedUrl);
	}

}
