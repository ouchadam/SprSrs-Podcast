package com.ouchadam.podcast.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.R;

import java.util.List;

public class FeedItemAdapter extends ArrayAdapter<FeedItem> {

    private final Context context;
    private final int itemLayout;
    private final List<FeedItem> messages;

    public FeedItemAdapter(Context context, int textViewResourceId, List<FeedItem> messages) {
        super(context, textViewResourceId, messages);
        this.messages = messages;
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
        holder.title = (TextView) view.findViewById(R.id.item_title);
        holder.details = (TextView) view.findViewById(R.id.item_details);
        holder.date = (TextView) view.findViewById(R.id.item_date);
        holder.feedImage = (ImageView) view.findViewById(R.id.item_image);
        return holder;
    }

    private void setViewText(ViewHolder holder, int position) {
        holder.title.setText(messages.get(position).getTitle());
        holder.details.setText(messages.get(position).getDescription());
        holder.date.setText(messages.get(position).getDate());
        holder.feedImage.setImageResource(R.drawable.feedicon);
    }

    private static class ViewHolder {
        TextView title;
        TextView details;
        TextView date;
        ImageView feedImage;
    }

}
