package com.sukeban.twitterclient.fragments;

import java.util.ArrayList;

import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.activities.ProfileActivity;
import com.sukeban.twitterclient.adapters.TweetAdapter;
import com.sukeban.twitterclient.baseclasses.EndlessListViewFragment;
import com.sukeban.twitterclient.listeners.EndlessListViewScrollListener;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TweetsListFragment extends EndlessListViewFragment {

	private ListView lvTweets;
	private ArrayList<Tweet> tweets;
	private TweetAdapter tweetAdapter;
	private EndlessListViewScrollListener listener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	tweets = new ArrayList<Tweet>();
        tweetAdapter = new TweetAdapter(getActivity(), tweets);

        listener = new EndlessListViewScrollListener();
        listener.setFragment(this);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		lvTweets = (ListView)v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetAdapter);
		lvTweets.setOnScrollListener(listener);
        return v;
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
	
    public void launchProfile(User user){
		Intent i = new Intent(getActivity(), ProfileActivity.class);
		i.putExtra("user", user);
    	startActivityForResult(i,10);// make 10 a constant declaration  
    }
    
    public void onImageClick(View v){
		Tweet tweet = getTweet((Integer)v.getTag());
		User user = tweet.getUser();
		this.launchProfile(user);
    }
}
