package edu.uw.cstanf.nbcsample.feed.data;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.concurrent.Future;

/** */
public class NewsFeedDataService {
    private static NewsFeedDataService instance;
    private Context context;
    private RequestQueue requestQueue;

    private NewsFeedDataService(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static synchronized NewsFeedDataService getInstance(Context context) {
        if (instance == null) {
            instance = new NewsFeedDataService(context);
        }
        return instance;
    }

    public Future<Boolean> fetchData(String sourceUrl) {
        String url = sourceUrl;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    // UI thread
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("CHRISTINA", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        // Runs on background thread.
        this.requestQueue.add(jsonObjectRequest);
        return null;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
