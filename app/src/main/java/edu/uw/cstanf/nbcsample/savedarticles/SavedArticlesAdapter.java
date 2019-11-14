package edu.uw.cstanf.nbcsample.savedarticles;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import edu.uw.cstanf.nbcsample.ui.NewsItemClickListener;

/** Manages dynamically displaying articles a user has saved. */
final class SavedArticlesAdapter extends RecyclerView.Adapter<SavedArticlesAdapter.ArticleViewHolder> {
    private static final String LOG_TAG = "SavedArticlesAdapter";

    private final Context context;
    private final NewsItemClickListener newsItemClickListener;
    private List<SavedArticle> savedArticles;

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final Button removeButton;
        private final ImageView thumbnail;
        private final TextView headline;
        private final View articleView;

        ArticleViewHolder(@NonNull View articleView) {
            super(articleView);

            this.articleView = articleView;
            this.removeButton = articleView.findViewById(R.id.saved_article_button);
            this.thumbnail = articleView.findViewById(R.id.saved_article_image);
            this.headline = articleView.findViewById(R.id.saved_article_text);
        }

        void bind(Context context, NewsItemClickListener newsItemClickListener, SavedArticle article) {
            Glide.with(context).load(article.thumbnailUrl).into(thumbnail);
            headline.setText(article.headline);

            articleView.setOnClickListener(v -> newsItemClickListener.onItemClicked("https://www.nbcnews.com/video/all-12-boys-and-coach-rescued-from-thai-cave-1273710147636"));
            removeButton.setOnClickListener(v -> newsItemClickListener.onRemoveButtonClicked(article));
        }
    }

    SavedArticlesAdapter(Context context, NewsItemClickListener newsItemClickListener, List<SavedArticle> savedArticles) {
        this.context = context;
        this.newsItemClickListener = newsItemClickListener;
        this.savedArticles = savedArticles;
    }

    /** Sets this adapter's data to the new provided data then refreshes relevant views. */
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
        articleViewHolder.bind(context, newsItemClickListener, savedArticles.get(i));
    }

    @Override
    public int getItemCount() {
        return savedArticles.size();
    }
}
