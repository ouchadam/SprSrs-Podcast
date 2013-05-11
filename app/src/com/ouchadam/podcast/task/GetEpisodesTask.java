package com.ouchadam.podcast.task;

import android.content.ContentResolver;
import android.os.AsyncTask;
import android.util.Log;

import com.ouchadam.podcast.database.ContentProviderOperationExecutable;
import com.ouchadam.podcast.database.Persister;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.episode.EpisodeMarshaller;
import com.ouchadam.podcast.parser.FeedParserFactory;
import com.ouchadam.podcast.pojo.Channel;
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

public class GetEpisodesTask extends AsyncTask<Channel, Void, Void> {

    private final FetchEpisodesTask fetchEpisodesTask;
    private final ContentResolver contentResolver;
    private final ContentProviderOperationExecutable executor;

    public GetEpisodesTask(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        executor = new ContentProviderOperationExecutable(contentResolver);
        fetchEpisodesTask = new FetchEpisodesTask();
    }

    public void start(Channel channel) {
        execute(channel);
    }

    @Override
    protected Void doInBackground(Channel... what) {
        Channel channel = what[0];
        List<Episode> episodes = fetchEpisodesTask.getEpisodes(channel.getRssLink());
        Persister<List<Episode>> episodePersister = new Persister<List<Episode>>(executor, new EpisodeMarshaller(channel.getTitle()));
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
                return FeedParserFactory.getItemParser(builder.parse(getInputStream(url)));
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("Parsing Failed");
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
