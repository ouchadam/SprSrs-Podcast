package com.ouchadam.podcast.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.database.ChannelDatabaseUtil;
import com.ouchadam.podcast.database.ChannelTable;
import com.ouchadam.podcast.pojo.Channel;

public class ChannelAdapter extends CursorAdapter {

    public ChannelAdapter(Context context, Cursor c) {
        super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_channel_layout, null);
        view.setTag(initViews(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        initViews(cursor, holder);
    }

    private void initViews(Cursor cursor, ViewHolder holder) {
        holder.title.setText(cursor.getString((cursor.getColumnIndex(ChannelTable.COLUMN_TITLE))));
        holder.category.setText(cursor.getString((cursor.getColumnIndex(ChannelTable.COLUMN_CATEGORY))));
        holder.channelImage.setImageResource(R.drawable.feedicon);
    }

    private ViewHolder initViews(View view) {
        ViewHolder holder = new ViewHolder();
        holder.title = (TextView) view.findViewById(R.id.channel_title);
        holder.category = (TextView) view.findViewById(R.id.channel_category);
        holder.channelImage = (ImageView) view.findViewById(R.id.channel_image);
        return holder;
    }

    private static class ViewHolder {
        TextView title;
        TextView category;
        ImageView channelImage;
    }

    @Override
    public Channel getItem(int position) {
        Cursor cursor = (Cursor) super.getItem(position);
        return ChannelDatabaseUtil.createChannelFromCursor(cursor);
    }

}
