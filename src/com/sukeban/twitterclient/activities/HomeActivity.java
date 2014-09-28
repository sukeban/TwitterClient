package com.sukeban.twitterclient.activities;


import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.fragments.HomeFragment;
import com.sukeban.twitterclient.fragments.MentionsFragment;
import com.sukeban.twitterclient.listeners.FragmentTabListener;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
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
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);   
        setupTabs();
        
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
    	Tab tab = getActionBar().getSelectedTab();
    	int position = tab.getPosition();
    	if (position == 0){
    		HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentByTag("first");
    		home.onImageClick(v);
    	} else {
    		MentionsFragment mentions = (MentionsFragment) getSupportFragmentManager().findFragmentByTag("second");
    		mentions.onImageClick(v);
    	}    	
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
    	        		HomeFragment home = (HomeFragment) getSupportFragmentManager().findFragmentByTag("first");
    	        		home.insert(tweet,0);
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
	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		
		Tab tab1 = actionBar
			.newTab()
			.setText("Home")
			.setIcon(R.drawable.icon_home)
			.setTag("HomeTimelineFragment")
			.setTabListener(
				(TabListener) new FragmentTabListener<HomeFragment>(R.id.flContainer, this, "first",
								HomeFragment.class));		

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
			.newTab()
			.setText("Mentions")
			.setIcon(R.drawable.icon_mentions)
			.setTag("MentionsTimelineFragment")
			.setTabListener(
			    (TabListener) new FragmentTabListener<MentionsFragment>(R.id.flContainer, this, "second",
								MentionsFragment.class));

		actionBar.addTab(tab2);
	}
    
}
