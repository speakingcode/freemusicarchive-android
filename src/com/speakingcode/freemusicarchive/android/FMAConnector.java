package com.speakingcode.freemusicarchive.android;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class FMAConnector
{
	
	//http://freemusicarchive.org/api/get/{dataset}.{format}?api_key={yourkey}
	//For example:
	//http://freemusicarchive.org/api/get/curators.xml?api_key=60BLHNQCAOUFPIBZ

	public static final String apiKey				= "R2FNSRG1D3FGDKG6";
	public static final String baseUrl				= "http://freemusicarchive.org/";
	public static final String apiUrl				= "api/get/";
	public static final String dataFormatJson		= ".json"; 
	public static final String dataFormatXml		= ".xml";
	public static final String defaultDataFormat	= dataFormatJson;
	public static final String curatorsAction		= "curators";
	public static final String genresAction			= "genres";
	public static final String artistsAction		= "artists";
	public static final String albumsAction			= "albums";
	public static final String tracksAction			= "tracks";
	final String TAG								= "FMAConnector";
	
	/**
	 * @param action
	 * @param args
	 * @return
	 */
	public String getRequestUrl(String action, ArrayList<String> args, String dataFormat)
	{
		String argString = "";
		for (String arg : args)
		{
			if (arg == null) continue;
			
			String[] argKeyValuePair = arg.split(":");
			argString += ("&" + argKeyValuePair[0] + "=" + argKeyValuePair[1]);
		}
		
		if (dataFormat == null || dataFormat == "")
			dataFormat = defaultDataFormat;
		
		String requestUrl = (baseUrl + apiUrl + action + dataFormat
				+ "?" + "api_key=" + apiKey + argString);
		Log.i(TAG, "**getRequestUrl() called with action: " + action + " dataFormat: " + dataFormat + "**");
		Log.i(TAG, requestUrl);
		Log.i(TAG, "****************************************");
		
		return requestUrl;
	}
	
	/**
	 * @param action
	 * @param args
	 * @return
	 */
	public String getJSONRequestUrl(String action, ArrayList<String> args)
	{
		return getRequestUrl(action, args, FMAConnector.dataFormatJson);
	}
	
 	public String getXMLRequestUrl(String action, ArrayList<String> args)
 	{
 		return getRequestUrl(action, args, FMAConnector.dataFormatXml);
 	}
	
	/**
	 * Makes the HTTP GET request to the provided REST url 
	 * @param requestUrl the URL to request
	 * @return The string of the response from the HTTP request
	 */
	public String callWebService(String requestUrl)
	{
		String deviceId					= "xxxxx" ;   
		
		HttpClient httpclient			= new DefaultHttpClient();  
		HttpGet request					= new HttpGet(requestUrl);  
		request.addHeader("deviceId", deviceId);  
		
		ResponseHandler<String> handler	= new BasicResponseHandler();  
		String result					= "";  
	
		try
		{  
			result = httpclient.execute(request, handler);  
		}
		catch (ClientProtocolException e)
		{  
			e.printStackTrace();
			Log.e(TAG, "ClientProtocolException in callWebService(). " + e.getMessage());
		}
		catch (IOException e)
		{  
			e.printStackTrace();
			Log.e(TAG, "IOException in callWebService(). " + e.getMessage());
  
		}
		
		httpclient.getConnectionManager().shutdown(); 
		Log.i(TAG, "**callWebService() successful. Result: **");
		Log.i(TAG, result);
		Log.i(TAG, "*****************************************");
		
		return result;
	}
	
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
	 * @param limit the number of records to fetch at once, between 1 and 20
	 * @param page the page of the recordset to retrieve 
	 * @return
	 */
	public TrackRecordSet getTrackRecordSet(String trackId, String artistId, String albumId,
			String genreId, String genreHandle, String artistHandle, String curatorHandle,
			String albumUrl, String albumTitle, String albumHandle, boolean commercial,
			boolean remix, boolean podcast, boolean video, boolean addedWeek, boolean addedMonth,
			boolean onlyInstrumental, boolean radioSafe, String minDuration, String maxDuration,
			int limit, int page)
	{
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
		
		args.add( (limit < 1 || limit > 20) ? ("limit:20") : ("limit:" + limit));
		
		args.add("page:" + page);
		
		String responseJSON = callWebService(getJSONRequestUrl("tracks", args));
		return new TrackRecordSet(getTrackListFromJSONResponse(responseJSON));
		
	}
	
	/**
	 * Convenience method for getting unfiltered track record sets 
	 * @param limit the number of records to pull, 1-20
	 * @param page the offset to pull from, with limit per page 
	 * @return
	 */
	public TrackRecordSet getTrackRecordSet(int limit, int page)
	{
		return getTrackRecordSet(null, null, null, null, null, null, null, null,
				null, null, false, false, false, false, false, false, false,
				false, null,null, limit, page);
	}
	
	/**
	 * Parses a String representation of a JSON track-response object
	 * and returns an ArrayList of Track objects obtained from the response object
	 * @param jsonTracksResponseString a String representation of a JSON object response from
	 * a track request.
	 * @return an ArrayList of Track objects obtained from the response object
	 */
	public ArrayList<Track> getTrackListFromJSONResponse(String jsonTracksResponseString)
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
	public ArrayList<Track> getTrackListFromJSONResponse(JSONObject jsonTracksResponseObject)
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
	 * @return a Track object with the same data as the JSON object
	 */
	private Track getTrackFromJSONObject(JSONObject jsonTrackObject)
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return track;
	}
}
