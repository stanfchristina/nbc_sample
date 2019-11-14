package edu.uw.cstanf.nbcsample.ui;

import edu.uw.cstanf.nbcsample.feed.data.NewsFeedItem;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;

/** Functionality for handling various click events on a news item. */
public interface NewsItemClickListener {
    /** Opens the given link to a news article in a web browser. */
    void onItemClicked(String articleLink);

    /** Removes the given saved article from the {@link edu.uw.cstanf.nbcsample.AppDatabase}. */
    void onRemoveButtonClicked(SavedArticle article);

    /** Saves the given news item into the {@link edu.uw.cstanf.nbcsample.AppDatabase}. */
    void onSaveButtonClicked(NewsFeedItem newsItem);
}
