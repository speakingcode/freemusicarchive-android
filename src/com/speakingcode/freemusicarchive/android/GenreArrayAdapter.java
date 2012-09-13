package com.speakingcode.freemusicarchive.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.freemusicarchive.api.Genre;

public class GenreArrayAdapter extends ArrayAdapter<Genre>
{
    protected Context context;
    protected int layoutResourceId;
    protected List<Genre> genres;

	public GenreArrayAdapter(Context context,
			int layoutResourceId, List<Genre> genres)
	{
		super(context, layoutResourceId, genres);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.genres = genres;
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
		
		TextView title = ((TextView) listItemView.findViewById(R.id.genreTitle));
		if (title != null)
			title.setText(genres.get(position).getGenreTitle());
		
		return listItemView;
	}
}