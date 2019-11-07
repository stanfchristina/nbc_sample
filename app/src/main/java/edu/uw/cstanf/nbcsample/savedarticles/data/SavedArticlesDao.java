package edu.uw.cstanf.nbcsample.savedarticles.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data access object to query and update the saved articles table.
 */
@Dao
public interface SavedArticlesDao {
    @Query("SELECT * FROM saved_articles")
    LiveData<List<SavedArticle>> getSavedArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(SavedArticle article);

    @Delete
    int delete(SavedArticle article);
}
