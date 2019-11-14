package edu.uw.cstanf.nbcsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import edu.uw.cstanf.nbcsample.savedarticles.SavedArticlesViewModel;
import edu.uw.cstanf.nbcsample.savedarticles.data.SavedArticle;
import edu.uw.cstanf.nbcsample.ui.NewsItemClickListener;

public class MainActivity extends AppCompatActivity implements NewsItemClickListener {
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
                    startFragment(new SavedArticlesFragment(this), false);
                    break;
            }
            return true;
        });
    }

    @Override
    public void onItemClicked(String articleLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleLink));
        if (intent.resolveActivity(getPackageManager()) != null) {
            Log.i(LOG_TAG, "Opening link in web browser: " + articleLink);
            startActivity(intent);
        }
    }

    @Override
    public void onRemoveButtonClicked(SavedArticle article) {
        SavedArticlesViewModel viewModel = new SavedArticlesViewModel(this.getApplication());
        Futures.addCallback(viewModel.deleteArticle(article), new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@NullableDecl Integer result) {
                if (result != null && result != -1) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Removed article.", Toast.LENGTH_SHORT).show());
                }
            }
            @Override
            public void onFailure(Throwable t) {
                Log.w(LOG_TAG, "Error attempting to remove saved article: " + t);
            }
        }, MoreExecutors.directExecutor());
    }

    @Override
    public void onSaveButtonClicked() {
        SavedArticlesViewModel savedArticlesViewModel = new SavedArticlesViewModel(this.getApplication());
        Toast.makeText(this, "Saved article.", Toast.LENGTH_SHORT).show();
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