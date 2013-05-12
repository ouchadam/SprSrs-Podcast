package com.ouchadam.podcast.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

public class IntentBuilder {

    private final Context context;
    private Intent intent;

    public IntentBuilder(Context context) {
        this.context = context;
    }

    public IntentBuilder start(Class<? extends Activity> klass) {
        this.intent = ActivityIntentFactory.getActivity(context, klass);
        return this;
    }

    public IntentBuilder start() {
        this.intent = new Intent();
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

    protected static class ActivityIntentFactory {
        public static Intent getActivity(Context context, Class<? extends Activity> klass) {
            return new Intent(context, klass);
        }
    }

}

