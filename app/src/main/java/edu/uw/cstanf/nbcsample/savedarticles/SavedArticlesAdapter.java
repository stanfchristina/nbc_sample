package edu.uw.cstanf.nbcsample.savedarticles;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;

/** Manages dynamically displaying articles a user has saved. */
final class SavedArticlesAdapter extends RecyclerView.Adapter<SavedArticlesAdapter.ArticleViewHolder> {
    private static final String LOG_TAG = "SavedArticlesAdapter";

    private final Application application;
    private final Context context;
    private List<SavedArticle> savedArticles;

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final Button removeButton;
        private final ImageView thumbnail;
        private final TextView headline;

        ArticleViewHolder(@NonNull View articleView) {
            super(articleView);

            this.removeButton = articleView.findViewById(R.id.saved_article_button);
            this.thumbnail = articleView.findViewById(R.id.saved_article_image);
            this.headline = articleView.findViewById(R.id.saved_article_text);
        }

        void bind(Application application, Context context, SavedArticle article) {
            Glide.with(context).load(article.thumbnailUrl).into(thumbnail);
            headline.setText(article.headline);

            removeButton.setOnClickListener(v -> {
                SavedArticlesViewModel viewModel = new SavedArticlesViewModel(application);
                Futures.addCallback(viewModel.deleteArticle(article), new FutureCallback<Integer>() {
                    @Override
                    public void onSuccess(@NullableDecl Integer result) {
                        if (result != null && result != -1) {
                            Toast.makeText(context, "Article removed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Log.w(LOG_TAG, "Error attempting to remove saved article: " + t);
                    }
                }, MoreExecutors.directExecutor());
            });
        }
    }

    SavedArticlesAdapter(Application application, Context context, List<SavedArticle> savedArticles) {
        this.application = application;
        this.context = context;
        this.savedArticles = savedArticles;
    }

    public void setData(List<SavedArticle> updatedSavedArticles) {
        this.savedArticles = updatedSavedArticles;
        notifyDataSetChanged();

        Log.i(LOG_TAG, "Updating saved articles data for adapter.");
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View articleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_news_item, parent, false);
        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int i) {
        articleViewHolder.bind(application, context, savedArticles.get(i));
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }
}
