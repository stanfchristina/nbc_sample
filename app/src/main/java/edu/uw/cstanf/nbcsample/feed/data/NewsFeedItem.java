package edu.uw.cstanf.nbcsample.feed.data;

import java.util.Objects;

/**
 * Immutable data class representing a distinct item in a news feed.
 */
public final class NewsFeedItem {
    private final String headline;
    private final String articleUrl;
    private final String thumbnailUrl;

    NewsFeedItem(String headline, String articleUrl, String thumbnailUrl) {
        this.headline = headline;
        this.articleUrl = articleUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    /** Returns the headline of this news item. */
    public String getHeadline() {
        return headline;
    }

    /** Returns the url linking to this news item's expanded article view. */
    public String getArticleUrl() {
        return articleUrl;
    }

    /** Returns the url linking to this news item's thumbnail. */
    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NewsFeedItem)) {
            return false;
        }
        NewsFeedItem other = (NewsFeedItem) o;
        return other.headline.equals(this.headline) &&
                other.articleUrl.equals(this.articleUrl) &&
                other.thumbnailUrl.equals(this.thumbnailUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headline, articleUrl, thumbnailUrl);
    }
}
