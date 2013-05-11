package com.ouchadam.podcast.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

import static com.ouchadam.podcast.navigation.Navigator.ActivityIntentFactory.getActivity;

public class IntentBuilder {

    private final Context context;
    private Intent intent;

    public IntentBuilder(Context context) {
        this.context = context;
    }

    public IntentBuilder start(Class<? extends Activity> klass) {
        this.intent = getActivity(context, klass);
        return this;
    }

    public IntentBuilder with(String key, Serializable value) {
        addExtra(key, value);
        return this;
    }

    private void addExtra(String key, Serializable value) {
        getIntent().putExtra(key, value);
    }

    public IntentBuilder with(int flags) {
        addFlags(flags);
        return this;
    }

    private void addFlags(int flags) {
        getIntent().setFlags(flags);
    }

    public Intent build() {
        return getIntent();
    }

    private Intent getIntent() {
        if (intent == null) {
            throw new RuntimeException("You must call start on the IntentBuilder");
        }
        return intent;
    }

}

