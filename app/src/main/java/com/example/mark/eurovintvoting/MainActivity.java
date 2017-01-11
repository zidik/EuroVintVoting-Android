package com.example.mark.eurovintvoting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mark.eurovintvoting.Entities.Voting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RequestQueue requestQueue;
    VotingArrayAdapter votingArrayAdapter;

    public final static String EXTRA_VOTING_ID = "com.example.eurovintvoting.VOTING_ID";

    // Initializing list view with the custom adapter
    ArrayList<Voting> votings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        votingArrayAdapter = new VotingArrayAdapter(R.layout.voting_item, votings);
        recyclerView = (RecyclerView) findViewById(R.id.voting_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(votingArrayAdapter);

        // Populating list items
        for(int i=0; i<10; i++) {
            votings.add(new Voting(0, "Loading... "+i, false));
        }

        // Instantiate the RequestQueue.
        requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestVotings();
    }

    public void openResults(View view){
        Intent intent = new Intent(this, ResultsActivity.class);
        startActivity(intent);
    }

    private void requestVotings(){
        String url ="https://eurovint-voting.herokuapp.com/votings.json";
        JsonArrayRequest jsObjRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        votings.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject item = response.getJSONObject(i);
                                Integer id = item.getInt("id");
                                String name = item.getString("name");
                                boolean running = item.getBoolean("running");
                                Voting voting = new Voting(id, name, running);
                                votings.add(voting);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for(int i=0; i<10; i++) {
                            votings.add(new Voting(0, "TestVoting in Rome "+i, false));
                        }

                        votingArrayAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Err:", error.toString());
                    }
                }
        );
        // Add the request to the RequestQueue.
        requestQueue.add(jsObjRequest);
    }
}
