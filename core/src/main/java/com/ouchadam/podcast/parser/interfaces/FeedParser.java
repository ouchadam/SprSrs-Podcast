package com.ouchadam.podcast.parser.interfaces;

import com.ouchadam.podcast.pojo.FeedItem;
import org.w3c.dom.Document;

import java.util.List;

public interface FeedParser {
	List<FeedItem> parse(Document doc);
}
