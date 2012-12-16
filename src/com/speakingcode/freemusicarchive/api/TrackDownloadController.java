package com.speakingcode.freemusicarchive.api;


import android.util.Log;

public class TrackDownloadController
{
	final static String TAG				= "TrackDownloadController";
	
	public static String getDownloadUrl(String pageUrl)
	{
//		String deviceId					= "xxxxx";   
//		
//		HttpClient httpclient			= new DefaultHttpClient();  
//		HttpGet request					= new HttpGet(pageUrl);  
//		request.addHeader("deviceId", deviceId);  
//		
//		ResponseHandler<String> handler	= new BasicResponseHandler();  
//		String result					= "";  
//	
//		try
//		{  
//			result = httpclient.execute(request, handler);  
//		}
//		catch (ClientProtocolException e)
//		{  
//			e.printStackTrace();
//			Log.e(TAG, "ClientProtocolException in callWebService(). " + e.getMessage());
//		}
//		catch (IOException e)
//		{  
//			e.printStackTrace();
//			Log.e(TAG, "IOException in callWebService(). " + e.getMessage());
//		}
//		
//		httpclient.getConnectionManager().shutdown(); 
//		Log.i(TAG, "**callWebService() successful. Result: **");
//		Log.i(TAG, result);
//		Log.i(TAG, "*****************************************");
//		
		String result = FMAConnector.callWebService(pageUrl);
		//rip the download url out of the page's html
		int begin = result.indexOf("<a href=\"http://freemusicarchive.org/music/download/");
		int end = result.indexOf("\"", begin + 10);
		result = result.substring(begin +9, end);
		
		Log.i(TAG, result);
		
		return result;
	}
}
