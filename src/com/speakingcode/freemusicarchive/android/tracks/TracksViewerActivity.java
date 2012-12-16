package com.speakingcode.freemusicarchive.android.tracks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.speakingcode.freemusicarchive.android.R;
import com.speakingcode.freemusicarchive.api.Album;
import com.speakingcode.freemusicarchive.api.Track;
import com.speakingcode.freemusicarchive.api.TrackDownloadController;

public class TracksViewerActivity extends FragmentActivity
implements	ITracksViewerClickHandler
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
	public void onTrackItemClicked(Track clickedTrack)
	{
		//String uri = TrackDownloadController.getDownloadUrl(clickedTrack.getTrackUrl());
		String uri = clickedTrack.getTrackUrl() + "/download";
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
		startActivity(intent);
	}

    
}
