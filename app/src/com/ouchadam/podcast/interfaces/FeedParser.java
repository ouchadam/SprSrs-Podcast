package com.ouchadam.podcast.interfaces;
import uk.co.ouch.adam.util.Message;

import java.util.List;

public interface FeedParser {
	List<Message> parse();
}
