package com.ouchadam.podcast.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ouchadam.podcast.R;

public class AddSubscriptionFragment extends Fragment implements View.OnClickListener {

    private Button addSubBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_subscription, container, false);
        initAddBtn(view);
        return view;
    }

    private void initAddBtn(View view) {
        addSubBtn = (Button) view.findViewById(R.id.add_subscription_btn);
        addSubBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    }
}
