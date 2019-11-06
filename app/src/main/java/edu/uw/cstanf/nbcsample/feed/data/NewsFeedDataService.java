package edu.uw.cstanf.nbcsample.feed.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.common.util.concurrent.Futures;
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

/** Provides rea*/
public class NewsFeedDataService {
    private static final String LOG_TAG = "NewsFeedDataService";
    private static NewsFeedDataService instance;

    private Context context;
    private ListeningExecutorService executor;
    private List<NewsFeedGroup> feedGroups;
    private RequestQueue requestQueue;

    private NewsFeedDataService(Context context) {
        this.context = context;
        this.executor = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        this.feedGroups = new ArrayList<>();
        this.requestQueue = getRequestQueue();
    }

    public static synchronized NewsFeedDataService getInstance(Context context) {
        if (instance == null) {
            instance = new NewsFeedDataService(context);
        }
        return instance;
    }

    public List<NewsFeedGroup> getNewsFeedGroups() {
        return this.feedGroups;
    }

    public ListenableFuture<Boolean> attemptFetch(String url) {
        return executor.submit(
                () -> {
                    String response = getJsonFromURL(url);
                    if (response != null) {
                        return parseNewsFeedData(new JSONObject(response));
                    }
                    return false;
                    /*RequestFuture<JSONObject> future = RequestFuture.newFuture();
                    JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(), future, future);
                    requestQueue.add(request);


                    try {
                        JSONObject response = future.get();
                        if (response != null) {
                            return parseNewsFeedData(response);
                        }
                        Log.w(LOG_TAG, "response was null");
                        return false;
                    } catch (Exception e) {
                        Log.w(LOG_TAG, "bad fetch" + e);
                        return false;
                    }*/
                }
        );
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private boolean parseNewsFeedData(JSONObject response) {
        try {
            JSONArray data = response.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject group = data.getJSONObject(i);

                switch(group.getString("type")) {
                    case "Hero":
                        parseHero(group);
                        break;
                    case "Section":
                        parseSection(group);
                        break;
                    case "Videos":
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
            Log.e(LOG_TAG, e.getMessage());
            return false;
        }
    }

    private void parseHero(JSONObject hero) {
        Log.i(LOG_TAG, "parseHero");
        try {
            JSONObject heroItem = hero.getJSONObject("item");
            NewsFeedItem item = new NewsFeedItem(heroItem.getString("headline"), heroItem.getString("tease"));
            NewsFeedGroup heroGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.HERO, "hero placeholder");
            heroGroup.addItem(item);

            feedGroups.add(heroGroup);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "hero " + e.getMessage());
        }
    }

    private void parseSection(JSONObject section) {
        Log.i(LOG_TAG, "parseSection");
        try {
            JSONArray articles = section.getJSONArray("items");
            // String sectionHeader = section.getString("header");
            NewsFeedGroup sectionGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.SECTION, "article placeholder");
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                NewsFeedItem item = new NewsFeedItem(article.getString("headline"), article.getString("tease"));
                sectionGroup.addItem(item);
            }

            feedGroups.add(sectionGroup);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "section " + e.getMessage());
        }
    }

    private void parseVideos(JSONObject videos) {
        Log.i(LOG_TAG, "parseVideos");

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
            Log.e(LOG_TAG, "videos " + e.getMessage());
        }
    }

    public String getJsonFromURL(String wantedUrl) {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            // create the HttpURLConnection
            url = new URL(wantedUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // just want to do an HTTP GET here
            connection.setRequestMethod("GET");

            // give it 15 seconds to respond
            //connection.setReadTimeout(2 * 1000);
            connection.connect();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            Log.w(LOG_TAG, "getjsonfromurl " + e);
            return null;
        }
    }
}
