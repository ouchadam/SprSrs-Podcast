package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Episode;

import java.util.List;

import org.w3c.dom.Document;

public interface FeedParser {
	List<Episode> parse(Document doc, int start, int end);
}
