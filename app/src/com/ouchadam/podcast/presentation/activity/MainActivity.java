package com.ouchadam.podcast.presentation.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.ouchadam.podcast.R;
import com.ouchadam.podcast.TitleUpdater;
import com.ouchadam.podcast.adapter.PageAdapter;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.presentation.channellist.AddSubscriptionFragment;
import com.ouchadam.podcast.presentation.channellist.ChannelListFragment;
import com.ouchadam.podcast.task.GetChannelTask;

import static com.ouchadam.podcast.presentation.channellist.AddSubscriptionFragment.AddSubscription;

public class MainActivity extends AbstractSprSrsActivity implements TitleUpdater, AddSubscription, ChannelListFragment.ShowSubscriptionDialog {

    private AddSubscriptionFragment addSubscriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViewPager();
    }

    private void initViewPager() {
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager)findViewById(R.id.view_pager);
        pager.setAdapter(adapter);
    }

    @Override
    public void updateTitle(CharSequence title) {
        setTitle(title);
    }

    @Override
    public void onAddSubscription(String url) {
        hideSubscriptionFragment();
        new GetChannelTask(getContentResolver()).start(url);
    }

    @Override
    public void onCancelSubscription() {
        hideSubscriptionFragment();
    }

    private void hideSubscriptionFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(addSubscriptionFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onShowSubscriptionDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        addSubscriptionFragment = getAddSubscriptionFragment();
        fragmentTransaction.replace(R.id.add_subscription_container, addSubscriptionFragment);
        fragmentTransaction.commit();
    }

    private AddSubscriptionFragment getAddSubscriptionFragment() {
        if (addSubscriptionFragment != null) {
            return addSubscriptionFragment;
        }
        return AddSubscriptionFragment.newInstance();
    }

}
