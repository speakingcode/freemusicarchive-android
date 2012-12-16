package com.speakingcode.freemusicarchive.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AlbumController
{
	private static AlbumController INSTANCE = new AlbumController();

	List<IAlbumControllerObserver> mObservers = new LinkedList<IAlbumControllerObserver>();
	private HashMap<String,AlbumRecordSet> mAlbumRecordSets = new HashMap<String,AlbumRecordSet>();
	
	private AlbumController() {}
	
	public static AlbumController getInstance()
	{
		return INSTANCE;
	}
	
	public void getAllAlbumsWithGenreHandle(final String genreHandle)
	{
		if (mAlbumRecordSets.containsKey(genreHandle))
		{
			AlbumRecordSet ars = mAlbumRecordSets.get(genreHandle);
			for (IAlbumControllerObserver observer : mObservers)
				observer.onGetAlbumRecordSetSuccess(ars);
			mObservers.clear();
		}
		
		else
		{
			new Thread()
			{
				public void run()
				{
					AlbumRecordSet ars = AlbumConnector.getlAllAlbumsWithGenreHandle(genreHandle);
					mAlbumRecordSets.put(genreHandle, ars);
					for (IAlbumControllerObserver observer : mObservers)
						observer.onGetAlbumRecordSetSuccess(ars);
					mObservers.clear();
				}
			}.start();
		}
		
	}	
	
	public void addObserver(IAlbumControllerObserver obs)
	{
		mObservers.add(obs);
	}

}
