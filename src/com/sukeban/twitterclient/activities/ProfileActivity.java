package com.sukeban.twitterclient.activities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import com.sukeban.twitterclient.EndlessListViewScrollListener;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.baseclasses.TimelineActivity;
import com.sukeban.twitterclient.fragments.TweetsListFragment;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.widget.TextView;

public class ProfileActivity extends TimelineActivity {
	
	private User user;
	
	private long maxId;
	private TwitterClient client;
	
	private EndlessListViewScrollListener listener;
	private TweetsListFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);	
		
		user = (User)getIntent().getSerializableExtra("user");

        setupViews();

        listener = new EndlessListViewScrollListener();
        listener.setActivity(this);
        fragment.setOnScrollListener(listener);
                
        maxId = 0;
        client = TwitterApplication.getRestClient();
        populateTimeline(true);
	}
	
	private void setupViews() {
		
        fragment = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_profile);   
		
		SmartImageView ivUserAvatar = (SmartImageView)findViewById(R.id.ivUserProfileAvatar);
		String avatarUrl = user.getProfileImageUrl();
		ivUserAvatar.setImageUrl(avatarUrl);
		
		TextView tvUserName = (TextView)findViewById(R.id.tvUserProfileName);
		String userName = user.getName();
		tvUserName.setText(userName);	
		
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		String followers = "" + user.getFollowersCount();
		tvFollowers.setText(followers);
		
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		String following = "" + user.getFriendsCount();
		tvFollowing.setText(following);
		
		TextView tvTagLine = (TextView)findViewById(R.id.tvTagLine);
		String tagLine = user.getTagLine();
		tvTagLine.setText(tagLine);
	}
	
	public void getMore() {
    	this.populateTimeline(false);
    }
	
	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void populateTimeline(final boolean clear) {
		
		client.getUserTimeline(maxId, user.getId(), new JsonHttpResponseHandler(){
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
}
