package com.ouchadam.podcast.pojo;

public class Channel {

    private String title;
    private String link;
    private String category;
    private Image image;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(Image image) {
        this.image = image;
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
