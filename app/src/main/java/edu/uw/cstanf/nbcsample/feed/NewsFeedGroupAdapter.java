package edu.uw.cstanf.nbcsample.feed;

import android.app.Application;
import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedGroup;

final class NewsFeedGroupAdapter extends RecyclerView.Adapter<NewsFeedGroupAdapter.GroupViewHolder> {
    private static final int HORIZONTAL_ITEM_SPACE = 8;

    private final Application application;
    private final Context context;
    private final List<NewsFeedGroup> newsGroups;

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView groupHeader;
        private final RecyclerView itemRecycler;

        GroupViewHolder(@NonNull View groupView) {
            super(groupView);

            this.groupHeader = groupView.findViewById(R.id.news_group_header);
            this.itemRecycler = groupView.findViewById(R.id.group_recycler);
        }

        void bind(Application application, Context context, NewsFeedGroup newsGroup) {
            LinearLayoutManager itemManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            NewsFeedItemAdapter itemAdapter = new NewsFeedItemAdapter(application, context, newsGroup.getGroupItems());

            itemRecycler.setLayoutManager(itemManager);
            itemRecycler.setAdapter(itemAdapter);
            itemRecycler.addItemDecoration(new HorizontalItemDecoration(HORIZONTAL_ITEM_SPACE));
            groupHeader.setText(newsGroup.getHeader());
        }
    }

    NewsFeedGroupAdapter(Application application, Context context, List<NewsFeedGroup> newsGroups) {
        this.application = application;
        this.context = context;
        this.newsGroups = newsGroups;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View groupView = LayoutInflater.from(context).inflate(R.layout.news_group, viewGroup, false);
        return new GroupViewHolder(groupView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder groupViewHolder, int i) {
        groupViewHolder.bind(application, context, newsGroups.get(i));
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
