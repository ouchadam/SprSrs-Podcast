package com.ouchadam.podcast.task;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

import com.ouchadam.podcast.database.ContentProviderOperationExecutable;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.channel.ChannelMarshaller;
import com.ouchadam.podcast.database.Persister;
import com.ouchadam.podcast.parser.FeedParserFactory;
import com.ouchadam.podcast.pojo.Channel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.xml.sax.SAXException;

public class GetChannelTask extends AsyncTask<String, Void, Void> {

    public GetChannelTask(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    private final ContentResolver contentResolver;

    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        Channel channel = new FetchChannelTask().getChannel(url);
        Persister<Channel> channelPersister = new Persister<Channel>(new ContentProviderOperationExecutable(contentResolver), new ChannelMarshaller(url));
        channelPersister.persist(channel);
        contentResolver.notifyChange(SprSrsProvider.URIs.CHANNEL.getUri(), null);
        return null;
    }

    private static class FetchChannelTask {

        private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
        private final DocumentBuilder builder;

        public FetchChannelTask() {
            try {
                builder = FACTORY.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException("ParserConfigurationException");
            }
        }

        public Channel getChannel(String url) {
            try {
                Channel channel = FeedParserFactory.getChannelParser(builder.parse(getInputStream(url)));
                return channel;
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private InputStream getInputStream(String url) {
            URL oracle = null;
            Log.e("Test", "URL : " + url);
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

}
