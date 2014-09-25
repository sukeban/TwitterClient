package com.sukeban.twitterclient;

import android.util.Log;
import com.sukeban.twitterclient.activities.TimelineActivity;


public class EndlessListViewScrollListener extends EndlessScrollListener {

	private TimelineActivity activity;
	
	public void setActivity(TimelineActivity activity) {
		this.activity = activity;
	}

	@Override
	public void onLoadMore(int page, int totalItemsCount) {
		this.activity.getMore();		
		Log.d("DEBUG","here");
	}

}
