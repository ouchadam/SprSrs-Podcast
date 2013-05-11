package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FeedItemFactory implements FeedParser {

    protected static final String CHANNEL = "channel";
    protected static final String LINK = "link";
    protected static final String IMAGE_URL = "enclosure";
    protected static final String IMAGE = "image";
    protected static final String TITLE = "title";
    protected static final String ITEM = "item";
    private static final String CATEGORY = "category";

    @Override
    public List<Episode> parse(Document doc, int start, int end) {
        EpisodeParser episodeParser = new EpisodeParser();
        List<Episode> episodes = new ArrayList<Episode>();
        for (int i = start; i < end; i++) {
            episodes.add(episodeParser.parse(doc.getElementsByTagName(ITEM).item(i).getChildNodes()));
        }
        return episodes;
    }

    public Channel parseChannel(Document doc) {
        return createChannel(doc.getElementsByTagName(CHANNEL).item(0).getChildNodes());
    }

    private Channel createChannel(NodeList item) {
        String title = "";
        String link = "";
        String rssLink = "";
        String category = "";
        Channel.Image image = null;

        for (int i = 0; i < item.getLength(); i++) {
            Node n = item.item(i);
            if (n.getNodeType() == 1) {
                if (n.getNodeName().equalsIgnoreCase(TITLE)) {
                    title = nodeToString(n);
                } else if (n.getNodeName().equalsIgnoreCase(LINK)) {
                    link = nodeToString(n);
                } else if (n.getNodeName().equalsIgnoreCase(IMAGE)) {
                    image = createImage(n.getChildNodes());
                } else if (n.getNodeName().equalsIgnoreCase(CATEGORY)) {
                    category = nodeToString(n);
                }
            }
        }
        return new Channel(title, link, rssLink, category, image);
    }

    private Channel.Image createImage(NodeList imageNode) {
        String title = null;
        String url = null;
        String link = null;

        for (int i = 0; i < imageNode.getLength(); i++) {
            Node n = imageNode.item(i);
            if (n.getNodeType() == 1) {
                if (n.getNodeName().equalsIgnoreCase(TITLE)) {
                    title = nodeToString(n);
                } else if (n.getNodeName().equalsIgnoreCase(IMAGE_URL)) {
                    url = nodeToString(n);
                } else if (n.getNodeName().equalsIgnoreCase(LINK)) {
                    link = nodeToString(n);
                }
            }
        }
        return new Channel.Image(url, title, link);
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
