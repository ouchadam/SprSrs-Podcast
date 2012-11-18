package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.adapter.ChannelAdapter;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.database.ChannelDatabaseUtil;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelListFragment extends ListFragment {

    private ChannelAdapter adapter;
    private ProgressBar progressBar;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
//        addTestChannel();
        initAdapter(getChannels());
    }

    private void addTestChannel() {
        Channel channel = new Channel();
        channel.setTitle("Test Channel");
        channel.setLink("http://testlink.com");
        channel.setCategory("Category : Test");
        channel.setImage(new Channel.Image("http://google.com", "Image Title", "http://google.com"));
        ChannelDatabaseUtil.addChannel(channel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        return view;
    }

    private List<Channel> getChannels(){
        return ChannelDatabaseUtil.getAllChannels();
    }

    private void initAdapter(List<Channel> items) {
        adapter = new ChannelAdapter(context, R.layout.adapter_channel_layout, items);
        this.setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(IntentFactory.getSubscriptionFeed());
    }

}
