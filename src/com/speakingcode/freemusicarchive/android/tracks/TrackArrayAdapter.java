/*
 *  Free Music Archive - Download Free Music legally from the FreeMusicArchive
 *  Copyright (C) 2012  Daniel Lissner (http://speakingcode.com)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.speakingcode.freemusicarchive.android.tracks;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.speakingcode.freemusicarchive.android.R;
import com.speakingcode.freemusicarchive.api.Track;

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
			title.setText(tracks.get(position).getTrackTitle() + " - " + tracks.get(position).getTrackDuration());
		
		return listItemView;
	}
}
