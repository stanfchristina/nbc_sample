package edu.uw.cstanf.nbcsample;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.MoreExecutors;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import edu.uw.cstanf.nbcsample.feed.NewsFeedFragment;
import edu.uw.cstanf.nbcsample.feed.data.NewsFeedDataService;
import edu.uw.cstanf.nbcsample.savedarticles.SavedArticlesFragment;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "MainActivity";
    private static final String SOURCE_URL = "https://s3.amazonaws.com/shrekendpoint/news.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            Futures.addCallback(NewsFeedDataService.getInstance().updateData(SOURCE_URL), new FutureCallback<Boolean>() {
                @Override
                public void onSuccess(@NullableDecl Boolean dataLoaded) {
                    if (dataLoaded != null && dataLoaded) {
                        // Show the news feed default on start-up.
                        // On older devices, this async task may complete after onCreate() so
                        // force the news feed to display and accept potential state loss
                        // because no state should change between start-up (blank screen) and
                        // displaying the initial UI.
                        startFragment(NewsFeedFragment.newInstance(), true);
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.w(LOG_TAG, "Error fetching news feed data on startup: " + t);
                }
            }, MoreExecutors.directExecutor());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_newsfeed:
                    startFragment(NewsFeedFragment.newInstance(), false);
                    break;
                case R.id.nav_savedarticles:
                    startFragment(SavedArticlesFragment.newInstance(), false);
                    break;
            }
            return true;
        });
    }

    private void startFragment(Fragment fragment, boolean isDefaultFragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        if (isDefaultFragment) {
            transaction.commitAllowingStateLoss();
        } else {
            transaction.commit();
        }
    }
}