package com.speakingcode.android.imageFetcher;

import android.graphics.Bitmap;

public interface IImageFetcherItem
{
	public void setFeaturedImage(Bitmap featuredImage);
	public Bitmap getFeaturedImage();
	public void setFeatuedImageUrl(String url);
	public String getFeaturedImageUrl();
}
