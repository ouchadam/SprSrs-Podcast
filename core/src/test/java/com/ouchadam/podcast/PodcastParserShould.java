package com.ouchadam.podcast;

import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.util.FeedParserHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PodcastParserShould {

    Document doc = new FeedParserHelper().getDocFromRes();
    NodeList itemList = doc.getElementsByTagName("item");
    NodeList tenthItem = itemList.item(10).getChildNodes();
    FeedItem parsedItem = mock(FeedItem.class);
    @Test
    public void shouldReadFileFromRes() {
        assertNotNull(doc);
    }

    @Test
    public void shouldParseItemTitle() {
        for (int i = 0; i < tenthItem.getLength(); i ++) {
            if (tenthItem.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (tenthItem.item(i).getNodeName().equalsIgnoreCase("title")) {
                    parsedItem.setTitle("");
                }
            }
        }

        verify(parsedItem).setTitle(anyString());
    }

    @Test
    public void shouldParseItemDescription() {
        for (int i = 0; i < tenthItem.getLength(); i ++) {
            if (tenthItem.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (tenthItem.item(i).getNodeName().equalsIgnoreCase("description")) {
                    parsedItem.setDescription("");
                }
            }
        }

        verify(parsedItem).setDescription(anyString());
    }

    @Test
    public void shouldParseItemDate() {
        for (int i = 0; i < tenthItem.getLength(); i ++) {
            if (tenthItem.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (tenthItem.item(i).getNodeName().equalsIgnoreCase("pubDate")) {
                    parsedItem.setDate("");
                }
            }
        }

        verify(parsedItem).setDate(anyString());
    }

    @Test
    public void shouldParseItemLink() {
        for (int i = 0; i < tenthItem.getLength(); i ++) {
            if (tenthItem.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (tenthItem.item(i).getNodeName().equalsIgnoreCase("link")) {
                    parsedItem.setLink("");
                }
            }
        }

        verify(parsedItem).setLink(anyString());
    }

}
