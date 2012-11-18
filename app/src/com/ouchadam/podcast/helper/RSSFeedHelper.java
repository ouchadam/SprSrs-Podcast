package com.ouchadam.podcast.helper;

import android.content.Context;
import com.ouchadam.podcast.parser.FeedParserFactory;
import com.ouchadam.podcast.pojo.FeedItem;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RSSFeedHelper {

    private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
    private final DocumentBuilder builder;
    private final Context context;

    public RSSFeedHelper(Context context) {
        try {
            builder = FACTORY.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("ParserConfigurationException");
        }
        this.context = context;
    }

    public List<FeedItem> getArticle(String url) {
//        InputStream in = context.getResources().openRawResource(R.raw.stuff_you_should_know);
        try {
            return FeedParserFactory.getParser(builder.parse(getInputStream(url)));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InputStream getInputStream(String url) {
        URL oracle = null;
        try {
            oracle = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        InputStream in = null;
        try {
            return oracle.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
