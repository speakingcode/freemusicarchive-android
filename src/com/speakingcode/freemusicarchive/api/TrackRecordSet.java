package com.speakingcode.freemusicarchive.api;

import java.util.ArrayList;
import java.util.Iterator;

public class TrackRecordSet
{
	ArrayList<Track> trackRecords;
	int total;
	int totalPages;
	int currentPage;
	
	public TrackRecordSet(ArrayList<Track> records, int total, int totalPages, int currentPage)
	{
		this.trackRecords	= records;
		this.total			= total;
		this.totalPages		= totalPages;
		this.currentPage	= currentPage;
	}
	
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public ArrayList<Track> getTrackRecords()
	{
		return trackRecords;
	}
	
	public void setTrackRecords(ArrayList<Track> records)
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
