package com.sukeban.twitterclient.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.models.Tweet;

public class MentionsFragment extends TweetsListFragment {

	private long maxId;
	private TwitterClient client;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            
        client = TwitterApplication.getRestClient();
        maxId = 0;
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
				
				// TODO: show a spinner until the results come in
			}
		});
	}
}
