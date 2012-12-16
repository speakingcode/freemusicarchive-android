package com.speakingcode.freemusicarchive.api;

import android.graphics.Bitmap;

import com.speakingcode.android.imageFetcher.IImageFetcherItem;

public class Album
implements Comparable<Album>,
	IImageFetcherItem
{
    String albumId;
    String albumTitle;
    String albumHandle;
    String albumUrl;
    String albumType;
    String artistName;
    String artistUrl;
    String albumProducer;
    String albumEngineer;
    String albumInformation;
    String albumDateReleased;
    String albumComments;
    String albumFavorites;
    String albumTracks;
    String albumListens;
    String albumDateCreated;
    String albumImageFile;
	


	public String getAlbumId()
	{
		return albumId;
	}
	
	public void setAlbumId(String albumId)
	{
		this.albumId = albumId;
	}
	
	public String getAlbumTitle()
	{
		return albumTitle;
	}
	
	public void setAlbumTitle(String albumTitle)
	{
		this.albumTitle = albumTitle;
	}
	
	public String getAlbumHandle()
	{
		return albumHandle;
	}
	
	public void setAlbumHandle(String albumHandle)
	{
		this.albumHandle = albumHandle;
	}
	
	public String getAlbumType()
	{
		return albumType;
	}
	
	public void setAlbumType(String albumType)
	{
		this.albumType = albumType;
	}
	
	public String getAlbumUrl()
	{
		return albumUrl;
	}
	
	public void setAlbumUrl(String albumUrl)
	{
		this.albumUrl = albumUrl;
	}
	
	public String getArtistName()
	{
		return artistName;
	}
	
	public void setArtistName(String artistName)
	{
		this.artistName = artistName;
	}
	
	public String getArtistUrl()
	{
		return artistUrl;
	}
	
	public void setArtistUrl(String artistUrl)
	{
		this.artistUrl = artistUrl;
	}
	
	public String getAlbumDateReleased()
	{
		return albumDateReleased;
	}
	
	public void setAlbumDateReleased(String albumDateReleased)
	{
		this.albumDateReleased = albumDateReleased;
	}
	
	public String getAlbumDateCreated()
	{
		return albumDateCreated;
	}
	
	public void setAlbumDateCreated(String albumDateCreated)
	{
		this.albumDateCreated = albumDateCreated;
	}
	
	public String getAlbumListens()
	{
		return albumListens;
	}
	
	public void setAlbumListens(String albumListens)
	{
		this.albumListens = albumListens;
	}

	public String getAlbumProducer() {
		return albumProducer;
	}

	public void setAlbumProducer(String albumProducer) {
		this.albumProducer = albumProducer;
	}

	public String getAlbumEngineer() {
		return albumEngineer;
	}

	public void setAlbumEngineer(String albumEngineer) {
		this.albumEngineer = albumEngineer;
	}

	public String getAlbumInformation() {
		return albumInformation;
	}

	public void setAlbumInformation(String albumInformation) {
		this.albumInformation = albumInformation;
	}

	public String getAlbumComments() {
		return albumComments;
	}

	public void setAlbumComments(String albumComments) {
		this.albumComments = albumComments;
	}

	public String getAlbumFavorites() {
		return albumFavorites;
	}

	public void setAlbumFavorites(String albumFavorites) {
		this.albumFavorites = albumFavorites;
	}

	public String getAlbumTracks() {
		return albumTracks;
	}

	public void setAlbumTracks(String albumTracks) {
		this.albumTracks = albumTracks;
	}
	
	public String getAlbumImageFile()
	{
		return albumImageFile;
	}

	public void setAlbumImageFile(String albumImageFile)
	{
		this.albumImageFile = albumImageFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((albumHandle == null) ? 0 : albumHandle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (albumHandle == null) {
			if (other.albumHandle != null)
				return false;
		} else if (!albumHandle.equals(other.albumHandle))
			return false;
		return true;
	}

	@Override
	public int compareTo(Album another)
	{
		if (this == another)
			return 0;
		if (another == null)
			return -1;
		if (albumHandle == null) {
			if (another.albumHandle != null)
				return 1;
		}
		return albumHandle.compareTo(another.getAlbumHandle());
	}

	@Override
	public void setFeaturedImage(Bitmap featuredImage)
	{
		
	}

	@Override
	public Bitmap getFeaturedImage()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFeatuedImageUrl(String url)
	{
		this.setAlbumImageFile(url);
	}

	@Override
	public String getFeaturedImageUrl()
	{
		return this.getAlbumImageFile();
	}
	
	
}
