package edu.uw.cstanf.nbcsample.feed.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Provides methods for refreshing and querying data displayed in a news feed.
 */
public final class NewsFeedDataService {
    private static final String LOG_TAG = "NewsFeedDataService";
    private static final String HERO_TYPE = "Hero";
    private static final String SECTION_TYPE = "Section";
    private static final String VIDEO_TYPE = "Videos";
    private static NewsFeedDataService instance;

    private final ListeningExecutorService executor;
    private List<NewsFeedGroup> feedGroups;

    private NewsFeedDataService() {
        this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.feedGroups = new ArrayList<>();
    }

    public static NewsFeedDataService getInstance() {
        if (instance == null) {
            synchronized (NewsFeedDataService.class) {
                if (instance == null) {
                    instance = new NewsFeedDataService();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the current {@link NewsFeedGroup} data.
     */
    public List<NewsFeedGroup> getNewsFeedGroups() {
        return this.feedGroups;
    }

    /**
     * Fetches then parses news feed data, returning true when successful and false otherwise.
     *
     * @param sourceUrl a non-null String url where the data is hosted
     */
    public ListenableFuture<Boolean> updateData(@NonNull String sourceUrl) {
        return executor.submit(
                () -> {
                    JSONObject response = getJsonFromURL(sourceUrl);
                    if (response != null) {
                        return parseNewsFeedData(response);
                    }
                    return false;
                }
        );
    }

    private boolean parseNewsFeedData(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject group = data.getJSONObject(i);

                switch (group.getString("type")) {
                    case HERO_TYPE:
                        parseHero(group);
                        break;
                    case SECTION_TYPE:
                        parseSection(group);
                        break;
                    case VIDEO_TYPE:
                        parseVideos(group);
                        break;
                    default:
                        break;
                }
            }

            Log.i(LOG_TAG, "feed groups: " + feedGroups.toString());
            Log.i(LOG_TAG, "feed groups len: " + feedGroups.size());
            return true;
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing news feed data: " + e);
            return false;
        }
    }

    private void parseHero(JSONObject hero) {
        try {
            JSONObject heroItem = hero.getJSONObject("item");
            NewsFeedItem item = new NewsFeedItem(heroItem.getString("headline"), heroItem.getString("tease"));
            NewsFeedGroup heroGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.HERO, "Hero Placeholder");
            heroGroup.addItem(item);

            feedGroups.add(heroGroup);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing hero data: " + e);
        }
    }

    private void parseSection(JSONObject section) {
        try {
            JSONArray articles = section.getJSONArray("items");
            // String sectionHeader = section.getString("header");
            NewsFeedGroup sectionGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.SECTION, "Section Placeholder");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(article.getString("headline"), article.getString("tease"));
                sectionGroup.addItem(item);
            }

            feedGroups.add(sectionGroup);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing section data: " + e);
        }
    }

    private void parseVideos(JSONObject videos) {
        try {
            JSONArray videoItems = videos.getJSONArray("videos");
            String header = videos.getString("header");
            NewsFeedGroup videoGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.VIDEOS, header);

            for (int i = 0; i < videoItems.length(); i++) {
                JSONObject video = videoItems.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(video.getString("headline"), video.getString("tease"));
                videoGroup.addItem(item);
            }

            feedGroups.add(videoGroup);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing video data: " + e);
        }
    }

    private JSONObject getJsonFromURL(String sourceUrl) {
        try {
            // Try to connect to the URL.
            URL url = new URL(sourceUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Read the output from the connection.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return new JSONObject(stringBuilder.toString());
        } catch (Exception e) {
            Log.w(LOG_TAG, "Unable to fetch/read data from URL: " + e);
            return null;
        }
    }
}
