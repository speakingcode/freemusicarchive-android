package com.speakingcode.freemusicarchive.android.tracks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.speakingcode.freemusicarchive.android.R;
import com.speakingcode.freemusicarchive.api.ITrackControllerObserver;
import com.speakingcode.freemusicarchive.api.Track;
import com.speakingcode.freemusicarchive.api.TrackController;
import com.speakingcode.freemusicarchive.api.TrackRecordSet;

public class TracksViewerFragment extends Fragment
	implements ITrackControllerObserver
{

	protected String TAG = "TracksViewerFragment";
	protected ListView tracksListView;
	protected TrackArrayAdapter tracksArrayAdapter;
	
	ProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mProgressDialog = new ProgressDialog(this.getActivity());
		mProgressDialog.setMessage("Downloading...");
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setMax(100);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
				Log.d(TAG, "track clicked at position: " + position);
				Track clickedTrack = tracksArrayAdapter.getItem(position);
				//((ITracksViewerClickHandler)getActivity()).onTrackItemClicked(clickedTrack);
				DownloadFile df = new DownloadFile();
				df.setTrack(clickedTrack);
				df.execute(clickedTrack.getTrackUrl() + "/download");
	
			}
		});

	}
	
	public void initializePullData(final String albumHandle)
	{
		TrackController tc = TrackController.getInstance();
		tc.addObserver(this);
		tc.getAllTracksWithAlbumHandle(albumHandle);
	}

	@Override
	public void onGetTrackRecordSetSuccess(final TrackRecordSet trs)
	{
		TracksViewerFragment.this.getActivity().runOnUiThread(new Runnable()
		{
			public void run()
			{
				tracksArrayAdapter = new TrackArrayAdapter
				(
					TracksViewerFragment.this.getActivity(),
					R.layout.list_item_track,
					trs.getTrackRecords()
				);
				
				tracksListView.setAdapter(tracksArrayAdapter);
			}
		});
	}
	

	// usually, subclasses of AsyncTask are declared inside the activity class.
	// that way, you can easily modify the UI thread from here
	private class DownloadFile extends AsyncTask<String, Integer, String>
	{
		Track mTrack;
		
		public void setTrack(Track t)
		{
			mTrack = t;
		}
		
		@Override
		protected String doInBackground(String... sUrl)
		{
			try
			{
				
				//setup directory for download (sdcard/FMADownloads/Artist/Album/)
				File downloadPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
						+ "/FMADownloads/" + mTrack.getArtistName() + "/" + mTrack.getAlbumTitle());
				if (!downloadPath.exists())
					downloadPath.mkdirs();
				
				File outputFile = new File (downloadPath.getAbsolutePath()
						+ "/" + mTrack.getArtistName() +  " - " + mTrack.getTrackTitle() + ".mp3");
				
				if(!outputFile.exists())
				{
			        	
					//setup connection
					URL url = new URL(sUrl[0]);
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					connection.connect();
					// used to show 0-100% progress bar
					int fileLength = connection.getContentLength();
					
					// download the file
					InputStream input = new BufferedInputStream(connection.getInputStream());
					OutputStream output = new FileOutputStream(outputFile);
					
					byte[] data = new byte[2048];
					long total = 0;
					int count;
					while ((count = input.read(data)) != -1)
					{
						total += count;
						// publishing the progress....
						publishProgress((int) (total * 100 / fileLength));
						output.write(data, 0, count);
	
					}
					
					//done
					output.flush();
					output.close();
					input.close();
					
					
				}
				//dismiss dialog
				TracksViewerFragment.this.mProgressDialog.dismiss();
				
				//play the track
				Intent viewMediaIntent = new Intent();   
				viewMediaIntent.setAction(android.content.Intent.ACTION_VIEW);   
				viewMediaIntent.setDataAndType(Uri.fromFile(outputFile), "audio/*");   
				//viewMediaIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
				startActivity(viewMediaIntent);     
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
	    protected void onPreExecute()
		{
	        super.onPreExecute();
	        TracksViewerFragment.this.mProgressDialog.show();
	    }

	    @Override
	    protected void onProgressUpdate(Integer... progress)
	    {
	        super.onProgressUpdate(progress);
	       TracksViewerFragment.this.mProgressDialog.setProgress(progress[0]);
	    }
	}
	
	
}
