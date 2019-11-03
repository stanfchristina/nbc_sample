package edu.uw.cstanf.nbcsample.feed;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

final class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder> {

    public static class NewsFeedViewHolder extends RecyclerView.ViewHolder {
        public NewsFeedViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public NewsFeedAdapter() {
    }

    @NonNull
    @Override
    public NewsFeedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedViewHolder newsFeedViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
