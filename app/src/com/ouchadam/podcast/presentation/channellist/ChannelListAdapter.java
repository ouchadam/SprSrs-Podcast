package com.ouchadam.podcast.presentation.channellist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.model.ImageTag;
import com.novoda.imageloader.core.model.ImageTagFactory;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelListAdapter extends BaseAdapter {

    private final List<Channel> channels;
    private final LayoutInflater layoutInflater;
    private final Context context;
    private final ImageTagFactory imageTagFactory;
    private final ImageManager imageManager;

    public ChannelListAdapter(List<Channel> channels, LayoutInflater layoutInflater, Context context,
                              ImageManager imageManager) {
        this.channels = channels;
        this.layoutInflater = layoutInflater;
        this.context = context;
        this.imageTagFactory = createImageTagFactory();
        this.imageManager = imageManager;
    }

    private static ImageTagFactory createImageTagFactory() {
        ImageTagFactory imageTagFactory = ImageTagFactory.newInstance(200, 200, R.drawable.app_launcher);
        return setupImageTagFactory(imageTagFactory);
    }

    private static ImageTagFactory setupImageTagFactory(ImageTagFactory imageTagFactory) {
        imageTagFactory.setErrorImageId(R.drawable.app_launcher);
        return imageTagFactory;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Override
    public Channel getItem(int position) {
        return channels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return channels.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.adapter_channel_layout, null);
            view.setTag(initViews(view));
        }
        updateViews((ViewHolder) view.getTag(), getItem(position));
        return view;
    }

    private ViewHolder initViews(View view) {
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.channel_title);
        holder.category = (TextView) view.findViewById(R.id.channel_category);
        holder.channelImage = (ImageView) view.findViewById(R.id.channel_image);
        return holder;
    }

    private void updateViews(ViewHolder holder, Channel channel) {
        holder.title.setText(channel.getTitle());
        holder.category.setText(channel.getCategory());
        loadProductImage(holder.channelImage, channel.getImage().imageUrl);
    }

    private void loadProductImage(ImageView view, String imageUrl) {
        if (imageUrl != null) {
            ImageTag tag = imageTagFactory.build(imageUrl, context);
            view.setTag(tag);
            imageManager.getLoader().load(view);
        }
    }

    private static class ViewHolder {
        TextView title;
        TextView category;
        ImageView channelImage;
    }

}
