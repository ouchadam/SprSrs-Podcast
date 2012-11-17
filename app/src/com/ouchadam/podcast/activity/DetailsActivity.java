package com.ouchadam.podcast.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ouchadam.podcast.Message;
import com.ouchadam.podcast.R;
import com.ouchadam.podcast.database.DatabaseUtil;
import com.ouchadam.podcast.loader.ImageLoader;

import java.io.IOException;

public class DetailsActivity extends Activity implements View.OnClickListener, MediaPlayer.OnPreparedListener, LoaderManager.LoaderCallbacks<Bitmap> {

    private static final int LOADER_IMAGE = 1;
    private Message message;
    private static MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String messagePos = getIntent().getStringExtra("title");
        message = DatabaseUtil.getFeedItem(null, messagePos);
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
        title.setText(message.getTitle());
        TextView details = (TextView) findViewById(R.id.message_details);
        details.setText(message.getDescription());
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
            mediaPlayer.setDataSource(message.getLink().toString());
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
        return new ImageLoader(this, message.getImageLink().toString());
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
