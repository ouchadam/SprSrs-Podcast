package com.ouchadam.podcast.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.actionbarsherlock.app.SherlockFragment;
import com.ouchadam.podcast.GetChannelTask;
import com.ouchadam.podcast.OnChannelAdded;
import com.ouchadam.podcast.R;

public class AddSubscriptionFragment extends SherlockFragment implements View.OnClickListener {

    private OnChannelAdded channelAddedListener;

    private Button addSubBtn;
    private EditText editText;

    public AddSubscriptionFragment(){}

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            channelAddedListener = (OnChannelAdded) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnChannelAdded");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);
        initAddBtn(view);
        initEditText(view);
        return view;
    }

    private void initEditText(View view) {
        editText = (EditText) view.findViewById(R.id.add_subscription_edit_text);
    }

    private void initAddBtn(View view) {
        addSubBtn = (Button) view.findViewById(R.id.add_subscription_btn);
        addSubBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        new GetChannelTask().execute(editText.getText().toString());
        channelAddedListener.onChannelAdded();
    }

}
