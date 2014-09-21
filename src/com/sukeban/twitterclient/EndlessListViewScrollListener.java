package com.sukeban.twitterclient;

import android.util.Log;

import com.sukeban.twitterclient.activities.HomeActivity;


public class EndlessListViewScrollListener extends EndlessScrollListener {

private HomeActivity homeActivity;
	
	public void setHomeActivity(HomeActivity activity) {
		this.homeActivity = activity;
	}

	@Override
	public void onLoadMore(int page, int totalItemsCount) {
		this.homeActivity.getMore();		
		Log.d("DEBUG","here");
	}

}
