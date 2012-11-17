package com.ouchadam.podcast.builder;

import uk.co.ouch.adam.interfaces.FeedParser;
import uk.co.ouch.adam.util.AndroidSaxFeedParser;

public abstract class FeedParserFactory {
	static String feedUrl = "http://www.howstuffworks.com/podcasts/stuff-you-should-know.rss";
	
	public static FeedParser getParser(){
        return new AndroidSaxFeedParser(feedUrl);
	}

}
