package com.ouchadam.podcast.parser.interfaces;

import com.ouchadam.podcast.pojo.Episode;

import org.w3c.dom.Document;

import java.util.List;

public interface FeedParser {
	List<Episode> parse(Document doc);
}
