package com.speakingcode.freemusicarchive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class AlbumConnector
{
	public static final String TAG = "AlbumConnector";

	/**
	 * @param limit the number of records to fetch at once, between 1 and 50
	 * @param page the page of the recordset to retrieve
	 * @param sortBy A field to sort by (must be one of the returned values)
	 * @param sortDir The directin to sort (asc or desc)
	 * @return
	 */
	public static AlbumRecordSet getAlbumRecordSet(String albumHandle, String artistId, String artistHandle, String genreHandle,
			String curatorHandle, String albumUrl, String albumTitle, int limit, int page, String sortBy, String sortDir)
	{
		//add the args to an array for the query url
		//don't include null, empty or false fields.
		ArrayList<String> args = new ArrayList<String>();
		
		if ( !(albumHandle == null || albumHandle == "" ) )
			args.add("album_handle:" + albumHandle);
		
		if ( !(artistId == null || artistId == "" ) )
			args.add("artist_id:" + artistId);

		if ( !(artistHandle == null || artistHandle == "" ) )
			args.add("artist_handle:" + artistHandle);
		
		if ( !(genreHandle == null || genreHandle == "" ) )
			args.add("genre_handle:" + genreHandle);
		
		if ( !(curatorHandle == null || curatorHandle == "" ) )
			args.add("curator_handle:" + curatorHandle);
	
		if ( !(albumUrl == null || albumUrl == "" ) )
			args.add("album_url:" + albumUrl);
		
		if ( !(albumTitle == null || albumTitle == "" ) )
			args.add("album_title:" + albumTitle);
		//limit must be [1...50] per API spec
		args.add( (limit < 1 || limit > 50) ? ("limit:50") : ("limit:" + limit));
		//page cannot be negative
		args.add( (page < 1 ) ? ("page:1") : ("page:" + page));
		
		if ( !(sortBy == null || sortBy == "" ))
			args.add("sort_by:" + sortBy);
		
		if (sortDir == "asc" || sortDir == "desc")
			args.add("sort_dir:" + sortDir);
		
		String responseJSON = FMAConnector.callWebService(FMAConnector.createJSONRequestUrl("albums", args));
		
		int totalItems			= FMAConnector.getTotalItemsFromJSONResponse(responseJSON);
		int totalPages			= FMAConnector.getTotalPagesFromJSONResponse(responseJSON);
		int currentPage			= FMAConnector.getPageFromJSONResponse(responseJSON);
		return new AlbumRecordSet(getAlbumListFromJSONResponse(responseJSON), totalItems, totalPages, currentPage);
		
	}
	
	
	/**
	 * Convenience method for getting unfiltered, unsorted album record sets 
	 * @param limit the number of records to pull, 1-50
	 * @param page the offset to pull from, with limit per page 
	 * @return
	 */
	public static AlbumRecordSet getAlbumRecordSet(int limit, int page)
	{
		return getAlbumRecordSet(limit, page, null, null);
	}
	
	/**
	 * Convenience method for getting unfiltered album record sets 
	 * @param limit the number of records to pull, 1-50
	 * @param page the offset to pull from, with limit per page 
	 * @param sortBy the returned field to sort by
	 * @param sortDir the directino to sort ("asc" or "desc")
	 * @return
	 */
	public static AlbumRecordSet getAlbumRecordSet(int limit, int page, String sortBy, String sortDir)
	{
		return getAlbumRecordSet(null, null, null, null, null, null, null, limit, page, sortBy, sortDir);
	}
	
	/**
	 * Convenience method to get an AlbumRecordSet with specific genre handle
	 * @param genreHandle
	 * @param limit
	 * @param page
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	public static AlbumRecordSet getAlbumRecordSetWithGenreHandle(String genreHandle, int limit, int page,
			String sortBy, String sortDir)
	{
		 return getAlbumRecordSet(null, null, null, genreHandle, null, null, null, limit, page, sortBy, sortDir);
	}
	
	/**
	 * Parses a String representation of a JSON album query response object
	 * and returns an ArrayList of Album objects obtained from the response object
	 * @param jsonAlbumsResponseString a String representation of a JSON object response from
	 * a album request.
	 * @return an ArrayList of Album objects obtained from the response object
	 */
	public static ArrayList<Album> getAlbumListFromJSONResponse(String jsonAlbumsResponseString)
	{
		ArrayList<Album> albumList;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonAlbumsResponseString);
			albumList = getAlbumListFromJSONResponse(jsonResponseObject);
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getAlbumListFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
			albumList = new ArrayList<Album>();
		}
		
		Log.i(TAG, "*****getAlbumListFromJSONResponse() completed.******");
		for (Album album : albumList)
		{
			Log.i(TAG, "albumId: " + album.getAlbumId() + "; album title: " + album.getAlbumTitle());
		}
		return albumList;
	}
	
	/**
	 * Parses a JSON album-response object and returns an ArrayList of Album objects
	 * obtained from the response object
	 * @param jsonAlbumsResponseObject a JSON object response from
	 * a album request.
	 * @return an ArrayList of Album objects obtained from the responce object
	 * @throws JSONException 
	 */
	public static ArrayList<Album> getAlbumListFromJSONResponse(JSONObject jsonAlbumsResponseObject)
			throws JSONException
	{
		ArrayList<Album> albumList = new ArrayList<Album>();
		JSONArray albums = jsonAlbumsResponseObject.getJSONArray("dataset");
		for (int i = 0; i < albums.length(); i++)
		{
			albumList.add(getAlbumFromJSONObject(albums.getJSONObject(i)));
		}
		return albumList;
	}

	/**
	 * Builds a Album object from a source JSON object representing an audio/song album
	 * @param jsonAlbumObject the JSON object representing the album
	 * @return a Album object with fields populated from the provided JSON object
	 */
	public static Album getAlbumFromJSONObject(JSONObject jsonAlbumObject)
	{
		Album album = new Album();
		try
		{
			album.setAlbumId			(jsonAlbumObject.getString("album_id"));
			album.setAlbumTitle			(jsonAlbumObject.getString("album_title"));
			album.setAlbumHandle		(jsonAlbumObject.getString("album_handle"));
			album.setAlbumType			(jsonAlbumObject.getString("album_type"));
			album.setAlbumUrl			(jsonAlbumObject.getString("album_url"));
			album.setArtistName			(jsonAlbumObject.getString("artist_name"));
			album.setArtistUrl			(jsonAlbumObject.getString("artist_url"));
			album.setAlbumProducer		(jsonAlbumObject.getString("album_producer"));
			album.setAlbumEngineer		(jsonAlbumObject.getString("album_engineer"));
			album.setAlbumInformation	(jsonAlbumObject.getString("album_information"));
			album.setAlbumDateReleased	(jsonAlbumObject.getString("album_date_released"));
			album.setAlbumComments		(jsonAlbumObject.getString("album_comments"));
			album.setAlbumFavorites		(jsonAlbumObject.getString("album_favorites"));
			album.setAlbumTracks		(jsonAlbumObject.getString("album_tracks"));
			album.setAlbumDateCreated	(jsonAlbumObject.getString("album_date_created"));
			album.setAlbumListens		(jsonAlbumObject.getString("album_listens"));
			album.setAlbumImageFile		(jsonAlbumObject.getString("album_image_file"));
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getAlbumFromJSONObject(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return album;
	}
	
	
	/**
	 * Gets all albums from a particular genre, sorted.<br>
	 * This may take quite a bit of time for multiple calls over the network complete. 
	 * @param ganreHandle
	 * @return
	 */
	public static AlbumRecordSet getlAllAlbumsWithGenreHandle(String genreHandle)
	{
		
		//get the first record set, then append with subsequent page record sets
		AlbumRecordSet allAlbums = getAlbumRecordSetWithGenreHandle(genreHandle, 50, 1, "album_title", "asc");
		AlbumRecordSet moreAlbums;
		for (int i = 2; i <= allAlbums.getTotalPages(); i++)
		{
			moreAlbums = getAlbumRecordSetWithGenreHandle(genreHandle, 50, i, null, null);
			allAlbums.addAlbums(moreAlbums.getAlbumRecords());

		}

		return allAlbums;
	}
}
