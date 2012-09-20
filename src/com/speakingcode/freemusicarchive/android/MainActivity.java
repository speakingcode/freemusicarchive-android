package com.speakingcode.freemusicarchive.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.freemusicarchive.api.Album;
import com.freemusicarchive.api.Genre;
import com.speakingcode.freemusicarchive.android.albums.AlbumsViewerActivity;
import com.speakingcode.freemusicarchive.android.albums.AlbumsViewerFragment;
import com.speakingcode.freemusicarchive.android.albums.IAlbumsViewerClickHandler;
import com.speakingcode.freemusicarchive.android.genres.IGenresViewerClickHandler;
import com.speakingcode.freemusicarchive.android.tracks.TracksViewerActivity;
import com.speakingcode.freemusicarchive.android.tracks.TracksViewerFragment;

public class MainActivity extends FragmentActivity
implements	IGenresViewerClickHandler,
			IAlbumsViewerClickHandler
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

    
}
