package edu.uw.cstanf.nbcsample.feed;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedGroup;

final class NewsFeedGroupAdapter extends RecyclerView.Adapter<NewsFeedGroupAdapter.GroupViewHolder> {
    private final Context context;
    private final List<NewsFeedGroup> newsGroups;

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final TextView groupHeader;
        private final RecyclerView itemRecycler;

        GroupViewHolder(@NonNull View groupView, Context context) {
            super(groupView);

            this.context = context;
            this.groupHeader = groupView.findViewById(R.id.news_group_header);
            this.itemRecycler = groupView.findViewById(R.id.group_recycler);
        }

        void bind(NewsFeedGroup newsGroup) {
            LinearLayoutManager itemManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            NewsFeedItemAdapter itemAdapter = new NewsFeedItemAdapter(context, newsGroup.getGroupItems());

            itemRecycler.setLayoutManager(itemManager);
            itemRecycler.setAdapter(itemAdapter);
            itemRecycler.addItemDecoration(new HorizontalItemDecoration(16));
            groupHeader.setText(newsGroup.getHeader());
        }
    }

    NewsFeedGroupAdapter(Context context, List<NewsFeedGroup> newsGroups) {
        this.context = context;
        this.newsGroups = newsGroups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View groupView = LayoutInflater.from(context).inflate(R.layout.news_group, viewGroup, false);
        return new GroupViewHolder(groupView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder groupViewHolder, int i) {
        groupViewHolder.bind(newsGroups.get(i));
    }

    @Override
    public int getItemCount() {
        return newsGroups.size();
    }

    static class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
        private final int horizontalSpace;

        HorizontalItemDecoration(int horizontalSpace) {
            this.horizontalSpace = horizontalSpace;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int endItemPosition = parent.getAdapter().getItemCount() - 1;
            if (parent.getChildAdapterPosition(view) != endItemPosition) {
                outRect.right = horizontalSpace;
            }
        }
    }
}
