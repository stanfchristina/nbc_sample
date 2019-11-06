package edu.uw.cstanf.nbcsample.feed;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedItem;

public final class NewsFeedItemAdapter extends RecyclerView.Adapter<NewsFeedItemAdapter.ItemViewHolder> {
    private Context context;
    private List<NewsFeedItem> newsItems;

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private Context context;
        private ImageView thumbnail;
        private TextView headline;

        ItemViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            this.context = context;
            this.thumbnail = itemView.findViewById(R.id.news_item_image);
            this.headline = itemView.findViewById(R.id.news_item_text);
        }

        void bind(NewsFeedItem newsItem) {
            headline.setText(newsItem.getHeadline());
            Glide.with(context).load(newsItem.getThumbnailUrl()).into(thumbnail);
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
