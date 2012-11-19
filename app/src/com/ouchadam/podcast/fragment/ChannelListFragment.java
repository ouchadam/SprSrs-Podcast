package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
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
import com.ouchadam.podcast.adapter.ChannelAdapter;
import com.ouchadam.podcast.builder.IntentFactory;
import com.ouchadam.podcast.database.ChannelTable;
import com.ouchadam.podcast.pojo.Channel;
import com.ouchadam.podcast.provider.FeedProvider;

public class ChannelListFragment extends SherlockListFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_CURSOR = 1;

    private AddSubscriptionFragment addSubscriptionFragment;
    private ProgressBar progressBar;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        getLoaderManager().initLoader(LOADER_CURSOR, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_list, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(IntentFactory.getSubscriptionFeed(((Channel) getListAdapter().getItem(position))));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        return new CursorLoader(context, FeedProvider.CONTENT_CHANNEL_URI, null,
                null,
                null,
                ChannelTable.COLUMN_ID);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        if (getListAdapter() == null && getActivity() != null) {
            setListAdapter(new ChannelAdapter(context, cursor));
        }else {
            ((ChannelAdapter) getListAdapter()).changeCursor(cursor);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (getListAdapter() instanceof ChannelAdapter) {
            ((ChannelAdapter) getListAdapter()).changeCursor(null);
        }
        loader = null;
        setListAdapter(null);
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

    public void onChannelAdded() {
        View view = getActivity().findViewById(R.id.add_subscription_container);
        view.setVisibility(View.GONE);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.remove(addSubscriptionFragment).commit();
        addSubscriptionFragment = null;
    }
}
