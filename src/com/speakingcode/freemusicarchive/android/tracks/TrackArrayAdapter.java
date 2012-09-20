package com.speakingcode.freemusicarchive.android.tracks;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freemusicarchive.api.Track;
import com.speakingcode.freemusicarchive.android.R;

public class TrackArrayAdapter extends ArrayAdapter<Track>
{

    protected Context context;
    protected int layoutResourceId;
    protected List<Track> tracks;

	public TrackArrayAdapter(Context context,
			int layoutResourceId, List<Track> tracks)
	{
		super(context, layoutResourceId, tracks);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.tracks = tracks;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View listItemView = convertView;
		if (listItemView == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			listItemView = inflater.inflate(layoutResourceId, parent, false);
		}
		
		TextView title = ((TextView) listItemView.findViewById(R.id.trackTitle));
		if (title != null)
			title.setText(tracks.get(position).getTrackTitle());
		
		TextView artist = ((TextView) listItemView.findViewById(R.id.trackArtist));
		if (artist != null)
			artist.setText(tracks.get(position).getArtistName());
		
		TextView duration = ((TextView) listItemView.findViewById(R.id.trackDuration));
		if(tracks != null)
			duration.setText(tracks.get(position).getTrackDuration());
		
		return listItemView;
	}
}