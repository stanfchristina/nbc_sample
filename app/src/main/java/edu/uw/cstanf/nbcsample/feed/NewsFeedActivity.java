package edu.uw.cstanf.nbcsample.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import edu.uw.cstanf.nbcsample.R;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;

public class NewsFeedActivity extends AppCompatActivity {
    private RecyclerView newsFeedRecycler;
    private RecyclerView.Adapter newsFeedAdapter;
    private RecyclerView.LayoutManager newsFeedLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("CHRISTINA", "onCreate in newsfeedactivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

       newsFeedRecycler = (RecyclerView) findViewById(R.id.newsfeed_recycler);
       newsFeedLayoutManager = new LinearLayoutManager(this);
       newsFeedAdapter = new NewsFeedGroupAdapter(this, NewsFeedDataService.getInstance(this.getApplicationContext()).getNewsFeedGroups());

       newsFeedRecycler.setAdapter(newsFeedAdapter);
       newsFeedRecycler.setLayoutManager(newsFeedLayoutManager);
    }
}
