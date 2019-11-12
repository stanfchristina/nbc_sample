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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Provides methods for refreshing and querying data displayed in a news feed.
 */
public final class NewsFeedDataService {
    private static final String LOG_TAG = "NewsFeedDataService";

    // Keys for parsing the JSON, assuming it is in the standard format.
    private static final String RESPONSE_DATA_KEY = "data";
    private static final String RESPONSE_TYPE_KEY = "type";
    private static final String PROMO_TYPE = "Promo";
    private static final String SECTION_TYPE = "Section";
    private static final String VIDEO_TYPE = "Videos";

    private static final String PROMO_KEY = "items";
    private static final String SECTION_KEY = "items";
    private static final String VIDEOS_KEY = "videos";
    private static final String GROUP_HEADER_KEY = "header";
    private static final String SECTION_VALID_KEY = "showMore";
    private static final String ITEM_HEADLINE_KEY = "headline";
    private static final String ITEM_THUMBNAIL_KEY = "tease";

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
        Collections.sort(feedGroups);
        return new ArrayList<>(feedGroups);
    }

    /**
     * Fetches then parses news feed data, returning true when successful and false otherwise.
     *
     * <p>Assumes the JSON at the sourceUrl is of a valid standard format, with the expected
     * nested structure, accessor keys, and non-null values stored in those keys. Will likely
     * throw an {@link JSONException} error if not adhering to this format.
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
            JSONArray data = response.getJSONArray(RESPONSE_DATA_KEY);
            for (int i = 0; i < data.length(); i++) {
                JSONObject group = data.getJSONObject(i);

                switch (group.getString(RESPONSE_TYPE_KEY)) {
                    case PROMO_TYPE:
                        parsePromo(group);
                        break;
                    case SECTION_TYPE:
                        parseSection(group);
                        break;
                    case VIDEO_TYPE:
                        parseVideos(group);
                        break;
                    default:
                        // Unsupported type for news feed.
                        break;
                }
            }

            return true;
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing news feed data: " + e);
            return false;
        }
    }

    private void parsePromo(JSONObject promo) {
        try {
            JSONArray promoItems = promo.getJSONArray(PROMO_KEY);
            String promoHeader = promo.getString(GROUP_HEADER_KEY);

            List<NewsFeedItem> promoGroup = new ArrayList<>();
            for (int i = 0; i < promoItems.length(); i++) {
                JSONObject promoItem = promoItems.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(promoItem.getString(ITEM_HEADLINE_KEY), promoItem.getString(ITEM_THUMBNAIL_KEY));
                promoGroup.add(item);
            }

            feedGroups.add(new NewsFeedGroup(promoGroup, NewsFeedGroup.GroupType.PROMO, promoHeader));
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing promo data: " + e);
        }
    }

    private void parseSection(JSONObject section) {
        try {
            if (!section.getBoolean(SECTION_VALID_KEY)) {
                // Don't parse a section that should not be displayed.
                return;
            }
            JSONArray sectionItems = section.getJSONArray(SECTION_KEY);
            String sectionHeader = section.getString(GROUP_HEADER_KEY);

            List<NewsFeedItem> sectionGroup = new ArrayList<>();
            for (int i = 0; i < sectionItems.length(); i++) {
                JSONObject article = sectionItems.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(article.getString(ITEM_HEADLINE_KEY), article.getString(ITEM_THUMBNAIL_KEY));
                sectionGroup.add(item);
            }

            feedGroups.add(new NewsFeedGroup(sectionGroup, NewsFeedGroup.GroupType.SECTION, sectionHeader));
        } catch (JSONException e) {
            Log.w(LOG_TAG, "Error parsing section data: " + e);
        }
    }

    private void parseVideos(JSONObject videos) {
        try {
            JSONArray videoItems = videos.getJSONArray(VIDEOS_KEY);
            String videoHeader = videos.getString(GROUP_HEADER_KEY);

            List<NewsFeedItem> videoGroup = new ArrayList<>();
            for (int i = 0; i < videoItems.length(); i++) {
                JSONObject video = videoItems.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(video.getString(ITEM_HEADLINE_KEY), video.getString(ITEM_THUMBNAIL_KEY));
                videoGroup.add(item);
            }

            feedGroups.add(new NewsFeedGroup(videoGroup, NewsFeedGroup.GroupType.VIDEOS, videoHeader));
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
