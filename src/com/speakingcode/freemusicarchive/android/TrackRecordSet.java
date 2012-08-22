package com.speakingcode.freemusicarchive.android;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackRecordSet
{
	private ArrayList<Track> trackRecords;
	
	public TrackRecordSet(ArrayList<Track> records)
	{
		this.trackRecords = records;
	}
	
	public TrackRecordSet()
	{
		this(new ArrayList<Track>());
	}
	
	public ArrayList<Track> getRecords()
	{
		return trackRecords;
	}
	
	public void setRecords(ArrayList<Track> records)
	{
		this.trackRecords = records;
	}
	
	public void addTracks(ArrayList<Track> newTracks)
	{
		this.trackRecords.addAll(newTracks);
	}
	
	public void addTrack(Track newTrack)
	{
		this.trackRecords.add(newTrack);
	}
	
	public void removeTrack(Track trackToRemove)
	{
		this.trackRecords.remove(trackToRemove);
	}
	
	public void removeTrack(String removeTrackId)
	{
		Iterator<Track> i = trackRecords.iterator();
		while (i.hasNext())
		{
			if (i.next().getTrackId() == removeTrackId)
				i.remove();
		}
	}

}
