package com.speakingcode.freemusicarchive.android.albums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.freemusicarchive.api.Album;
import com.speakingcode.freemusicarchive.android.R;
import com.speakingcode.freemusicarchive.android.tracks.TracksViewerActivity;

public class AlbumsViewerActivity extends FragmentActivity
implements	IAlbumsViewerClickHandler
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums_viewer);
        
        AlbumsViewerFragment albumsFragment = (AlbumsViewerFragment)
				getSupportFragmentManager().findFragmentById(R.id.albumsFragment);
        
        String genreHandle = (String)getIntent().getExtras().get("genreHandle");
        albumsFragment.initializePullData(genreHandle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onAlbumItemClicked(Album clickedAlbum)
	{
		Intent intent = new Intent(this, TracksViewerActivity.class);
		intent.putExtra("albumHandle", clickedAlbum.getAlbumHandle());
		startActivity(intent);
	}

    
}
