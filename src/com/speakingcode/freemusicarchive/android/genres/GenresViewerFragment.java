package com.speakingcode.freemusicarchive.android.genres;

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
import com.freemusicarchive.api.Genre;
import com.freemusicarchive.api.GenreRecordSet;
import com.speakingcode.freemusicarchive.android.R;

public class GenresViewerFragment extends Fragment
{
	protected String TAG = "GenresViewerFragment";
	protected ListView genresListView;
	protected GenreArrayAdapter genresArrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		initializePullData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_genres_viewer, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		genresListView = (ListView) view.findViewById(R.id.genresListView);
		genresListView.setAdapter(genresArrayAdapter);
		genresListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id)
			{
				Log.i(TAG, "genre clicked at position: " + position);
				if (getActivity() instanceof IGenresViewerClickHandler)
				{
					Genre clickedGenre = genresArrayAdapter.getItem(position);
					((IGenresViewerClickHandler)getActivity()).onGenreItemClicked(clickedGenre);
				}
				
			}
		});
		
	}
	
	public void initializePullData()
	{
		new Thread()
		{
			public void run()
			{
				//TODO implement controller and make this async
				GenreRecordSet grs = FMAConnector.getAllGenres();
				genresArrayAdapter = new GenreArrayAdapter(GenresViewerFragment.this.getActivity(), R.layout.list_item_genre, grs.getGenreRecords() );
				GenresViewerFragment.this.getActivity().runOnUiThread(new Runnable()
				{
					public void run()
					{
					genresListView.setAdapter(genresArrayAdapter);
					}
				});

			}
		}.start();
		
	}
	

}
