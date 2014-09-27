package com.sukeban.twitterclient;

import com.sukeban.twitterclient.baseclasses.EndlessListViewFragment;

public class EndlessListViewScrollListener extends EndlessScrollListener {

	private EndlessListViewFragment fragment;
	
	public void setFragment(EndlessListViewFragment fragment) {
		this.fragment = fragment;
	}

	@Override
	public void onLoadMore(int page, int totalItemsCount) {
		this.fragment.getMore();		
	}
}
