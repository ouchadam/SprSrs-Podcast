package com.ouchadam.podcast.presentation.episode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.base.CursorListFragment;
import com.ouchadam.podcast.database.CursorRestorer;
import com.ouchadam.podcast.database.DataUpdater;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.Tables;
import com.ouchadam.podcast.database.episode.EpisodeListRestorer;
import com.ouchadam.podcast.pojo.Episode;
import com.ouchadam.podcast.task.GetEpisodesTask;

import java.util.List;

public class EpisodeListFragment extends CursorListFragment<List<Episode>> {

    private static final String ARG_CHANNEL_TITLE = "key";
    private ProgressBar progressBar;
    private String channelTitle;

    public static EpisodeListFragment newInstance(String channelTitle) {
        EpisodeListFragment episodeListFragment = new EpisodeListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_CHANNEL_TITLE, channelTitle);
        episodeListFragment.setArguments(bundle);
        return episodeListFragment;
    }

    public EpisodeListFragment() {}

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
        View footerView = getActivity().getLayoutInflater().inflate(R.layout.footer_layout, null, false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getListView().addFooterView(footerView);
    }

    @Override
    protected DataUpdater.Query getQuery() {
        return new DataUpdater.QueryBuilder()
                .withUri(SprSrsProvider.URIs.EPISODE.getUri())
                .withSelection(Tables.Episode.CHANNEL + "=?")
                .withSelectionArgs(new String[] {getChannelTitle()})
                .build();
    }

    @Override
    protected CursorRestorer<List<Episode>> getRestorer() {
        return new EpisodeListRestorer();
    }

    @Override
    protected void onListItemClick(Object itemAtPosition) {
        ((AbstractSprSrsActivity) getActivity()).getNavigator().toEpisodeDetails((Episode) itemAtPosition);
    }

    @Override
    protected BaseAdapter getAdapter(List<Episode> data) {
        return new EpisodeListAdapter(getActivity(), R.layout.adapter_item_layout, data);
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
        new GetEpisodesTask(getActivity().getContentResolver()).start(getChannelTitle());
    }

    private String getChannelTitle() {
        if (channelTitle == null) {
            channelTitle = getArguments().getString("key");
        }
        return channelTitle;
    }

    @Override
    public void onDataUpdated(List<Episode> data) {
        super.onDataUpdated(data);
        progressBar.setVisibility(View.GONE);
    }
}
