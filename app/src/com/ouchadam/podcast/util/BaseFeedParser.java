package com.ouchadam.podcast.util;
import uk.co.ouch.adam.interfaces.FeedParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParser implements FeedParser {

	protected static final String CHANNEL = "channel";
	protected static final String PUB_DATE = "pubDate";
	protected static final  String DESCRIPTION = "description";
	protected static final  String LINK = "link";
    protected static final  String IMAGE_URL = "url";
    protected static final  String IMAGE = "image";
	protected static final  String TITLE = "title";
	protected static final  String ITEM = "item";
	
	private final URL feedUrl;

	protected BaseFeedParser(String feedUrl){
		try {
			this.feedUrl = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	protected InputStream getInputStream() {
		try {
			return feedUrl.openConnection().getInputStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}