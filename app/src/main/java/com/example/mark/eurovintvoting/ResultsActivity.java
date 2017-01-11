package com.example.mark.eurovintvoting;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.mark.eurovintvoting.Entities.Participant;
import com.example.mark.eurovintvoting.Entities.RegistrationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class ResultsActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    // Initializing list view with the custom adapter
    ArrayList<RegistrationResult> results = new ArrayList<>();
    ArrayAdapter<RegistrationResult> adapter;
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        requestQueue = Volley.newRequestQueue(this);
        adapter = new ResultsAdapter(this,R.layout.result_item, results);
        ListView listView = (ListView) findViewById(R.id.result_list);
        listView.setAdapter(adapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        final int id = getIntent().getIntExtra(MainActivity.EXTRA_VOTING_ID, 0);

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchResults(id);
            }
        });
        fetchResults(id);
    }

    private void fetchResults(int id){
        if (id <= 0){
            results.clear();
            results.add(new RegistrationResult(new Participant(0,"Test Artist","Test Title","TestCountry"), 123));
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);
            return;
        }
        Log.d("FETCH", String.valueOf(id));
        GsonRequest<RegistrationResult[]> request = new GsonRequest<>(
                "https://eurovint-voting.herokuapp.com/votings/"+id+"/results.json",
                RegistrationResult[].class,
                null,
                onSuccess,
                onError);
        // Add the request to the RequestQueue.
        requestQueue.add(request);
    }

    private final Response.Listener<RegistrationResult[]> onSuccess = new Response.Listener<RegistrationResult[]>() {
        @Override
        public void onResponse(RegistrationResult[] response) {
            Log.d("onSuccess", response[0].toString());
            Arrays.sort(response, new Comparator<RegistrationResult> () {
                @Override
                public int compare(RegistrationResult r1, RegistrationResult r2) {
                    return r2.getVotecount().compareTo(r1.getVotecount());
                }
            });
            results.clear();
            results.addAll(Arrays.asList(response));
            adapter.notifyDataSetChanged();
            swipeContainer.setRefreshing(false);

        }
    };
    private final Response.ErrorListener onError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError e) {
            Log.e("Volley error", e.toString());
            swipeContainer.setRefreshing(false);
        }
    };
}
