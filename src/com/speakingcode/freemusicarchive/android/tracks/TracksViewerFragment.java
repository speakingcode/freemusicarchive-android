package com.speakingcode.freemusicarchive.android.tracks;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.freemusicarchive.api.FMAConnector;
import com.freemusicarchive.api.Track;
import com.freemusicarchive.api.TrackRecordSet;
import com.speakingcode.freemusicarchive.android.R;

public class TracksViewerFragment extends Fragment
{

	protected String TAG = "TracksViewerFragment";
	protected ListView tracksListView;
	protected TrackArrayAdapter tracksArrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_tracks_viewer, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		tracksListView = (ListView) view.findViewById(R.id.tracksListView);
		tracksListView.setAdapter(tracksArrayAdapter);
		tracksListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				Log.i(TAG, "track clicked at position: " + position);
				if (getActivity() instanceof ITracksViewerClickHandler)
				{
					Track clickedTrack = tracksArrayAdapter.getItem(position);
					((ITracksViewerClickHandler)getActivity()).onTrackItemClicked(clickedTrack);
				}
			}
		});
		
	}
	
	public void initializePullData(final String albumHandle)
	{
		new Thread()
		{
			public void run()
			{
				//TODO implement controller and make this async
				TrackRecordSet trs = FMAConnector.getlAllTracksWithAlbumHandle(albumHandle);
				tracksArrayAdapter = new TrackArrayAdapter(TracksViewerFragment.this.getActivity(), R.layout.list_item_track, trs.getTrackRecords() );
				TracksViewerFragment.this.getActivity().runOnUiThread(new Runnable()
				{
					public void run()
					{
					tracksListView.setAdapter(tracksArrayAdapter);
					}
				});

			}
		}.start();
		
	}
	
}
