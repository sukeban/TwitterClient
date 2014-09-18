package com.sukeban.twitterclient;

import android.content.Context;

public class TwitterApplication extends com.activeandroid.app.Application {
	private static Context context;
	
	// TODO: if not online color the UI or show a toast 

	@Override
	public void onCreate() {
		super.onCreate();
		TwitterApplication.context = this;
		
		// TODO: persistence
	}

	public static TwitterClient getRestClient() {
		return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
	}
}