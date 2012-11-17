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
    NodeList item = doc.getElementsByTagName("item").item(0).getChildNodes();
    FeedItem message = mock(FeedItem.class);


    @Test
    public void shouldReadFileFromRes() {
        assertNotNull(doc);
    }

    @Test
    public void shouldParseItemTitle() {
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (item.item(i).getNodeName().equalsIgnoreCase("title")) {
                    message.setTitle("");
                }
            }
        }

        verify(message).setTitle(anyString());
    }

    @Test
    public void shouldParseItemDescription() {
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (item.item(i).getNodeName().equalsIgnoreCase("description")) {
                    message.setDescription("");
                }
            }
        }

        verify(message).setDescription(anyString());
    }

    @Test
    public void shouldParseItemDate() {
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (item.item(i).getNodeName().equalsIgnoreCase("pubDate")) {
                    message.setDate("");
                }
            }
        }

        verify(message).setDate(anyString());
    }

    @Test
    public void shouldParseItemLink() {
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (item.item(i).getNodeName().equalsIgnoreCase("link")) {
                    message.setLink("");
                }
            }
        }

        verify(message).setLink(anyString());
    }

    @Test
    public void shouldParseItemImageLink() {
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == Node.ELEMENT_NODE) {
                if (item.item(i).getNodeName().equalsIgnoreCase("url")) {
                    message.setImageLink("");
                }
            }
        }

        verify(message).setImageLink(anyString());
    }

}
