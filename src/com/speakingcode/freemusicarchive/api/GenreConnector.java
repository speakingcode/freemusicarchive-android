package com.speakingcode.freemusicarchive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GenreConnector
{
	public static String TAG = "GenreConnector";

	/**
	 * 
	 */
	public static GenreRecordSet getGenreRecordSet(int limit, int page, String sortBy, String sortDir)
	{

		//add the args to an array for the query url
		//don't include null, empty or false fields.
		ArrayList<String> args = new ArrayList<String>();
		args.add( (limit < 1 || limit > 50) ? ("limit:50") : ("limit:" + limit));

		args.add( (page < 1 ) ? ("page:1") : ("page:" + page));
		
		if ( !(sortBy == null || sortBy == "" ))
			args.add("sort_by:" + sortBy);

		if (sortDir == "asc" || sortDir == "desc")
			args.add("sort_dir:" + sortDir);
		
		String responseJSON = FMAConnector.callWebService(FMAConnector.createJSONRequestUrl("genres", args));
		
		int totalItems			= FMAConnector.getTotalItemsFromJSONResponse(responseJSON);
		int totalPages			= FMAConnector.getTotalPagesFromJSONResponse(responseJSON);
		int currentPage			= FMAConnector.getPageFromJSONResponse(responseJSON);
		
		return new GenreRecordSet(getGenreListFromJSONResponse(responseJSON), totalItems, totalPages, currentPage);

	}
	
	/**
	 * Parses a String representation of a JSON genre query response object
	 * and returns an ArrayList of Genre objects obtained from the response object
	 * @param jsonGenresResponseString a String representation of a JSON object response from
	 * a genre request.
	 * @return an ArrayList of Genre objects obtained from the response object
	 */
	public static ArrayList<Genre> getGenreListFromJSONResponse(String jsonGenresResponseString)
	{
		ArrayList<Genre> genreList;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonGenresResponseString);
			genreList = getGenreListFromJSONResponse(jsonResponseObject);
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getGenreListFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
			genreList = new ArrayList<Genre>();
		}
		
		Log.i(TAG, "*****getGenreListFromJSONResponse() completed.******");
		for (Genre genre : genreList)
		{
			Log.i(TAG, "genreId: " + genre.getGenreId() + "; genre title: " + genre.getGenreTitle());
		}
		return genreList;
	}
	
	/**
	 * Parses a JSON genre query response object and returns an ArrayList of Genre objects
	 * obtained from the response object
	 * @param jsonGenreResponseObject a JSON object response from
	 * a genre request.
	 * @return an ArrayList of Genre objects obtained from the response object
	 * @throws JSONException 
	 */
	public static ArrayList<Genre> getGenreListFromJSONResponse(JSONObject jsonGenreResponseObject)
			throws JSONException
	{
		ArrayList<Genre> genreList = new ArrayList<Genre>();
		JSONArray genres = jsonGenreResponseObject.getJSONArray("dataset");
		for (int i = 0; i < genres.length(); i++)
		{
			genreList.add(getGenreFromJSONObject(genres.getJSONObject(i)));
		}
		return genreList;
	}
	
	/**
	 * Builds a Genre object from a source JSON object representing a genre
	 * @param jsonTrackObject the JSON object representing the genre
	 * @return a Genre object with fields populated from the proviced JSON object
	 */
	public static Genre getGenreFromJSONObject(JSONObject jsonGenreObject)
	{
		Genre genre = new Genre();
		try
		{
			genre.setGenreColor		(jsonGenreObject.getString("genre_color"));
			genre.setGenreHandle	(jsonGenreObject.getString("genre_handle"));
			genre.setGenreId		(jsonGenreObject.getString("genre_id"));
		//	genre.setGenreParentId	(jsonGenreObject.getString("parent_id"));
			genre.setGenreTitle		(jsonGenreObject.getString("genre_title"));
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getGenreFromJSONObject(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return genre;
	}
	
	
	
	/**
	 * Retrieves the entire Genres Data Set, sorted by title
	 * @return
	 */
	public static GenreRecordSet getAllGenres()
	{
		//get the first record set, then append with subsequent page record sets
		GenreRecordSet allGenres = getGenreRecordSet(50, 1, "genre_title", "asc");
		GenreRecordSet moreGenres;
		for (int i = 2; i <= allGenres.getTotalPages(); i++)
		{
			moreGenres = getGenreRecordSet(50, i, null, null);
			allGenres.addGenres(moreGenres.getGenreRecords());
			
		}
		
		return allGenres;
	}
	
}
