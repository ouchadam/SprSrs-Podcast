package com.ouchadam.podcast.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

public class FeedParserHelper {

    private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
    private static final String TEST_FILE_PATH = "core/src/main/res/";
    private static final String STUFF_YOU_SHOULD_KNOW = "stuff_you_should_know.rss";
    private static final String STUFF_TO_BLOW_YOUR_MIND = "stuff_to_blow_your_mind.rss";
    private final DocumentBuilder builder;

    public FeedParserHelper() {
        try {
            builder = FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("ParserConfigurationException");
        }
    }

    public Document getDocFromRes() {
        try {
            return builder.parse(createFile());
        } catch (SAXException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    private File createFile() {
        return new File((TEST_FILE_PATH + STUFF_TO_BLOW_YOUR_MIND).toString());
    }

}
