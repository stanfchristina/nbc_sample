package edu.uw.cstanf.nbcsample;

import android.app.Application;
import android.util.Log;

import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;

public class App extends Application {

    private final String SOURCE_URL = "https://s3.amazonaws.com/shrekendpoint/news.json";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("CHRISTINA", "here");
        NewsFeedDataService.getInstance(this.getApplicationContext()).fetchData(SOURCE_URL);
    }
}
