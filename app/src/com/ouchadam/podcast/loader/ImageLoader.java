package com.ouchadam.podcast.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageLoader extends AsyncTaskLoader<Bitmap> {

    private Bitmap bitmap;
    private String url;

    public ImageLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (bitmap != null) {
            deliverResult(bitmap);
        }
        if (takeContentChanged() || bitmap == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(Bitmap bitmap) {
        if (!isReset()) {
            this.bitmap = bitmap;
            super.deliverResult(bitmap);
        }
    }

    @Override
    public Bitmap loadInBackground() {
        Bitmap temp = null;
        try {
            temp = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;

    }

}