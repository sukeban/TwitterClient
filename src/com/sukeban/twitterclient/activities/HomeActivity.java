package com.sukeban.twitterclient.activities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.EndlessListViewScrollListener;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.baseclasses.TimelineActivity;
import com.sukeban.twitterclient.fragments.TweetsListFragment;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends TimelineActivity {
	
	private User loggedInUser;

	private Context toastContext;
	
	private long maxId;
	private TwitterClient client;

	private EndlessListViewScrollListener listener;
	private TweetsListFragment fragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        toastContext = this;

        fragment = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_home);   

        listener = new EndlessListViewScrollListener();
        listener.setActivity(this);
        fragment.setOnScrollListener(listener);
        
        client = TwitterApplication.getRestClient();
        maxId = 0;
        getUserInfo();
        populateTimeline(true);      
    }
	
	
	public void getMore() {
	   this.populateTimeline(false);
	}
	
	// TODO: add a indetermininte progress view around each client call then stop on success or failure

	public void populateTimeline(final boolean clear) {
		
		client.getHomeFeed(maxId, new JsonHttpResponseHandler(){
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
					fragment.clear();
					maxId = 0;
				}	
				
				int length = jsonArray.length();
				JSONObject lastObject = null;
				try{
					lastObject = jsonArray.getJSONObject(length-1);
				} catch (JSONException e){
        			e.printStackTrace();
				}
				if (lastObject != null){
					Tweet last = Tweet.fromJson(lastObject);
					maxId = last.getId();
				}
				fragment.addAll(Tweet.fromJson(jsonArray));
				
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
    	return true;
	} 
    
    public void onAddItem(MenuItem m) {
    	Intent i = new Intent(this, ComposeActivity.class);
    	
    	// TODO: this could take a user also, but this is such a small aomut of info
    	
    	i.putExtra("avatarUrl", loggedInUser.getProfileImageUrl());
    	i.putExtra("name", loggedInUser.getName());
    	startActivityForResult(i,5);// make 5 a constant declaration    	
    }
    
    public void onViewProfile(MenuItem m) {
    	this.launchProfile(loggedInUser); 	
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
    	    			fragment.insert(tweet,0);
    				}
    			}, status);
    		}
    	}
    }
    
    public void launchProfile(User user){
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", user);
    	startActivityForResult(i,10);// make 10 a constant declaration  
    }
    
    public void onImageClick(View v){
		Tweet tweet = fragment.getTweet((Integer)v.getTag());
		User user = tweet.getUser();
		this.launchProfile(user);
    }
}
