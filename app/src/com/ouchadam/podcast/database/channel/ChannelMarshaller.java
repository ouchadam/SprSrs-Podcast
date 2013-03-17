package com.ouchadam.podcast.database.channel;

import android.content.ContentProviderOperation;

import com.ouchadam.podcast.database.Marshaller;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.pojo.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelMarshaller implements Marshaller<Channel, List<ContentProviderOperation>> {

    private final String url;

    public ChannelMarshaller(String url) {
        this.url = url;
    }

    @Override
    public List<ContentProviderOperation> marshall(Channel what) {
        List<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
        contentProviderOperations.add(createValuesFromMessage(what));
        return contentProviderOperations;
    }

    private ContentProviderOperation createValuesFromMessage(Channel channel) {
        ContentProviderOperation.Builder channelBuilder = newInsertForChannel();
        channelBuilder.withValue(Tables.Channel.TITLE.name(), channel.getTitle());
        channelBuilder.withValue(Tables.Channel.LINK.name(), channel.getLink());
        channelBuilder.withValue(Tables.Channel.RSS_LINK.name(), url);
        channelBuilder.withValue(Tables.Channel.CATEGORY.name(), channel.getCategory());
        channelBuilder.withValue(Tables.Channel.IMAGE_TITLE.name(), channel.getImage().imageTitle);
        channelBuilder.withValue(Tables.Channel.IMAGE_URL.name(), channel.getImage().imageUrl);
        return channelBuilder.build();
    }

    public ContentProviderOperation.Builder newInsertForChannel() {
        return ContentProviderOperation.newInsert(SprSrsProvider.URIs.CHANNEL.getUri());
    }

}
