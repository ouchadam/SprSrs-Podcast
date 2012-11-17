package com.ouchadam.podcast;

import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.util.FeedParserHelper;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import static junitx.framework.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PodcastParserShould {

    Document doc = new FeedParserHelper().getDocFromRes();

    @Test
    public void shouldReadFileFromRes() {
        assertNotNull(doc);
    }

    @Test
    public void shouldParseItemTagToMesage() {
        NodeList item = doc.getElementsByTagName("item").item(0).getChildNodes();
        FeedItem message = new FeedItem();
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == 1) {
                if (item.item(i).getNodeName().equalsIgnoreCase("title")) {
                    message.setTitle(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase("link")) {
                    message.setLink(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase("description")) {
                    message.setDescription(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase("pubDate")) {
                    message.setDate(nodeToString(item.item(i)));
                }
            }
        }

        assertFalse(message.getTitle().isEmpty());
        assertFalse(message.getDate().isEmpty());
        assertFalse(message.getDescription().isEmpty());
        assertFalse(message.getLink().toString().isEmpty());
    }

    private static String nodeToString(Node node) {
        StringWriter sw = new StringWriter();
        try {
            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            t.setOutputProperty(OutputKeys.INDENT, "yes");
            t.transform(new DOMSource(node), new StreamResult(sw));
        } catch (TransformerException te) {
            System.out.println("nodeToString Transformer Exception");
        }
        return removeTags(sw.toString());
    }

    private static String removeTags(String content) {
        return content.toString().replaceAll("<(.|\\n)*?>", "");
    }

}
