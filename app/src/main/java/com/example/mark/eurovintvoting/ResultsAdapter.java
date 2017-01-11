package com.example.mark.eurovintvoting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mark.eurovintvoting.Entities.Participant;
import com.example.mark.eurovintvoting.Entities.RegistrationResult;

import java.util.List;


public class ResultsAdapter extends ArrayAdapter<RegistrationResult> {
    public ResultsAdapter(Context context, int resource, List<RegistrationResult> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RegistrationResult result = getItem(position);
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.result_item, parent,false);
        }

        TextView place = (TextView) convertView.findViewById(R.id.place);
        TextView votecount = (TextView) convertView.findViewById(R.id.votecount);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView country = (TextView) convertView.findViewById(R.id.country);
        TextView artist = (TextView) convertView.findViewById(R.id.artist);

        place.setText(String.valueOf(position+1));
        votecount.setText(result != null ? String.valueOf(result.getVotecount()): "--");
        assert result != null;
        Participant p = result.getParticipant();
        title.setText(p.getTitle());
        country.setText(p.getCountry());
        artist.setText(p.getArtist());
        // Return the completed view to render on screen
        return convertView;
    }
}
