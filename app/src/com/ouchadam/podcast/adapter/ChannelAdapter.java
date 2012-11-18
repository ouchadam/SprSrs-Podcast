package com.ouchadam.podcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelAdapter extends ArrayAdapter<Channel> {

    private final Context context;
    private final int itemLayout;
    private final List<Channel> channels;

    public ChannelAdapter(Context context, int textViewResourceId, List<Channel> channels) {
        super(context, textViewResourceId, channels);
        this.channels = channels;
        this.itemLayout = textViewResourceId;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemLayout, null);
            holder = initViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setViewText(holder, position);
        return convertView;
    }

    private ViewHolder initViews(View view) {
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.channel_title);
        holder.category = (TextView) view.findViewById(R.id.channel_category);
        holder.channelImage = (ImageView) view.findViewById(R.id.channel_image);
        return holder;
    }

    private void setViewText(ViewHolder holder, int position) {
        holder.title.setText(channels.get(position).getTitle());
        holder.category.setText(channels.get(position).getCategory());
        holder.channelImage.setImageResource(R.drawable.feedicon);
    }

    private static class ViewHolder {
        TextView title;
        TextView category;
        ImageView channelImage;
    }

}
