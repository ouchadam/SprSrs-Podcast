package com.ouchadam.podcast.fragment;

import android.app.Activity;
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
import com.ouchadam.podcast.GetEpisodesTask;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.adapter.FeedItemAdapter;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.database.episode.EpisodeLoader;
import com.ouchadam.podcast.pojo.Episode;

import java.util.List;

public class ItemListFragment extends SherlockListFragment implements EpisodeLoader.Callback {

    private ProgressBar progressBar;
    private EpisodeLoader episodeLoader;

    public ItemListFragment() {}

    public static ItemListFragment newInstance(String channel, String url) {
        ItemListFragment fragment = new ItemListFragment();
        fragment.setArguments(createArgs(channel, url));
        return fragment;
    }

    private static Bundle createArgs(String channel, String url) {
        Bundle b = new Bundle();
        b.putString("channel", channel);
        b.putString("url", url);
        return b;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        startLoadingFeed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void startLoadingFeed(){
        episodeLoader = new EpisodeLoader(getActivity(), getLoaderManager(), this, getArguments().getString("channel"));
        episodeLoader.startWatchingData();
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
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.footer_layout, null, false);
        getListView().addFooterView(footerView);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(IntentFactory.getMessageDetails(((Episode) l.getItemAtPosition(position))));
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
                progressBar.setVisibility(View.VISIBLE);
                startRefreshService();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startRefreshService() {
        String url = getArguments().getString("url");
        String channel = getArguments().getString("channel");
        new GetEpisodesTask(getActivity().getContentResolver()).execute(url, channel);
    }

    @Override
    public void onFinish(List<Episode> episodes) {
        progressBar.setVisibility(View.GONE);
        getListView().setAdapter(new FeedItemAdapter(getActivity().getApplicationContext(), R.layout.adapter_item_layout, episodes));
    }

}
