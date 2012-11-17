package com.ouchadam.podcast.parser;


import com.ouchadam.podcast.Message;
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
    public List<Message> parse(Document doc) {
        NodeList body = doc.getElementsByTagName("rss").item(0).getChildNodes().item(1).getChildNodes();
        System.out.println("Test ::  body length : " + body.getLength());
        System.out.println("Test ::  item 0 : " + body.item(0).getTextContent());

        List<Message> messages = new ArrayList<Message>();
//        for (int i = 0; i < 1; i ++) {
//            messages.add(parseMessage(body.item(i).getChildNodes()));
//        }

        return messages;
    }

    private static Message parseMessage(NodeList item) {
        System.out.println("Test ::  item length : " + item.getLength());

        final Message message = new Message();
        for (int i = 0; i < item.getLength(); i++) {
            Node n = item.item(i);

//            if (n.getNodeType() == Node.ELEMENT_NODE) {

            System.out.println("Test :: item : " + i + " is : " + nodeToString(n));
            System.out.println("Test :: type : " + n.getNodeType());

                if (TITLE.equalsIgnoreCase(n.getNodeName())) {
                    message.setTitle(nodeToString(n));
                } else if (LINK.equalsIgnoreCase(n.getNodeName())) {
                    message.setLink(nodeToString(n));
                } else if (DESCRIPTION.equalsIgnoreCase(n.getNodeName())) {
                    message.setDescription(nodeToString(n));
                } else if (PUB_DATE.equalsIgnoreCase(n.getNodeName())) {
                        message.setDate(nodeToString(n));
                }
//            }
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
