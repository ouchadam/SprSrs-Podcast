package com.ouchadam.podcast.helper;

import android.content.Context;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.parser.FeedParserFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
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

    public List<FeedItem> getArticle() {
        InputStream in = context.getResources().openRawResource(R.raw.stuff_you_should_know);
        try {
            return FeedParserFactory.getParser(builder.parse(in));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
