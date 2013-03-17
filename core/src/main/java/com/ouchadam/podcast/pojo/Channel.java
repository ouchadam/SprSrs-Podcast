package com.ouchadam.podcast.pojo;

public class Channel {

    private final String title;
    private final String link;
    private final String rsslink;
    private final String category;
    private final Image image;

    public Channel(String title, String link, String rsslink, String category, Image image) {
        this.title = title;
        this.link = link;
        this.rsslink = rsslink;
        this.category = category;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getCategory() {
        return category;
    }

    public Image getImage() {
        return image;
    }

    public String getRssLink() {
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
