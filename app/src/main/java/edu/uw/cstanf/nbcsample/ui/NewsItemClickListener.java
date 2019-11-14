package edu.uw.cstanf.nbcsample.ui;

import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;

public interface NewsItemClickListener {
    void onItemClicked(String articleLink);

    void onRemoveButtonClicked(SavedArticle article);

    void onSaveButtonClicked();
}
