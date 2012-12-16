package com.speakingcode.android.imageFetcher;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Queue;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageFetcher implements Runnable {

	private Queue<IImageFetcherItem> mItems;
	private IImageFetcherClient mClient;

	public ImageFetcher(IImageFetcherClient client, Queue<IImageFetcherItem> items)
	{
		this.mItems		= items;
		this.mClient	= client;
	}

	@Override
	public void run()
	{
		IImageFetcherItem currentItem;
		String currentItemUrl;
		while ((currentItem = mItems.peek()) != null)
		{
			currentItemUrl = currentItem.getFeaturedImageUrl();
			
			try
			{
				URL newurl = new URL(currentItemUrl);
				Bitmap featuredImageBitmap = BitmapFactory.decodeStream(
						newurl.openConnection().getInputStream());
				// return featuredImageBitmap;
				currentItem.setFeaturedImage(featuredImageBitmap);
				mClient.fetchImageSuccess();
			}
			catch (MalformedURLException e)
			{
				Log.e("ImgeFetcher", "Malformed URL exception. url: "  + currentItemUrl );
				mClient.fetchImageFail();
				e.printStackTrace();
			}
			catch (IOException e)
			{
				mClient.fetchImageFail();
				e.printStackTrace();
			}
		}
		
			
			
	
		// return null;

	}
}
