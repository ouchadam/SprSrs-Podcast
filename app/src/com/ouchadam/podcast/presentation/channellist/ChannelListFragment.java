package com.ouchadam.podcast.presentation.channellist;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.novoda.imageloader.core.ImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.cache.LruBitmapCache;
import com.novoda.util.ClassCaster;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.base.BaseListFragment;
import com.ouchadam.podcast.database.DataUpdater;
import com.ouchadam.podcast.database.SprSrsProvider;
import com.ouchadam.podcast.database.channel.ChannelRestorer;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelListFragment extends BaseListFragment implements DataUpdater.DataUpdatedListener<List<Channel>> {

    private ProgressBar progressBar;
    private DataUpdater<List<Channel>> dataUpdater;
    private ImageManager imageManager;

    private ShowSubscriptionDialog showSubscriptionDialogListener;

    public interface ShowSubscriptionDialog {
        void onShowSubscriptionDialog();
    }

    public static ChannelListFragment newInstance() {
        return new ChannelListFragment();
    }

    @Override
    public CharSequence getFragmentPageTitle() {
        return "Subscriptions";
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        showSubscriptionDialogListener = ClassCaster.toListener(activity);
        if (dataUpdater != null) {
            dataUpdater.stopWatchingData();
        }

        DataUpdater.Query query = new DataUpdater.QueryBuilder().withUri(SprSrsProvider.URIs.CHANNEL.getUri()).build();

        dataUpdater = new DataUpdater<List<Channel>>(activity, query, new ChannelRestorer(), this, getLoaderManager());
        dataUpdater.startWatchingData();
        imageManager = new ImageManager(getActivity(), createLoaderSettings());
    }

    private LoaderSettings createLoaderSettings() {
        return new LoaderSettings.SettingsBuilder()
                    .withDisconnectOnEveryCall(true)
                    .withCacheManager(new LruBitmapCache(getActivity()))
                    .withUpsampling(true)
                    .build(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.subscription, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subscription_menu_add:
                showSubscriptionDialogListener.onShowSubscriptionDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDataUpdated(List<Channel> data) {
        progressBar.setVisibility(View.GONE);
        getListView().setAdapter(new ChannelListAdapter(data, getActivity().getLayoutInflater(),
                getActivity(), imageManager));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ChannelListAdapter adapter = (ChannelListAdapter) l.getAdapter();
        Channel channel = adapter.getItem(position);
        ((AbstractSprSrsActivity) getActivity()).getNavigator().toEpisodeList(channel);
    }

}
