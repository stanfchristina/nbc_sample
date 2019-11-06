package edu.uw.cstanf.nbcsample.feed;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

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
    }
}
