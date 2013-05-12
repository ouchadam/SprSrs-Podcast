package com.ouchadam.podcast.presentation.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ouchadam.podcast.R;
import com.ouchadam.podcast.base.AbstractSprSrsActivity;
import com.ouchadam.podcast.loader.ImageLoader;
import com.ouchadam.podcast.pojo.Episode;

import java.io.IOException;

public class EpisodeDetailsActivity extends AbstractSprSrsActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, LoaderManager.LoaderCallbacks<Bitmap> {

    private static final int LOADER_IMAGE = 1;
    private static MediaPlayer mediaPlayer;
    private Episode episode;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        episode = (Episode) getIntent().getSerializableExtra("episode");
        initTextViews();
        initButton();
        initImage();
    }

    private void initImage() {
        imageView = (ImageView) findViewById(R.id.channel_image);
        getLoaderManager().initLoader(LOADER_IMAGE, null, this);
    }

    private void initButton() {
        button = (Button) findViewById(R.id.play_button);
        button.setOnClickListener(this);
    }

    private void initTextViews() {
        TextView title = (TextView) findViewById(R.id.message_title);
        title.setText(episode.getTitle());
        TextView details = (TextView) findViewById(R.id.message_details);
        details.setText(episode.getDescription());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.play_button) {
            if (mediaPlayer == null) {
                startStreaming();
                button.setText("Stop!");
            } else {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    button.setText("Start!");
                }
            }
        }
    }

    private void startStreaming() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        try {
            Log.e("XXXXXX", "Trying to load : " + episode.getLink());
            mediaPlayer.setDataSource(episode.getLink());
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int i, Bundle bundle) {
        return new ImageLoader(this, "");
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> bitmapLoader, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> bitmapLoader) {
        bitmapLoader.reset();
    }
}
