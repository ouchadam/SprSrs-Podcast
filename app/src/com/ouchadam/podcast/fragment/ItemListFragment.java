package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.adapter.FeedItemAdapter;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.parser.interfaces.OnParseFinished;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.receiver.ParseReceiver;

import java.util.List;

public class ItemListFragment extends ListFragment implements OnParseFinished {

    private FeedItemAdapter adapter;
    private ParseReceiver receiver;
    private ProgressBar progressBar;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        initReceiver();
        startLoadingFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListFooter();
    }

    private void initListFooter() {
        View footerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);
        getListView().addFooterView(footerView);
    }

    private void initReceiver() {
        receiver = new ParseReceiver();
        receiver.setOnParseListener(this);
    }

    private void startLoadingFeed(){
        context.startService(IntentFactory.getParseService());
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ParseReceiver.ACTION_ON_PARSE_FINISHED);
        context.registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        context.unregisterReceiver(receiver);
    }

    @Override
    public void onParseFinished(List<FeedItem> items) {
        initAdapter(items);
        progressBar.setVisibility(View.GONE);
    }

    private void initAdapter(List<FeedItem> items) {
        adapter = new FeedItemAdapter(context, R.layout.item_feed, items);
        this.setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(IntentFactory.getMessageDetails(((FeedItem) l.getItemAtPosition(position)).getTitle()));
    }

}
