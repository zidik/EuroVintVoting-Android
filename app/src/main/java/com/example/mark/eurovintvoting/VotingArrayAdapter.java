package com.example.mark.eurovintvoting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mark.eurovintvoting.Entities.Voting;

import java.util.ArrayList;

public class VotingArrayAdapter extends RecyclerView.Adapter<VotingArrayAdapter.ViewHolder> {

    private int votingLayout;
    private ArrayList<Voting> votings;

    public VotingArrayAdapter(int layoutId, ArrayList<Voting> votings) {
        votingLayout = layoutId;
        this.votings = votings;
    }

    // get the size of the list
    @Override
    public int getItemCount() {
        return votings == null ? 0 : votings.size();
    }


    // specify the row layout file and click for each row
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(votingLayout, parent, false);
        return new ViewHolder(view);
    }

    // load data in each row element
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        Voting voting = votings.get(listPosition);
        holder.id = voting.getId();
        holder.name.setText(voting.getName());
        holder.running.setText("Status: " + (voting.isRunning()?"Running":"Stopped"));
        holder.cover_photo.setImageResource(R.drawable.event_cover);
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int id;
        public TextView name;
        public TextView running;
        public ImageView cover_photo;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            name = (TextView) view.findViewById(R.id.name);
            running = (TextView) view.findViewById(R.id.running);
            cover_photo = (ImageView) view.findViewById(R.id.cover_photo);
        }
        @Override
        public void onClick(View view) {
            openResults(view.getContext());
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + name.getText());
        }

        private void openResults(Context context){
            Intent intent = new Intent(context, ResultsActivity.class);
            intent.putExtra(MainActivity.EXTRA_VOTING_ID, id);
            context.startActivity(intent);
        }
    }
}
