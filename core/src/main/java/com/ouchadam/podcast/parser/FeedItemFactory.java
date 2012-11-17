package com.ouchadam.podcast.parser;


import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.parser.interfaces.FeedParser;
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
import java.util.ArrayList;
import java.util.List;

public class FeedItemFactory implements FeedParser {

    protected static final String CHANNEL = "channel";
    protected static final String PUB_DATE = "pubDate";
    protected static final  String DESCRIPTION = "description";
    protected static final  String LINK = "link";
    protected static final  String IMAGE_URL = "url";
    protected static final  String IMAGE = "image";
    protected static final  String TITLE = "title";
    protected static final  String ITEM = "item";

    @Override
    public List<FeedItem> parse(Document doc) {
        int itemAmount = doc.getElementsByTagName(ITEM).getLength();

        List<FeedItem> messages = new ArrayList<FeedItem>();
        for (int i = 0; i < 10; i ++) {
            messages.add(parseMessage(doc.getElementsByTagName(ITEM).item(i).getChildNodes()));
        }

        return messages;
    }

    private static FeedItem parseMessage(NodeList item) {
        final FeedItem message = new FeedItem();
        for (int i = 0; i < item.getLength(); i ++) {
            if (item.item(i).getNodeType() == 1) {
                if (item.item(i).getNodeName().equalsIgnoreCase(TITLE)) {
                    message.setTitle(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase(LINK)) {
                    message.setLink(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase(DESCRIPTION)) {
                    message.setDescription(nodeToString(item.item(i)));
                } else if (item.item(i).getNodeName().equalsIgnoreCase(PUB_DATE)) {
                    message.setDate(nodeToString(item.item(i)));
                }
            }
        }
        return message;
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
