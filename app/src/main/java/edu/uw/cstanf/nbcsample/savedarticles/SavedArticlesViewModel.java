package edu.uw.cstanf.nbcsample.savedarticles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.List;
import java.util.concurrent.Executors;

import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticlesDao;
import edu.uw.cstanf.nbcsample.AppDatabase;

/** Provides functionality for safely querying and updating saved articles. */
public final class SavedArticlesViewModel extends AndroidViewModel {
    private final ListeningExecutorService executor;
    private final SavedArticlesDao savedArticlesDao;

    public SavedArticlesViewModel(@NonNull Application application) {
        super(application);

        this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.savedArticlesDao = AppDatabase
                .getInstance(application.getApplicationContext()).savedArticlesDao();
    }

    /**
     * Retrieves all saved articles stored in the {@link AppDatabase}.
     */
    LiveData<List<SavedArticle>> getSavedArticles() {
        return savedArticlesDao.getSavedArticles();
    }

    /**
     * Inserts the given article into the {@link AppDatabase}, returning the row ID of the
     * newly inserted item. If the article is already stored in the database, replaces it.
     */
    public ListenableFuture<Long> saveArticle(SavedArticle article) {
        return executor.submit(() -> savedArticlesDao.insert(article));
    }

    /**
     * Removes the given article from the {@link AppDatabase}, returning 1 if successful.
     */
    public ListenableFuture<Integer> deleteArticle(SavedArticle article) {
        return executor.submit(() -> savedArticlesDao.delete(article));
    }
}
