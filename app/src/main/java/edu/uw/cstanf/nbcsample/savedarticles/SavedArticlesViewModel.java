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
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticlesDatabase;

public final class SavedArticlesViewModel extends AndroidViewModel {
    private final ListeningExecutorService executor;
    private final SavedArticlesDao savedArticlesDao;

    public SavedArticlesViewModel(@NonNull Application application) {
        super(application);
        this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.savedArticlesDao = SavedArticlesDatabase.getInstance(application.getApplicationContext()).savedArticlesDao();
    }

    public LiveData<List<SavedArticle>> getSavedArticles() {
        return savedArticlesDao.getSavedArticles();
    }

    public ListenableFuture<Long> saveArticle(SavedArticle article) {
        return executor.submit(() -> savedArticlesDao.insert(article));
    }

    public ListenableFuture<Integer> deleteArticle(SavedArticle article) {
        return executor.submit(() -> savedArticlesDao.delete(article));
    }
}
