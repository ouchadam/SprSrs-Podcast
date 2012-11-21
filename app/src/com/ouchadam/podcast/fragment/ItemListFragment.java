package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockListFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.adapter.FeedItemAdapter;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.parser.interfaces.OnParseFinished;
import com.ouchadam.podcast.pojo.FeedItem;
import com.ouchadam.podcast.receiver.ParseReceiver;

import java.util.List;

public class ItemListFragment extends SherlockListFragment implements OnParseFinished {

    private FeedItemAdapter adapter;
    private ParseReceiver receiver;
    private ProgressBar progressBar;
    private Context context;

    public ItemListFragment() {}

    public static ItemListFragment newInstance(String channel, String url) {
        ItemListFragment fragment = new ItemListFragment();
        Bundle b = new Bundle();
        b.putString("channel", channel);
        b.putString("url", url);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        initReceiver();
        startLoadingFeed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        context.startService(IntentFactory.getParseService(getArguments().getString("channel"), getArguments().getString("url")));
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
        adapter = new FeedItemAdapter(context, R.layout.adapter_item_layout, items);
        this.setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(IntentFactory.getMessageDetails(((FeedItem) l.getItemAtPosition(position)).getTitle()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.item_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feed_list_refresh:
                context.startService(IntentFactory.getRefreshService(getArguments().getString("channel")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
