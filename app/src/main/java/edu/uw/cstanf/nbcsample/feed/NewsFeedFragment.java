package edu.uw.cstanf.nbcsample.feed;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
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

public class NewsFeedFragment extends Fragment {
    private static final String LOG_TAG = "NewsFeedFragment";

    public static NewsFeedFragment newInstance() {
        return new NewsFeedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        RecyclerView newsFeedRecycler = root.findViewById(R.id.newsfeed_recycler);
        RecyclerView.LayoutManager newsFeedLayoutManager = new LinearLayoutManager(this.getContext());
        RecyclerView.Adapter newsFeedAdapter = new NewsFeedGroupAdapter(this.getActivity().getApplication(), this.getContext(), NewsFeedDataService.getInstance().getNewsFeedGroups());

        newsFeedRecycler.setAdapter(newsFeedAdapter);
        newsFeedRecycler.setLayoutManager(newsFeedLayoutManager);
        newsFeedRecycler.addItemDecoration(new VerticalItemDecoration(12));

        return root;
    }

    static class VerticalItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpace;

        VerticalItemDecoration(int verticalSpace) {
            this.verticalSpace = verticalSpace;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            int endItemPosition = parent.getAdapter().getItemCount() - 1;
            if (parent.getChildAdapterPosition(view) != endItemPosition) {
                outRect.bottom = verticalSpace;
            }
        }
    }
}
