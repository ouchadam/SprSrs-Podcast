package com.ouchadam.podcast.presentation.channellist;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.novoda.util.ClassCaster;
import com.ouchadam.podcast.R;

public class AddSubscriptionFragment extends SherlockFragment implements View.OnClickListener {

    private EditText editText;
    private AddSubscription addSubscriptionListener;

    public interface AddSubscription {
        void onAddSubscription(String url);
        void onCancelSubscription();
    }

    public AddSubscriptionFragment(){}

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addSubscriptionListener = ClassCaster.toListener(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        initButtons(view);
        initEditText(view);
    }

    private void initButtons(View view) {
        Button addSubBtn = (Button) view.findViewById(R.id.add_subscription_btn);
        addSubBtn.setOnClickListener(this);
        Button cancelSubBtn = (Button) view.findViewById(R.id.cancel_subscription_btn);
        cancelSubBtn.setOnClickListener(this);
    }

    private void initEditText(View view) {
        editText = (EditText) view.findViewById(R.id.add_subscription_edit_text);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_subscription_btn :
                addSubscriptionListener.onAddSubscription(getSubscriptionUrl());
                break;
            case R.id.cancel_subscription_btn:
                addSubscriptionListener.onCancelSubscription();
                break;
        }
    }

    private String getSubscriptionUrl() {
        return editText.getText().toString();
    }

}
