package edu.uw.cstanf.nbcsample.feed;

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

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedItem;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticlesDao;

public final class NewsFeedItemAdapter extends RecyclerView.Adapter<NewsFeedItemAdapter.ItemViewHolder> {
    private final Context context;
    private final List<NewsFeedItem> newsItems;

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final ImageButton saveButton;
        private final ImageView thumbnail;
        private final TextView headline;

        ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;
            this.saveButton = itemView.findViewById(R.id.news_item_button);
            this.thumbnail = itemView.findViewById(R.id.news_item_image);
            this.headline = itemView.findViewById(R.id.news_item_text);
        }

        void bind(NewsFeedItem newsItem) {
            Glide.with(context).load(newsItem.getThumbnailUrl()).into(thumbnail);
            headline.setText(newsItem.getHeadline());

            saveButton.setOnClickListener(v -> {
                SavedArticlesDao service;
                Log.i("CHRISTINA", "saving article...");
            });
        }
    }

    public NewsFeedItemAdapter(Context context, List<NewsFeedItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_item, viewGroup, false);
        return new ItemViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.bind(newsItems.get(i));
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }
}
