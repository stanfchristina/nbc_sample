package edu.uw.cstanf.nbcsample.feed.data;

/**
 * Immutable data class representing a distinct item in a news feed.
 */
public final class NewsFeedItem {
    private final String headline;
    private final String thumbnailUrl;

    NewsFeedItem(String headline, String thumbnailUrl) {
        this.headline = headline;
        this.thumbnailUrl = thumbnailUrl;
    }

    /** Returns the headline of this news item. */
    public String getHeadline() {
        return headline;
    }

    /** Returns the url linking to this news item's thumbnail. */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
