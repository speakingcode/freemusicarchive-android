package com.speakingcode.freemusicarchive.android.albums;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freemusicarchive.api.Album;
import com.speakingcode.freemusicarchive.android.R;

public class AlbumArrayAdapter extends ArrayAdapter<Album>
{

    protected Context context;
    protected int layoutResourceId;
    protected List<Album> albums;

	public AlbumArrayAdapter(Context context,
			int layoutResourceId, List<Album> albums)
	{
		super(context, layoutResourceId, albums);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.albums = albums;
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
		
		TextView title = ((TextView) listItemView.findViewById(R.id.albumTitle));
		if (title != null)
			title.setText(albums.get(position).getAlbumTitle());
		
		TextView artist = ((TextView) listItemView.findViewById(R.id.albumArtist));
		if (artist != null)
			artist.setText(albums.get(position).getArtistName());
		
		TextView tracks = ((TextView) listItemView.findViewById(R.id.albumTracks));
		if(tracks != null)
			tracks.setText(albums.get(position).getAlbumTracks());
		
		return listItemView;
	}
}
