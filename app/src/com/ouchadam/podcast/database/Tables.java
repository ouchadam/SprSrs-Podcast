package com.ouchadam.podcast.database;

public class Tables {

    public enum Channel {
        _id,
        TITLE,
        LINK,
        IMAGE_TITLE,
        IMAGE_URL,
        CATEGORY,
        RSS_LINK
    }

    public enum Episode {
        _id,
        TITLE,
        DETAILS,
        DATE,
        AUDIO_URL,
        IMAGE_URL,
        CHANNEL
    }

}
