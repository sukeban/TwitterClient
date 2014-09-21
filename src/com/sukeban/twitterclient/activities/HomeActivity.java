package com.sukeban.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.EndlessListViewScrollListener;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.adapters.TweetAdapter;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private ListView lvHomeFeed;
	private ArrayList<Tweet> tweets;
	private TweetAdapter tweetAdapter;
	
	private TwitterClient client;
		
	private Context toastContext;
	
	private User loggedInUser;

	private EndlessListViewScrollListener listener;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        client = TwitterApplication.getRestClient();

    	tweets = new ArrayList<Tweet>();
        tweetAdapter = new TweetAdapter(this, tweets);
        
        toastContext = this;

        setupViews();

        lvHomeFeed.setAdapter(tweetAdapter);
        listener = new EndlessListViewScrollListener();
        listener.setHomeActivity(this);
        lvHomeFeed.setOnScrollListener(listener);
        
        getUserInfo();
        populateTimeline(true);
    }
	
	private void setupViews() {
        lvHomeFeed = (ListView)findViewById(R.id.lvHomeFeed);
	}
	
	 public void getMore() {
	    	this.populateTimeline(false);
	    }
	
	public void populateTimeline(final boolean clear) {
		client.getHomeFeed(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				Log.d("debug", jsonArray.toString());	
				if (clear == true)
				{
					tweetAdapter.clear();
				}
				tweetAdapter.addAll(Tweet.fromJson(jsonArray));
				
				// TODO: show a spinner until the results come in
			}
		});
	}
	
	public void getUserInfo() {
		client.getLoggedInUserInfo(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
			
			@Override
			public void onSuccess(JSONObject jsonObject){
				Log.d("debug", jsonObject.toString());	
				loggedInUser = User.fromJson(jsonObject);
			}
		});
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.composemenu, menu);
        // TODO: a refresh button on the action bar will force a reload
    	return true;
	} 
    
    
    public void onAddItem(MenuItem m) {
    	Intent i = new Intent(this, ComposeActivity.class);
    	i.putExtra("avatarUrl", loggedInUser.getProfileImageUrl());
    	i.putExtra("name", loggedInUser.getName());
    	startActivityForResult(i,5);// make 5 a constant declaration    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (requestCode == 5){
    		if (resultCode == RESULT_OK){
    			
    			String status = data.getStringExtra("status");
    			    			
    			client.postTweet(new JsonHttpResponseHandler(){
    				@Override
    				public void onFailure(Throwable e, String s){
    					Log.d("debug", e.toString());
    					Log.d("debug", s.toString());
    					
    	    			Toast.makeText(toastContext, "Tweet failed!", Toast.LENGTH_SHORT).show();
    				}
    				
    				@Override
    				public void onSuccess(JSONObject jsonObject){
    					Log.d("debug", jsonObject.toString());
    	    			Toast.makeText(toastContext, "Tweet posted!", Toast.LENGTH_SHORT).show();
    	    			Tweet tweet = Tweet.fromJson(jsonObject);
    	    			tweetAdapter.insert(tweet,0);
    				}
    			}, status);
    		}
    	}
    }
}
