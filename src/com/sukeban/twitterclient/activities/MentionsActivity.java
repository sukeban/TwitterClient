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

import android.os.Bundle;
import android.util.Log;

public class MentionsActivity extends TimelineActivity {
	
	private long maxId;
	private TwitterClient client;
	
	private TweetsListFragment fragment;
	private EndlessListViewScrollListener listener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mentions);
	        
        fragment = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_mentions);   

        listener = new EndlessListViewScrollListener();
        listener.setActivity(this);
        fragment.setOnScrollListener(listener);
	        
	    maxId = 0;
		client = TwitterApplication.getRestClient();
	    getMentions(true);
	}
	
	public void getMore() {
	   this.getMentions(false);
	}
	
	public void getMentions(final boolean clear) {
		
		client.getMentions(maxId, new JsonHttpResponseHandler(){
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
