package edu.uw.cstanf.nbcsample.feed;

import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;

public class NewsFeedActivity extends AppCompatActivity {
    private static final String LOG_TAG = "NewsFeedActivity";

    private RecyclerView newsFeedRecycler;
    private RecyclerView.Adapter newsFeedAdapter;
    private RecyclerView.LayoutManager newsFeedLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CHRISTINA", "onCreate in newsfeedactivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

       newsFeedRecycler = findViewById(R.id.newsfeed_recycler);
       newsFeedLayoutManager = new LinearLayoutManager(this);
       newsFeedAdapter = new NewsFeedGroupAdapter(this, NewsFeedDataService.getInstance().getNewsFeedGroups());

       newsFeedRecycler.setAdapter(newsFeedAdapter);
       newsFeedRecycler.setLayoutManager(newsFeedLayoutManager);
       newsFeedRecycler.addItemDecoration(new VerticalItemDecoration(12));
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
