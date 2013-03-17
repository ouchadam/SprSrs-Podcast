package com.ouchadam.podcast.database.episode;

import android.content.ContentProviderOperation;

import com.ouchadam.podcast.database.Marshaller;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.pojo.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeMarshaller implements Marshaller<List<Episode>, List<ContentProviderOperation>> {

    private final String channelTitle;

    public EpisodeMarshaller(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    @Override
    public List<ContentProviderOperation> marshall(List<Episode> what) {
        List<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
        for (Episode episode : what) {
            contentProviderOperations.add(createValuesFromMessage(episode));
        }
        return contentProviderOperations;
    }

    private ContentProviderOperation createValuesFromMessage(Episode channel) {
        ContentProviderOperation.Builder channelBuilder = newInsertForEpisode();
        channelBuilder.withValue(Tables.Episode.TITLE.name(), channel.getTitle());
        channelBuilder.withValue(Tables.Episode.DETAILS.name(), channel.getDescription());
        channelBuilder.withValue(Tables.Episode.DATE.name(), channel.getDate());
        channelBuilder.withValue(Tables.Episode.AUDIO_URL.name(), channel.getLink());
        channelBuilder.withValue(Tables.Episode.IMAGE_URL.name(), channel.getLink());
        channelBuilder.withValue(Tables.Episode.CHANNEL.name(), channelTitle);
        return channelBuilder.build();
    }

    public ContentProviderOperation.Builder newInsertForEpisode() {
        return ContentProviderOperation.newInsert(SprSrsProvider.URIs.EPISODE.getUri());
    }

}
