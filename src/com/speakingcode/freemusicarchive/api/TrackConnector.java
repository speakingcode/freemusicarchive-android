package com.speakingcode.freemusicarchive.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class TrackConnector 
{
	private static String TAG		= "TrackConnector";
	/**
	 * Retrieves <code>limit</code> many track records from the tracks dataset, starting from the
	 * position <code>limit*page</code>, filtered by values provided for each filter parameter.<br>
	 * Provide 'false' to ignore boolean parameters. Provide 'null' to ignore other parameters.
	 * @param trackId (int) include only track with track id
	 * @param artistId (int) include tracks with artist id
	 * @param albumId (int) include tracks with album id
	 * @param genreId (int) include tracks with genre id
	 * @param genreHandle include tracks with genre handle
	 * @param artistHandle include tracks with artist handle
	 * @param curatorHandle include tracks with curator handle
	 * @param albumUrl include tracks with album url
	 * @param albumTitle include tracks with album title
	 * @param albumHandle include tracks with album handle
	 * @param commercial include tracks where permitted use for commercial purposes
	 * @param remix include tracks where permitted to remix
	 * @param podcast include tracks where permitted to add to podcast
	 * @param video include tracks where permitted to sync with video
	 * @param addedWeek include tracks added in the last week
	 * @param addedMonth include tracks added in the last month
	 * @param onlyInstrumental include only instrumental tracks
	 * @param radioSafe include only radio safe tracks
	 * @param minDuration include tracks at least as long as (must be in 'HH:MM:SS' format)
	 * @param maxDuration include tracks no longer than (must be in 'HH:MM:SS' format)
	 * @param limit the number of records to fetch at once, between 1 and 50
	 * @param page the page of the recordset to retrieve
	 * @param sortBy A field to sort by (must be one of the returned values)
	 * @param sortDir The direction to sort (asc or desc)
	 * @return
	 */
	public static TrackRecordSet getTrackRecordSet(String trackId, String artistId, String albumId,
			String genreId, String genreHandle, String artistHandle, String curatorHandle,
			String albumUrl, String albumTitle, String albumHandle, boolean commercial,
			boolean remix, boolean podcast, boolean video, boolean addedWeek, boolean addedMonth,
			boolean onlyInstrumental, boolean radioSafe, String minDuration, String maxDuration,
			int limit, int page, String sortBy, String sortDir)
	{
		//add the args to an array for the query url
		//don't include null, empty or false fields.
		ArrayList<String> args = new ArrayList<String>();
		
		if ( !(trackId == null || trackId == "" ) )
			args.add("track_id:" + trackId);
		
		if ( !(artistId == null || artistId == "" ) )
			args.add("artist_id:" + artistId);
		
		if ( !(albumId == null || albumId == "" ) )
			args.add("album_id:" + albumId);
		
		if ( !(genreId == null || genreId == "" ) )
			args.add("genre_id:" + genreId);
		
		if ( !(genreHandle == null || genreHandle == "" ) )
			args.add("genre_handle:" + genreHandle);
		
		if ( !(artistHandle == null || artistHandle == "" ) )
			args.add("artist_handle:" + artistHandle);
		
		if ( !(curatorHandle == null || curatorHandle == "" ) )
			args.add("curator_handle:" + curatorHandle);
	
		if ( !(albumUrl == null || albumUrl == "" ) )
			args.add("album_url:" + albumUrl);
		
		if ( !(albumTitle == null || albumTitle == "" ) )
			args.add("album_title:" + albumTitle);
		
		if ( !(albumHandle == null || albumHandle == "" ) )
			args.add("album_handle:" + albumHandle);
		
		if (commercial)
			args.add("commercial:true");
		
		if (remix)
			args.add("remix:true");
		
		if (podcast)
			args.add("podcast:true");
		
		if (video)
			args.add("video:true");
		
		if (addedWeek)
			args.add("added_week:true");
		
		if (addedMonth)
			args.add("added_month:true");
		
		if (onlyInstrumental)
			args.add("only_instrumental:true");
		
		if (radioSafe)
			args.add("radio_safe:true");
		
		if ( !(minDuration == null || minDuration == "" ) )
			args.add("min_duration:" + minDuration);
		
		if ( !(maxDuration == null || maxDuration == "" ) )
			args.add("max_duration:" + maxDuration);
		
		args.add( (limit < 1 || limit > 50) ? ("limit:50") : ("limit:" + limit));
		
		args.add( (page < 1 ) ? ("page:1") : ("page:" + page));
		
		if ( !(sortBy == null || sortBy == "" ))
			args.add("sort_by:" + sortBy);
		
		if (sortDir == "asc" || sortDir == "desc")
			args.add("sort_dir:" + sortDir);
		
		String responseJSON = FMAConnector.callWebService(FMAConnector.createJSONRequestUrl("tracks", args));
		
		int totalItems			= FMAConnector.getTotalItemsFromJSONResponse(responseJSON);
		int totalPages			= FMAConnector.getTotalPagesFromJSONResponse(responseJSON);
		int currentPage			= FMAConnector.getPageFromJSONResponse(responseJSON);
		return new TrackRecordSet(getTrackListFromJSONResponse(responseJSON), totalItems, totalPages, currentPage);
		
	}

	/**
	 * Convenience method for getting unfiltered, unsorted track record sets 
	 * @param limit the number of records to pull, 1-50
	 * @param page the offset to pull from, with limit per page 
	 * @return
	 */
	public static TrackRecordSet getTrackRecordSet(int limit, int page)
	{
		return getTrackRecordSet(limit, page, null, null);
	}
	
	/**
	 * Convenience method for getting unfiltered track record sets 
	 * @param limit the number of records to pull, 1-50
	 * @param page the offset to pull from, with limit per page 
	 * @param sortBy the returned field to sort by
	 * @param sortDir the directino to sort ("asc" or "desc")
	 * @return
	 */
	public static TrackRecordSet getTrackRecordSet(int limit, int page, String sortBy, String sortDir)
	{
		return getTrackRecordSet(null, null, null, null, null, null, null, null,
				null, null, false, false, false, false, false, false, false,
				false, null,null, limit, page, null, null);
	}
	
	/**
	 * Convenience method for getting a track record set from a particular album (by handle)
	 * @param limit the number of records to pull, 1-50
	 * @param page the offset to pull from, with limit per page 
	 * @param sortBy the returned field to sort by
	 * @param sortDir the directino to sort ("asc" or "desc")
	 * @return
	 */
	private static TrackRecordSet getTrackRecordSetWithAlbumHandle(
			String albumHandle, int limit, int page, String sortBy, String sortDir)
	{
		return getTrackRecordSet(null, null, null, null, null, null, null, null,
				null, albumHandle, false, false, false, false, false, false, false,
				false, null,null, limit, page, null, null);
	}
	
	/**
	 * Parses a String representation of a JSON track query response object
	 * and returns an ArrayList of Track objects obtained from the response object
	 * @param jsonTracksResponseString a String representation of a JSON object response from
	 * a track request.
	 * @return an ArrayList of Track objects obtained from the response object
	 */
	public static ArrayList<Track> getTrackListFromJSONResponse(String jsonTracksResponseString)
	{
		ArrayList<Track> trackList;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonTracksResponseString);
			trackList = getTrackListFromJSONResponse(jsonResponseObject);
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getTrackListFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
			trackList = new ArrayList<Track>();
		}
		
		Log.i(TAG, "*****getTrackListFromJSONResponse() completed.******");
		for (Track track : trackList)
		{
			Log.i(TAG, "trackId: " + track.getTrackId() + "; track title: " + track.getTrackTitle());
		}
		return trackList;
	}
	
	/**
	 * Parses a JSON track-response object and returns an ArrayList of Track objects
	 * obtained from the response object
	 * @param jsonTracksResponseObject a JSON object response from
	 * a track request.
	 * @return an ArrayList of Track objects obtained from the responce object
	 * @throws JSONException 
	 */
	public static ArrayList<Track> getTrackListFromJSONResponse(JSONObject jsonTracksResponseObject)
			throws JSONException
	{
		ArrayList<Track> trackList = new ArrayList<Track>();
		JSONArray tracks = jsonTracksResponseObject.getJSONArray("dataset");
		for (int i = 0; i < tracks.length(); i++)
		{
			trackList.add(getTrackFromJSONObject(tracks.getJSONObject(i)));
		}
		return trackList;
	}

	/**
	 * Builds a Track object from a source JSON object representing an audio/song track
	 * @param jsonTrackObject the JSON object representing the track
	 * @return a Track object with fields populated from the provided JSON object
	 */
	public static Track getTrackFromJSONObject(JSONObject jsonTrackObject)
	{
		Track track = new Track();
		try
		{
			track.setAlbumId			(jsonTrackObject.getString("album_id"));
			track.setAlbumTitle			(jsonTrackObject.getString("album_title"));
			track.setAlbumUrl			(jsonTrackObject.getString("album_url"));
			track.setArtistId			(jsonTrackObject.getString("artist_id"));
			track.setArtistName			(jsonTrackObject.getString("artist_name"));
			track.setArtistUrl			(jsonTrackObject.getString("artist_url"));
			track.setArtistWebsite		(jsonTrackObject.getString("artist_website"));
			track.setLicenseTitle		(jsonTrackObject.getString("license_title"));
			track.setLicenseUrl			(jsonTrackObject.getString("license_url"));
			track.setTrackBitRate		(jsonTrackObject.getString("track_bit_rate"));
			track.setTrackComments		(jsonTrackObject.getString("track_comments"));
			track.setTrackComposer		(jsonTrackObject.getString("track_composer"));
			track.setTrackCopyrightC	(jsonTrackObject.getString("track_copyright_c"));
			track.setTrackCopyrightP	(jsonTrackObject.getString("track_copyright_p"));
			track.setTrackDateCreated	(jsonTrackObject.getString("track_date_created"));
			track.setTrackDateRecorded	(jsonTrackObject.getString("track_date_recorded"));
			track.setTrackDiscNumber	(jsonTrackObject.getString("track_disc_number"));
			track.setTrackDuration		(jsonTrackObject.getString("track_duration"));
			track.setTrackExplicit		(jsonTrackObject.getString("track_explicit"));
			track.setTrackFavorites		(jsonTrackObject.getString("track_favorites"));
			track.setTrackId			(jsonTrackObject.getString("track_id"));
			track.setTrackImageFile		(jsonTrackObject.getString("track_image_file"));
			track.setTrackInformation	(jsonTrackObject.getString("track_information"));
			track.setTrackInstrumental	(jsonTrackObject.getString("track_instrumental"));
			track.setTrackLanguageCode	(jsonTrackObject.getString("track_language_code"));
			track.setTrackListens		(jsonTrackObject.getString("track_listens"));
			track.setTrackLyricist		(jsonTrackObject.getString("track_lyricist"));
			track.setTrackNumber		(jsonTrackObject.getString("track_number"));
			track.setTrackPublisher		(jsonTrackObject.getString("track_publisher"));
			track.setTrackTitle			(jsonTrackObject.getString("track_title"));
			track.setTrackUrl			(jsonTrackObject.getString("track_url"));
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSONException in getTrackFromJSONObject(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return track;
	}
	
	/**
	 * Gets all albums from a particular genre, sorted.<br>
	 * This may take quite a bit of time for multiple calls over the network complete. 
	 * @param ganreHandle
	 * @return
	 */
	public static TrackRecordSet getlAllTracksWithAlbumHandle(String albumHandle)
	{
		
		//get the first record set, then append with subsequent page record sets
		TrackRecordSet allTracks = getTrackRecordSetWithAlbumHandle(albumHandle, 50, 1, "album_title", "asc");
		TrackRecordSet moreTracks;
		for (int i = 2; i <= allTracks.getTotalPages(); i++)
		{
			moreTracks = getTrackRecordSetWithAlbumHandle(albumHandle, 50, i, "album_title", null);
			allTracks.addTracks(moreTracks.getTrackRecords());

		}

		return allTracks;
	}

}
