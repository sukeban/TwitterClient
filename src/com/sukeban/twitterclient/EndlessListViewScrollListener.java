package com.sukeban.twitterclient;

import com.sukeban.twitterclient.baseclasses.EndlessListViewFragment;
import com.sukeban.twitterclient.fragments.TweetsListFragment;

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
