package com.speakingcode.freemusicarchive.android.albums;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.freemusicarchive.api.Album;
import com.freemusicarchive.api.AlbumRecordSet;
import com.freemusicarchive.api.FMAConnector;
import com.speakingcode.freemusicarchive.android.R;

public class AlbumsViewerFragment extends Fragment
{
	protected String TAG = "AlbumsViewerFragment";
	
	protected ListView albumsListView;
	protected AlbumArrayAdapter albumsArrayAdapter;
	
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
		return inflater.inflate(R.layout.fragment_albums_viewer, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		albumsListView = (ListView) view.findViewById(R.id.albumsListView);
		
		albumsListView.setEmptyView(getActivity().findViewById(android.R.id.empty));
		albumsListView.setAdapter(albumsArrayAdapter);
		
		albumsListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				Log.i(TAG, "album clicked at position: " + position);
				if (getActivity() instanceof IAlbumsViewerClickHandler)
				{
					Album clickedAlbum = albumsArrayAdapter.getItem(position);
					((IAlbumsViewerClickHandler)getActivity()).onAlbumItemClicked(clickedAlbum);
				}
				
			}
		});
		
	}
	
	public void initializePullData(final String genreHandle)
	{
		//this.genreHandle = genreHandle;
		new Thread()
		{
			public void run()
			{

				//TODO implement controller and make this async
				AlbumRecordSet ars = FMAConnector.getlAllAlbumsWithGenreHandle(genreHandle);
				albumsArrayAdapter = new AlbumArrayAdapter(AlbumsViewerFragment.this.getActivity(), R.layout.list_item_album, ars.getAlbumRecords() );
				AlbumsViewerFragment.this.getActivity().runOnUiThread(new Runnable()
				{
					public void run()
					{
						albumsListView.setAdapter(albumsArrayAdapter);
					}
				});
			}
		}.start();
	}
}
