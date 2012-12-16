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

package com.speakingcode.freemusicarchive.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.speakingcode.freemusicarchive.android.albums.AlbumsViewerActivity;
import com.speakingcode.freemusicarchive.android.albums.AlbumsViewerFragment;
import com.speakingcode.freemusicarchive.android.albums.IAlbumsViewerClickHandler;
import com.speakingcode.freemusicarchive.android.genres.IGenresViewerClickHandler;
import com.speakingcode.freemusicarchive.android.tracks.ITracksViewerClickHandler;
import com.speakingcode.freemusicarchive.android.tracks.TracksViewerActivity;
import com.speakingcode.freemusicarchive.android.tracks.TracksViewerFragment;
import com.speakingcode.freemusicarchive.api.Album;
import com.speakingcode.freemusicarchive.api.Genre;
import com.speakingcode.freemusicarchive.api.Track;
import com.speakingcode.freemusicarchive.api.TrackDownloadController;

public class MainActivity extends FragmentActivity
implements	IGenresViewerClickHandler,
			IAlbumsViewerClickHandler,
			ITracksViewerClickHandler
			
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onGenreItemClicked(Genre clickedGenre)
	{
		AlbumsViewerFragment albumsFragment = (AlbumsViewerFragment)
				getSupportFragmentManager().findFragmentById(R.id.albumsFragment);
		
		if (albumsFragment == null)
		{
			//albums fragment is null, meaning not on screen (handset/small screen)
			Intent intent = new Intent(this, AlbumsViewerActivity.class);
			intent.putExtra("genreHandle", clickedGenre.getGenreHandle());
			startActivity(intent);
		}
		else
		{
			albumsFragment.initializePullData(clickedGenre.getGenreHandle());
		}
	}

	@Override
	public void onAlbumItemClicked(Album clickedAlbum)
	{
		TracksViewerFragment tracksFragment = (TracksViewerFragment)
				getSupportFragmentManager().findFragmentById(R.id.tracksFragment);
		
		if (tracksFragment == null)
		{
			//albums fragment is null, meaning not on screen (handset/small screen)
			Intent intent = new Intent(this, TracksViewerActivity.class);
			intent.putExtra("albumHandle", clickedAlbum.getAlbumHandle());
			startActivity(intent);
		}
		else
		{
			tracksFragment.initializePullData(clickedAlbum.getAlbumHandle());
		}
		
	}

	@Override
	public void onTrackItemClicked(Track clickedTrack)
	{
		//String uri = TrackDownloadController.getDownloadUrl(clickedTrack.getTrackUrl());
		String uri = clickedTrack.getTrackUrl() + "/download";
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		startActivity(intent);
		
	}

    
}
