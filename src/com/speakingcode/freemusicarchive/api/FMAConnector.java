/*
 *  Free Music Archive - Download Free Music legally from the FreeMusicArchive
 *  Copyright (C) 2012  Daniel Lissner (http://speakingcode.com)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.speakingcode.freemusicarchive.api;

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
	
	static String TAG								= "FMAConnector";
	
	
	
	
	
	/**
	 * ************************************************************
	 * **********************Web Service API***********************
	 * ************************************************************
	 */
	
	/**
	 * Creates a request url for the FMA API
	 * @param dataset
	 * @param args
	 * @return
	 */
	public static String createRequestUrl(String dataset, ArrayList<String> args, String dataFormat)
	{
		String argString = "";
		if (args != null)
		{
			for (String arg : args)
			
			{
				if (arg == null) continue;
				
				String[] argKeyValuePair = arg.split(":");
				argString += ("&" + argKeyValuePair[0] + "=" + argKeyValuePair[1]);
			}
		}
		
		if (dataFormat == null || dataFormat == "")
			dataFormat = defaultDataFormat;
		
		String requestUrl = (baseUrl + apiUrl + dataset + dataFormat
				+ "?" + "api_key=" + apiKey + argString);
		Log.i(TAG, "**getRequestUrl() called with action: " + dataset + " dataFormat: " + dataFormat + "**");
		Log.i(TAG, requestUrl);
		Log.i(TAG, "****************************************");
		
		return requestUrl;
	}
	
	/**
	 * Builds a REST API request URL for JSON response based on the provided dataset and arguments,
	 * @param dataset The API action to hit
	 * @param args The arguments to pass to the REST API
	 * @return
	 */
	public static String createJSONRequestUrl(String dataset, ArrayList<String> args)
	{
		return createRequestUrl(dataset, args, FMAConnector.dataFormatJson);
	}
	
 	public String createXMLRequestUrl(String dataset, ArrayList<String> args)
 	{
 		return createRequestUrl(dataset, args, FMAConnector.dataFormatXml);
 	}
	
	/**
	 * Makes the HTTP GET request to the provided REST url 
	 * @param requestUrl the URL to request
	 * @return The string of the response from the HTTP request
	 */
	public static String callWebService(String requestUrl)
	{
		String deviceId					= "xxxxx";   
		
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
	 * **********************************************************
	 * **********************Track Records***********************
	 * **********************************************************
	 */
	
	
	/**
	 * **********************************************************
	 * **********************Genre Records***********************
	 * **********************************************************
	 */


	/**
	 * **********************************************************
	 * **********************Album Records***********************
	 * **********************************************************
	 */
	
	
	
	
	/**
	 * **********************************************************
	 * ****************** General Response Handling *************
	 * **********************************************************
	 */
	
	
	/**
	 * Gets the record set's page in the source dataset
	 * @param jsonResponseString
	 * @return
	 */
	public static int getPageFromJSONResponse(String jsonResponseString)
	{
		int page = -1;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonResponseString);
			page = Integer.parseInt(jsonResponseObject.getString("page"));
			
		}
		catch(JSONException e)
		{
			Log.e(TAG, "JSONException in getPageFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return page;
	}

	/**
	 * Gets the total number of pages in a dataset
	 * @param jsonResponseString
	 * @return
	 */
	public static int getTotalPagesFromJSONResponse(String jsonResponseString)
	{
		int totalPages = -1;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonResponseString);
			totalPages = jsonResponseObject.getInt("total_pages");
			
		}
		catch(JSONException e)
		{
			Log.e(TAG, "JSONException in getTotalPagesFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return totalPages;
	}

	/**
	 * Gets the total number of items in a dataset
	 * @param jsonResponseString
	 * @return
	 */
	public static int getTotalItemsFromJSONResponse(String jsonResponseString)
	{
		int totalItems = -1;
		try
		{
			JSONObject jsonResponseObject = new JSONObject(jsonResponseString);
			totalItems = Integer.parseInt(jsonResponseObject.getString("total"));
		}
		catch(JSONException e)
		{
			Log.e(TAG, "JSONException in getTotalPagesFromJSONResponse(): " + e.getMessage());
			e.printStackTrace();
		}
		
		return totalItems;
	}
}
