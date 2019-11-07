package edu.uw.cstanf.nbcsample.savedarticles.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data access object to query and update the {@link SavedArticlesDatabase}.
 */
@Dao
public interface SavedArticlesDao {
    /**
     * Retrieves all saved articles stored in the {@link SavedArticlesDatabase}.
     */
    @Query("SELECT * FROM saved_articles")
    LiveData<List<SavedArticle>> getSavedArticles();

    /**
     * Inserts the given article into the {@link SavedArticlesDatabase}, returning the row ID of the
     * newly inserted item. If the article is already stored in the database, replaces it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SavedArticle article);

    /**
     * Removes the given article from the {@link SavedArticlesDatabase}, returning 1 if successfully
     * removed.
     */
    @Delete
    int delete(SavedArticle article);
}
