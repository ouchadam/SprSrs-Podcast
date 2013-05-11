package com.ouchadam.podcast.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static org.junit.Assert.fail;

public class FeedParserHelper {

    private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
    private static final String TEST_FILE_PATH = "core/src/test/res/";
    private static final String STUFF_YOU_SHOULD_KNOW = "stuff_you_should_know.rss";
    private static final String STUFF_TO_BLOW_YOUR_MIND = "stuff_to_blow_your_mind.rss";
    private static final String CNET_UK = "cnet_uk.rss";
    private final DocumentBuilder builder;

    public FeedParserHelper() throws ParserConfigurationException {
        builder = FACTORY.newDocumentBuilder();
    }

    public Document getCnetUk() {
        return getDocFromRes(CNET_UK);
    }

    public Document getStuffToBlowYourMind() {
        return getDocFromRes(STUFF_TO_BLOW_YOUR_MIND);
    }

    public Document getStuffYouShouldKnow() {
        return getDocFromRes(STUFF_YOU_SHOULD_KNOW);
    }

    public Document createDocFromString(String string) throws IOException, SAXException {
        return builder.parse(new InputSource(new StringReader(string)));
    }

    private Document getDocFromRes(String stuffToBlowYourMind) {
        try {
            return builder.parse(createFile(stuffToBlowYourMind));
        } catch (SAXException e) {
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return null;
    }

    public File createFile(String fileName) {
        return new File((TEST_FILE_PATH + fileName));
    }

}
