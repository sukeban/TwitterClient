package com.sukeban.twitterclient.activities;

import java.util.ArrayList;

import org.json.JSONArray;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.adapters.TweetAdapter;
import com.sukeban.twitterclient.models.Tweet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class HomeActivity extends Activity {

	private ListView lvHomeFeed;
//	private EndlessGridViewScrollListener listener;
	private ArrayList<Tweet> tweets;
	private TweetAdapter tweetAdapter;
//	private short pageCounter;
	
	private TwitterClient client;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        client = TwitterApplication.getRestClient();

    	tweets = new ArrayList<Tweet>();
        tweetAdapter = new TweetAdapter(this, tweets);
        
        setupViews();

        lvHomeFeed.setAdapter(tweetAdapter);
        
 //       listener = new EndlessGridViewScrollListener();
 //       listener.setSearchActivity(this);
 //       gvResults.setOnScrollListener(listener);
       
 //       pageCounter = 0;
        
        populateTimeline();

    }
	
	private void setupViews() {
        lvHomeFeed = (ListView)findViewById(R.id.lvHomeFeed);
	}
	
	public void populateTimeline(){
		client.getHomeFeed(new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
			
			@Override
			public void onSuccess(JSONArray jsonArray){
				Log.d("debug", jsonArray.toString());	
				tweetAdapter.addAll(Tweet.fromJson(jsonArray));
				
				// TODO: show a spinner until the results come in
			}
		});
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.composemenu, menu);
    	return true;
	} 
    
    // TODO: a refresh button on the action bar will force a reload
    
    public void onAddItem(MenuItem m) {
    	Intent i = new Intent(this, ComposeActivity.class);
    	startActivityForResult(i,5);// make it a constant
    	
    	// TODO: make a new scratch tweet
    	// pass it to the compose intent
    	// get it back when its closed to sent it up
    	
    }
}
