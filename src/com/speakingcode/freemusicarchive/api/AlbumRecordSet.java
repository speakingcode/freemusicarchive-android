package com.speakingcode.freemusicarchive.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class AlbumRecordSet
{
	List<Album> albumRecords;
	int total;
	int totalPages;
	int currentPage;
	
	public AlbumRecordSet(ArrayList<Album> records, int total, int totalPages, int currentPage)
	{
		this.albumRecords	= records;
		Collections.sort(albumRecords);

		this.total			= total;
		this.totalPages		= totalPages;
		this.currentPage	= currentPage;
	}
	
	public int getTotal()
	{
		return total;
	}

	public void setTotal(int total)
	{
		this.total = total;
	}

	public int getTotalPages()
	{
		return totalPages;
	}

	public void setTotalPages(int totalPages)
	{
		this.totalPages = totalPages;
	}

	
	public int getCurrentPage()
	{
		return currentPage;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public List<Album> getAlbumRecords()
	{
		return albumRecords;
	}
	
	public void setAlbumRecords(ArrayList<Album> records)
	{
		this.albumRecords = records;
	}
	
	public void addAlbums(List<Album> newAlbums)
	{
		this.albumRecords.addAll(newAlbums);
		Collections.sort(albumRecords);
	}
	
	public void addAlbum(Album newAlbum)
	{
		this.albumRecords.add(newAlbum);
	}
	
	public void removeAlbum(Album albumToRemove)
	{
		this.albumRecords.remove(albumToRemove);
	}
	
	public void removeAlbum(String removeAlbumId)
	{
		Iterator<Album> i = albumRecords.iterator();
		while (i.hasNext())
		{
			if (i.next().getAlbumId() == removeAlbumId)
				i.remove();
		}
	}

}
