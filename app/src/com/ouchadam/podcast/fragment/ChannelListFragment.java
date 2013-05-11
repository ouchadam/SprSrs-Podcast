package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.activity.AbstractSprSrsActivity;
import com.ouchadam.podcast.adapter.ChannelListAdapter;
import com.ouchadam.podcast.database.channel.ChannelLoader;
import com.ouchadam.podcast.pojo.Channel;

import java.util.List;

public class ChannelListFragment extends BaseListFragment implements ChannelLoader.Callback {

    private AddSubscriptionFragment addSubscriptionFragment;
    private ProgressBar progressBar;
    private ChannelLoader channelLoader;
    private ImageManager imageManager;

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
        channelLoader = new ChannelLoader(activity.getApplicationContext(), getLoaderManager(), this);
        channelLoader.startWatchingData();
        LoaderSettings settings = new LoaderSettings.SettingsBuilder()
                .withDisconnectOnEveryCall(true)
                .withCacheManager(new LruBitmapCache(getActivity()))
                .withUpsampling(true)
                .build(getActivity());
        imageManager = new ImageManager(getActivity(), settings);
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
                initFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initFragment() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (addSubscriptionFragment == null) {
            addSubscriptionFragment = AddSubscriptionFragment.newInstance();
        }
        addSubscriptionFragment.setTargetFragment(this, 0);
        ft.add(R.id.add_subscription_container, addSubscriptionFragment).commit();
        View view = getActivity().findViewById(R.id.add_subscription_container);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinish(List<Channel> channels) {
        progressBar.setVisibility(View.GONE);
        getListView().setAdapter(new ChannelListAdapter(channels, getActivity().getLayoutInflater(),
                getActivity(), imageManager));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ChannelListAdapter adapter = (ChannelListAdapter) l.getAdapter();
        Channel channel = adapter.getItem(position);
        ((AbstractSprSrsActivity) getActivity()).getNavigator().toEpisodeList(channel);
    }

    public void onChannelAdded() {
        View view = getActivity().findViewById(R.id.add_subscription_container);
        view.setVisibility(View.GONE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(addSubscriptionFragment).commit();
        addSubscriptionFragment = null;
    }
}
