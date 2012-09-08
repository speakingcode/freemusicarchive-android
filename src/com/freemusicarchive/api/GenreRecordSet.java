package com.freemusicarchive.api;

import java.util.ArrayList;
import java.util.Iterator;

public class GenreRecordSet
{
	ArrayList<Genre> genreRecords;
	int total;
	int totalPages;
	int currentPage;
	
	public GenreRecordSet(ArrayList<Genre> records, int total, int totalPages, int currentPage)
	{
		this.genreRecords	= records;
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

	public ArrayList<Genre> getGenreRecords()
	{
		return genreRecords;
	}
	
	public void setGenreRecords(ArrayList<Genre> records)
	{
		this.genreRecords = records;
	}
	
	public void addGenres(ArrayList<Genre> newGenres)
	{
		this.genreRecords.addAll(newGenres);
	}
	
	public void addGenre(Genre newGenre)
	{
		this.genreRecords.add(newGenre);
	}
	
	public void removeGenre(Genre genreToRemove)
	{
		this.genreRecords.remove(genreToRemove);
	}
	
	public void removeGenre(String removeGenreId)
	{
		Iterator<Genre> i = genreRecords.iterator();
		while (i.hasNext())
		{
			if (i.next().getGenreId() == removeGenreId)
				i.remove();
		}
	}

}
