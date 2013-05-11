package com.ouchadam.podcast.parser;

import com.ouchadam.podcast.pojo.Episode;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class EpisodeParser implements Parser<NodeList, Episode> {

    private static final int VALID_NODE = 1;

    private enum NodeType {
        channel,
        pubDate,
        description,
        link,
        enclosure,
        image,
        title,
        item,
        category,
        INVALID;
    }

    @Override
    public Episode parse(NodeList item) {
        final Episode episode = new Episode();
        for (int i = 0; i < item.getLength(); i++) {
            if (item.item(i).getNodeType() == VALID_NODE) {
                Node node = item.item(i);
                NodeType nodeType = getNodeType(node);
                if (nodeType != NodeType.INVALID) {
                    setEpisodeFromMode(nodeType, nodeToString(node), episode);
                }
            }
        }
        return episode;
    }

    private NodeType getNodeType(Node node) {
        String name = nodeName(node);
        try {
            return NodeType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return NodeType.INVALID;
        }
    }

    private String nodeName(Node node) {
        return node.getNodeName();
    }

    private void setEpisodeFromMode(NodeType nodeType, String content, Episode episode) {
        switch (nodeType) {
            case title:
                episode.setTitle(content);
                break;
            case link:
                if (isAudioLink(content)) {
                    episode.setLink(content);
                }
                break;
            case description:
                episode.setDescription(content);
                break;
            case pubDate:
                episode.setDate(content);
                break;
            default:
                break;
        }
    }

    private boolean isAudioLink(String audioLink) {
        return audioLink.contains(".mp3") || audioLink.contains(".mp4") || audioLink.contains(".aac");
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
        return content.replaceAll("<(.|\\n)*?>", "");
    }

}
