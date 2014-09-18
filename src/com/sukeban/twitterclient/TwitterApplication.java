package com.sukeban.twitterclient;

import android.content.Context;

public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
		
		// TODO: possibly allocate the image loader
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}
}