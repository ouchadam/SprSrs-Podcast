package com.ouchadam.podcast.database.channel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ouchadam.podcast.R;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelListAdapter extends BaseAdapter {

    private final List<Channel> channels;
    private final LayoutInflater layoutInflater;

    public ChannelListAdapter(List<Channel> channels, LayoutInflater layoutInflater) {
        this.channels = channels;
        this.layoutInflater = layoutInflater;
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
        holder.channelImage.setImageResource(R.drawable.feedicon);
    }

    private static class ViewHolder {
        TextView title;
        TextView category;
        ImageView channelImage;
    }

}
