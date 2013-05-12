package com.ouchadam.podcast.presentation.episode;

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
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.database.episode.EpisodeLoader;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.pojo.Episode;
import com.ouchadam.podcast.task.GetEpisodesTask;

import java.util.List;

public class EpisodeListFragment extends SherlockListFragment implements EpisodeLoader.Callback {

    private ProgressBar progressBar;
    private EpisodeLoader episodeLoader;
    private Channel channel;

    public EpisodeListFragment() {}

    public static EpisodeListFragment newInstance(Channel channel) {
        EpisodeListFragment fragment = new EpisodeListFragment();
        fragment.setArguments(createArgs(channel));
        return fragment;
    }

    private static Bundle createArgs(Channel channel) {
        Bundle b = new Bundle();
        b.putSerializable("channel", channel);
        return b;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        channel = (Channel) getArguments().getSerializable("channel");
        startLoadingFeed();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void startLoadingFeed(){
        episodeLoader = new EpisodeLoader(getActivity(), getLoaderManager(), this, channel.getTitle());
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
    public void onListItemClick(ListView listView, View v, int position, long id) {
        super.onListItemClick(listView, v, position, id);
        Episode episode = (Episode) listView.getItemAtPosition(position);
        ((AbstractSprSrsActivity) getActivity()).getNavigator().toEpisodeDetails(episode);
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
        new GetEpisodesTask(getActivity().getContentResolver()).start(channel);
    }

    @Override
    public void onFinish(List<Episode> episodes) {
        progressBar.setVisibility(View.GONE);
        EpisodeListAdapter adapter = new EpisodeListAdapter(getActivity().getApplicationContext(), R.layout.adapter_item_layout, episodes);
        getListView().setAdapter(adapter);
    }

}
