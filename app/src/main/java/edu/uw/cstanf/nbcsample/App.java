package edu.uw.cstanf.nbcsample;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import edu.uw.cstanf.nbcsample.feed.NewsFeedActivity;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class App extends Application {
    private static final String LOG_TAG = "App";
    private static final String SOURCE_URL = "https://s3.amazonaws.com/shrekendpoint/news.json";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "here");

        Futures.addCallback(NewsFeedDataService.getInstance().updateData(SOURCE_URL), new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@NullableDecl Boolean dataLoaded) {
                if (dataLoaded != null && dataLoaded) {
                    Intent intent = new Intent(getApplicationContext(), NewsFeedActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.w(LOG_TAG, "Error fetching news feed data on startup: " + t);
            }
        }, MoreExecutors.directExecutor());
    }
}
