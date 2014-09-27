package com.sukeban.twitterclient.activities;


import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.fragments.HomeFragment;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {
	
	private Context toastContext;
	private User loggedInUser;
	private TwitterClient client;	
	private HomeFragment fragment;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);   
        fragment = (HomeFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_home);   
        toastContext = this;
        client = TwitterApplication.getRestClient();
        getUserInfo();
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.composemenu, menu);
    	return true;
	} 
    
    public void onAddItem(MenuItem m) {
    	Intent i = new Intent(this, ComposeActivity.class);
    	    	
    	i.putExtra("avatarUrl", loggedInUser.getProfileImageUrl()); // TODO: could send User
    	i.putExtra("name", loggedInUser.getName());
    	startActivityForResult(i,5);// make 5 a constant declaration    	
    }
    
    public void onImageClick(View v){
    	fragment.onImageClick(v);
    }
    
    public void onViewProfile(MenuItem m) {
    	this.launchProfile(loggedInUser); 	
    }
    
    public void launchProfile(User user){
		Intent i = new Intent(this, ProfileActivity.class);
		i.putExtra("user", user);
    	startActivityForResult(i,10);// make 10 a constant declaration  
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
    
    // for compose
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
}
