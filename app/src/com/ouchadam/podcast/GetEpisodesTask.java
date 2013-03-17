package com.ouchadam.podcast;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

import com.ouchadam.podcast.database.ContentProviderOperationExecutable;
import com.ouchadam.podcast.database.Persister;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.episode.EpisodeMarshaller;
import com.ouchadam.podcast.parser.FeedParserFactory;
import com.ouchadam.podcast.pojo.Episode;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.xml.sax.SAXException;

public class GetEpisodesTask extends AsyncTask<String, Void, Void> {

    public GetEpisodesTask(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    private final ContentResolver contentResolver;

    @Override
    protected Void doInBackground(String... strings) {
        String url = strings[0];
        String channel = strings[1];
        List<Episode> episodes = new FetchEpisodesTask().getEpisodes(url);
        Persister<List<Episode>> episodePersister = new Persister<List<Episode>>(new ContentProviderOperationExecutable(contentResolver), new EpisodeMarshaller(channel));
        episodePersister.persist(episodes);
        contentResolver.notifyChange(SprSrsProvider.URIs.EPISODE.getUri(), null);
        return null;
    }

    private static class FetchEpisodesTask {

        private static final DocumentBuilderFactory FACTORY = DocumentBuilderFactory.newInstance();
        private final DocumentBuilder builder;

        public FetchEpisodesTask() {
            try {
                builder = FACTORY.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new RuntimeException("ParserConfigurationException");
            }
        }

        public List<Episode> getEpisodes(String url) {
            try {
                List<Episode> episodes = FeedParserFactory.getItemParser(builder.parse(getInputStream(url)));
                return episodes;
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
