package com.speakingcode.freemusicarchive.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class GenreController
{
	private static GenreController INSTANCE		= new GenreController();

	List<IGenreControllerObserver> mObservers	= new LinkedList<IGenreControllerObserver>();
	GenreRecordSet mGenreRecordSet;
	
	private GenreController() {}
	
	public static GenreController getInstance()
	{
		return INSTANCE;
	}
	
	public void getAllGenres()
	{
		if (mGenreRecordSet != null)
		{
			for (IGenreControllerObserver observer : mObservers)
				observer.onGetGenreRecordSetSuccess(mGenreRecordSet);
			mObservers.clear();
		}
		else
		{
			new Thread()
			{
				public void run()
				{
					mGenreRecordSet = GenreConnector.getAllGenres();
					for (IGenreControllerObserver observer : mObservers)
						observer.onGetGenreRecordSetSuccess(mGenreRecordSet);
					mObservers.clear();
				}
			}.start();
		}
		
	}	
	
	public void addObserver(IGenreControllerObserver obs)
	{
		mObservers.add(obs);
	}

}
