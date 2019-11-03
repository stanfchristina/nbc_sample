package edu.uw.cstanf.nbcsample.feed.data;

public final class NewsFeedItem {

    private String headline;
    private String thumbnailUrl;

    NewsFeedItem(String headline, String thumbnailUrl) {
        this.headline = headline;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getHeadline() {
        return this.headline;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }
}
