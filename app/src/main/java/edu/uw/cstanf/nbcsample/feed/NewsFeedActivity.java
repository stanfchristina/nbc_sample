package edu.uw.cstanf.nbcsample.feed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import edu.uw.cstanf.nbcsample.R;

public class NewsFeedActivity extends AppCompatActivity {
    private RecyclerView newsFeedRecycler;
    private RecyclerView.Adapter newsFeedAdapter;
    private RecyclerView.LayoutManager newsFeedLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);

        newsFeedRecycler = (RecyclerView) findViewById(R.id.newsfeed_recycler_view);
        newsFeedLayoutManager = new LinearLayoutManager(this);
        newsFeedAdapter = new NewsFeedAdapter();

    }
}
