package com.speakingcode.freemusicarchive.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TrackController
{
	//TODO full on observer
	private static TrackController INSTANCE = new TrackController();
	
	List<ITrackControllerObserver> mObservers = new LinkedList<ITrackControllerObserver>();
	private HashMap<String,TrackRecordSet> mTrackRecordSets = new HashMap<String,TrackRecordSet>();
	
	private TrackController() {}
	
	public static TrackController getInstance()
	{
		return INSTANCE;
	}
	
	public void getAllTracksWithAlbumHandle(final String albumHandle)
	{
		if (mTrackRecordSets.containsKey(albumHandle))
		{
			TrackRecordSet trs = mTrackRecordSets.get(albumHandle);
			for (ITrackControllerObserver observer : mObservers)
				observer.onGetTrackRecordSetSuccess(trs);
			mObservers.clear();
		}
		
		else
		{
			new Thread()
			{
				public void run()
				{
					TrackRecordSet trs = TrackConnector.getlAllTracksWithAlbumHandle(albumHandle);
					mTrackRecordSets.put(albumHandle, trs);
					for (ITrackControllerObserver observer : mObservers)
						observer.onGetTrackRecordSetSuccess(trs);
					mObservers.clear();
				}
			}.start();
		}
		
	}
	
	public void addObserver(ITrackControllerObserver obs)
	{
		mObservers.add(obs);
	}

}
