package com.sukeban.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.EndlessListViewScrollListener;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.adapters.TweetAdapter;
import com.sukeban.twitterclient.models.Tweet;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MentionsActivity extends TimelineActivity {
	
	private ListView lvMentions;
	private ArrayList<Tweet> mentions;
	private TweetAdapter mentionsAdapter;

	private TwitterClient client;
	
	private EndlessListViewScrollListener listener;
	private long maxId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mentions);
		
		client = TwitterApplication.getRestClient();

		mentions = new ArrayList<Tweet>();
		mentionsAdapter = new TweetAdapter(this, mentions);
	        
	    setupViews();

	    lvMentions.setAdapter(mentionsAdapter);
	    listener = new EndlessListViewScrollListener();
	    listener.setActivity(this);
	    lvMentions.setOnScrollListener(listener);
	        
	    getMentions(true);
	        
	    maxId = 0;
		
	}
	
	private void setupViews() {
		lvMentions = (ListView)findViewById(R.id.lvMentions);
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
					mentionsAdapter.clear();
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
				mentionsAdapter.addAll(Tweet.fromJson(jsonArray));
				
				// TODO: show a spinner until the results come in
			}
		});
	}
}
