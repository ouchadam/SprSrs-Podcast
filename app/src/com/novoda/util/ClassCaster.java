package com.novoda.util;

import android.app.Activity;

public class ClassCaster {

    public static <T> T toListener(Activity activity) {
        try {
            return (T) activity;
        } catch (ClassCastException e) {
            throw new RuntimeException("The parent " + activity.toString() + " does not implement the wanted interface");
        }
    }

    public static <T> T from(Object implementor) {
        return (T) implementor;
    }

}