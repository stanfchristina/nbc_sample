package edu.uw.cstanf.nbcsample.feed;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedItem;
import edu.uw.cstanf.nbcsample.savedarticles.SavedArticlesViewModel;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;

public final class NewsFeedItemAdapter extends RecyclerView.Adapter<NewsFeedItemAdapter.ItemViewHolder> {
    private final Application application;
    private final Context context;
    private final List<NewsFeedItem> newsItems;

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageButton saveButton;
        private final ImageView thumbnail;
        private final TextView headline;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            this.saveButton = itemView.findViewById(R.id.news_item_button);
            this.thumbnail = itemView.findViewById(R.id.news_item_image);
            this.headline = itemView.findViewById(R.id.news_item_text);
        }

        void bind(Application application, Context context, NewsFeedItem newsItem) {
            Glide.with(context).load(newsItem.getThumbnailUrl()).into(thumbnail);
            headline.setText(newsItem.getHeadline());

            saveButton.setOnClickListener(v -> {
                SavedArticlesViewModel savedArticlesViewModel = new SavedArticlesViewModel(application);
                Futures.addCallback(savedArticlesViewModel.saveArticle(new SavedArticle()), new FutureCallback<Long>() {
                    @Override
                    public void onSuccess(@NullableDecl Long result) {
                        if (result != null && result != -1) {
                            Log.i("CHRISTINA", "saving article...");
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.w("CHRISTINA", "Failed to save article: " + t);
                    }
                }, MoreExecutors.directExecutor());
            });
        }
    }

    NewsFeedItemAdapter(Application application, Context context, List<NewsFeedItem> newsItems) {
        this.application = application;
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bind(application, context, newsItems.get(i));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
}
