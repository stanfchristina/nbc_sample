package edu.uw.cstanf.nbcsample.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;
import edu.uw.cstanf.nbcsample.ui.NewsItemClickListener;
import edu.uw.cstanf.nbcsample.ui.VerticalItemDecoration;

/** Displays a news feed populated by {@link NewsFeedDataService}. */
public class NewsFeedFragment extends Fragment {
    private static final int VERTICAL_ITEM_PADDING = 16;

    private final NewsItemClickListener newsItemClickListener;

    public NewsFeedFragment(NewsItemClickListener newsItemClickListener) {
        this.newsItemClickListener = newsItemClickListener;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        RecyclerView newsFeedRecycler = root.findViewById(R.id.newsfeed_recycler);
        RecyclerView.LayoutManager newsFeedLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.Adapter newsFeedAdapter = new NewsFeedGroupAdapter(getContext(), NewsFeedDataService.getInstance().getNewsFeedGroups(), newsItemClickListener);

        newsFeedRecycler.setAdapter(newsFeedAdapter);
        newsFeedRecycler.setLayoutManager(newsFeedLayoutManager);
        newsFeedRecycler.addItemDecoration(new VerticalItemDecoration(VERTICAL_ITEM_PADDING));

        return root;
    }
}
