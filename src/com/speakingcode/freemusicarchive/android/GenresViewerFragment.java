package com.speakingcode.freemusicarchive.android;

import com.freemusicarchive.api.GenreRecordSet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class GenresViewerFragment extends Fragment
{
	protected ListView genresListView;
	protected GenreArrayAdapter genresArrayAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		FMAConnector fmac = new FMAConnector();
		GenreRecordSet grs = fmac.getAllGenres();
		genresArrayAdapter = new GenreArrayAdapter(this.getActivity(), R.layout.list_item_genre, grs.getGenreRecords() );
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
		
	}
	

}
