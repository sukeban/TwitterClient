package com.sukeban.twitterclient.fragments;

import java.util.ArrayList;

import com.sukeban.twitterclient.EndlessListViewScrollListener;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.adapters.TweetAdapter;
import com.sukeban.twitterclient.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetsListFragment extends Fragment {

	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetAdapter tweetAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	tweets = new ArrayList<Tweet>();
        tweetAdapter = new TweetAdapter(getActivity(), tweets);
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView)v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);
        return v;
	}
	
	// todo setters for add all, and set listener
	public void setOnScrollListener(EndlessListViewScrollListener listener){
		lvTweets.setOnScrollListener(listener);
	}
	
	public void clear(){
		tweetAdapter.clear();
	}
	
	public void addAll(ArrayList<Tweet> tweets){
		tweetAdapter.addAll(tweets);
	}
	
	public void insert(Tweet tweet, int index){
		tweetAdapter.insert(tweet,0);
	}
	
	public Tweet getTweet(int index){
		return tweets.get(index);
	}
	
}
