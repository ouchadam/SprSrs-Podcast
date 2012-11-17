package com.ouchadam.podcast.parser.interfaces;

import com.ouchadam.podcast.pojo.Message;
import org.w3c.dom.Document;

import java.util.List;

public interface FeedParser {
	List<Message> parse(Document doc);
}
