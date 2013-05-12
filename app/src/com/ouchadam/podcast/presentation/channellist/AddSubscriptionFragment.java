package com.ouchadam.podcast.presentation.channellist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.ouchadam.podcast.task.GetChannelTask;
import com.ouchadam.podcast.R;

public class AddSubscriptionFragment extends SherlockFragment implements View.OnClickListener {

    private EditText editText;

    public AddSubscriptionFragment(){}

    public static AddSubscriptionFragment newInstance() {
        return new AddSubscriptionFragment();
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
        Button addSubBtn = (Button) view.findViewById(R.id.add_subscription_btn);
        addSubBtn.setOnClickListener(this);
        Button cancelSubBtn = (Button) view.findViewById(R.id.cancel_subscription_btn);
        cancelSubBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_subscription_btn :
                addSub();
                break;
        }

        ChannelListFragment f = (ChannelListFragment) getTargetFragment();
        f.onChannelAdded();
    }

    private void addSub() {
        new GetChannelTask(getActivity().getContentResolver()).execute(editText.getText().toString());
    }

}
