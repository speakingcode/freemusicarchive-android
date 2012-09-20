package com.speakingcode.freemusicarchive.android.tracks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.freemusicarchive.api.Album;
import com.speakingcode.freemusicarchive.android.R;
import com.speakingcode.freemusicarchive.android.albums.IAlbumsViewerClickHandler;

public class TracksViewerActivity extends FragmentActivity
implements	IAlbumsViewerClickHandler
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks_viewer);
        
        TracksViewerFragment tracksFragment = (TracksViewerFragment)
				getSupportFragmentManager().findFragmentById(R.id.tracksFragment);
        
        String albumHandle = (String)getIntent().getExtras().get("albumHandle");
        tracksFragment.initializePullData(albumHandle);
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
