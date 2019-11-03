package edu.uw.cstanf.nbcsample.feed.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import edu.uw.cstanf.nbcsample.feed.NewsFeedActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/** */
public class NewsFeedDataService {
    private static NewsFeedDataService instance;
    private Context context;
    private RequestQueue requestQueue;
    private List<NewsFeedGroup> feedGroups;

    private NewsFeedDataService(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
        this.feedGroups = new ArrayList<>();
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

    public void fetchData(String sourceUrl) {
        Log.i("CHRISTINA", "bouta fetch");
        String url = sourceUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null) {
                            parseNewsFeedData(response);
                            Log.i("CHRISTINA", "feed groups: " + feedGroups.toString());
                            Log.i("CHRISTINA", "feed groups len: " + feedGroups.size());
                            Intent intent  = new Intent(context, NewsFeedActivity.class);
                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        } else {
                            Log.e("CHRISTINA", "response was null");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("CHRISTINA", "error response " + error.getMessage());
                    }
                });

        // Runs on background thread.
        this.requestQueue.add(jsonObjectRequest);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    private void parseNewsFeedData(JSONObject response) {
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
        } catch (JSONException e) {
            Log.e("CHRISTINA", e.getMessage());
        }
    }

    private void parseHero(JSONObject hero) {
        Log.i("CHRISTINA", "parseHero");
        try {
            JSONObject heroItem = hero.getJSONObject("item");
            NewsFeedItem item = new NewsFeedItem(heroItem.getString("headline"), heroItem.getString("tease"));
            NewsFeedGroup heroGroup = new NewsFeedGroup(NewsFeedGroup.GroupType.HERO, "hero placeholder");
            heroGroup.addItem(item);

            feedGroups.add(heroGroup);
        } catch (JSONException e) {
            Log.e("CHRISTINA", "hero " + e.getMessage());
        }
    }

    private void parseSection(JSONObject section) {
        Log.i("CHRISTINA", "parseSection");
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
            Log.e("CHRISTINA", "section " + e.getMessage());
        }
    }

    private void parseVideos(JSONObject videos) {
        Log.i("CHRISTINA", "parseVideos");

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
            Log.e("CHRISTINA", "videos " + e.getMessage());
        }
    }

}
