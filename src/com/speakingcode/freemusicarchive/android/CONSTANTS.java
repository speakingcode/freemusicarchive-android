package com.speakingcode.freemusicarchive.android;

public class CONSTANTS
{
	//http://freemusicarchive.org/api/get/{dataset}.{format}?api_key={yourkey}
	//For example:
	//h
	public static String apiKey					= "R2FNSRG1D3FGDKG6";
	public static String baseUrl				= "http://freemusicarchive.org/";
	public static String apiUrl					= "api/get/";
	public static String dataFormatJson			= ".json"; 
	public static String dataFormatXml			= ".xml";
	public static String defaultDataFormat		= dataFormatJson;
	public static String curatorsAction			= "curators";
	public static String genresAction			= "genres";
	public static String artistsAction			= "artists";
	public static String albumsAction			= "albums";
	public static String tracksAction			= "tracks";
}
