//package com.ouchadam.podcast;
//
//import com.ouchadam.podcast.pojo.Channel;
//import com.ouchadam.podcast.util.FeedParserHelper;
//import org.junit.Test;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.fail;
//import static org.mockito.Matchers.any;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//public class ChannelParserShould {
//
//    Document doc = new FeedParserHelper().getDocFromRes();
//    NodeList channel = doc.getElementsByTagName("channel").item(0).getChildNodes();
//    Channel parsedChannel = mock(Channel.class);
//
//    @Test
//    public void shouldReadFileFromRes() {
//        assertNotNull(doc);
//    }
//
//    @Test
//    public void shouldParseChannelTitle() {
//        for (int i = 0; i < channel.getLength(); i ++) {
//            if (channel.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (channel.item(i).getNodeName().equalsIgnoreCase("title")) {
//                    parsedChannel.setTitle("");
//                }
//            }
//        }
//
//        verify(parsedChannel).setTitle(anyString());
//    }
//
//    @Test
//    public void shouldParseChannelCategory() {
//        for (int i = 0; i < channel.getLength(); i ++) {
//            if (channel.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (channel.item(i).getNodeName().equalsIgnoreCase("category")) {
//                    parsedChannel.setCategory("");
//                }
//            }
//        }
//
//        verify(parsedChannel).setCategory(anyString());
//    }
//
//    @Test
//    public void shouldParseChannelLink() {
//        for (int i = 0; i < channel.getLength(); i ++) {
//            if (channel.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (channel.item(i).getNodeName().equalsIgnoreCase("link")) {
//                    parsedChannel.setLink("");
//                }
//            }
//        }
//
//        verify(parsedChannel).setLink(anyString());
//    }
//
//    @Test
//    public void shouldParseChannelImage() {
//        for (int i = 0; i < channel.getLength(); i ++) {
//            if (channel.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (channel.item(i).getNodeName().equalsIgnoreCase("image")) {
//                    parsedChannel.setImage(null);
//                }
//            }
//        }
//
//        verify(parsedChannel).setImage(any(Channel.Image.class));
//    }
//
//    @Test
//    public void shouldParseChannelImageTitle() {
//        NodeList image = getImageNode().getChildNodes();
//        for (int i = 0; i < image.getLength(); i ++) {
//            if (image.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (image.item(i).getNodeName().equalsIgnoreCase("title")) {
//                    return;
//                }
//            }
//        }
//
//        fail("Image has no title element");
//    }
//
//    @Test
//    public void shouldParseChannelImageUrl() {
//        NodeList image = getImageNode().getChildNodes();
//        for (int i = 0; i < image.getLength(); i ++) {
//            if (image.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (image.item(i).getNodeName().equalsIgnoreCase("url")) {
//                    return;
//                }
//            }
//        }
//
//        fail("Image has no url element");
//    }
//
//    @Test
//    public void shouldParseChannelImageLink() {
//        NodeList image = getImageNode().getChildNodes();
//        for (int i = 0; i < image.getLength(); i ++) {
//            if (image.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (image.item(i).getNodeName().equalsIgnoreCase("link")) {
//                    return;
//                }
//            }
//        }
//
//        fail("Image has no link element");
//    }
//
//    private Node getImageNode() {
//        for (int i = 0; i < channel.getLength(); i ++) {
//            if (channel.item(i).getNodeType() == Node.ELEMENT_NODE) {
//                if (channel.item(i).getNodeName().equalsIgnoreCase("image")) {
//                    return  channel.item(i);
//                }
//            }
//        }
//        throw new NullPointerException("No image node found");
//    }
//
//}
