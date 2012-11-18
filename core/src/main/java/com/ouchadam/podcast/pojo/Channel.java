package com.ouchadam.podcast.pojo;

import java.net.MalformedURLException;
import java.net.URL;

public class Channel {

    private String title;
    private URL link;
    private URL rsslink;
    private String category;
    private Image image;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setRssLink(String link) {
        try {
            this.rsslink = new URL(link);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public URL getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public Image getImage() {
        return image;
    }

    public URL getRssLink() {
        return rsslink;
    }

    public static class Image {

        public final String imageUrl;
        public final String imageTitle;
        public final String imageLink;

        public Image(String imageUrl, String imageTitle, String imageLink) {
            this.imageUrl = imageUrl;
            this.imageTitle = imageTitle;
            this.imageLink = imageLink;
        }
    }
}
