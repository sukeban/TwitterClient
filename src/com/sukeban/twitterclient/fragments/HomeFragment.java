package com.sukeban.twitterclient.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.RequestListener;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.models.Tweet;

public class HomeFragment extends TweetsListFragment implements RequestListener {
	
	private ProgressBar progressBar;

	private long maxId;
	private TwitterClient client;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            
        client = TwitterApplication.getRestClient();
        maxId = 0;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.progressBar = (ProgressBar)getActivity().findViewById(R.id.progressBar);
        populateTimeline(this, true);
	}
	
	public void getMore() {
		populateTimeline(this, false);
	}
	
	public void populateTimeline(final RequestListener listener, final boolean clear) {

		this.progressBar.setVisibility(ProgressBar.VISIBLE);
					
		client.getHomeFeed(maxId, new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s){
				
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
				
				listener.requestFinished();
			}
				
			@Override
			public void onSuccess(JSONArray jsonArray){
				Log.d("debug", jsonArray.toString());	
				if (clear == true)
				{
					clear();
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
				addAll(Tweet.fromJson(jsonArray));
					
				listener.requestFinished();
			}
		});
	}
	
	@Override
	public void requestFinished(){
		this.progressBar.setVisibility(ProgressBar.INVISIBLE);
	}
}
