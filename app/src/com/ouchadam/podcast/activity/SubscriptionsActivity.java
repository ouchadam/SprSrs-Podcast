package com.ouchadam.podcast.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.ouchadam.podcast.OnChannelAdded;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.fragment.AddSubscriptionFragment;

public class SubscriptionsActivity extends SherlockFragmentActivity implements OnChannelAdded {

    private AddSubscriptionFragment addSubscriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.subscription, menu);
        return true;
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (addSubscriptionFragment == null) {
            addSubscriptionFragment = AddSubscriptionFragment.newInstance();
        }
        ft.add(R.id.add_subscription_container, addSubscriptionFragment);
        ft.commit();
        View view = findViewById(R.id.add_subscription_container);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onChannelAdded() {
        View view = findViewById(R.id.add_subscription_container);
        view.setVisibility(View.GONE);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(addSubscriptionFragment).commit();
        addSubscriptionFragment = null;
    }
}
