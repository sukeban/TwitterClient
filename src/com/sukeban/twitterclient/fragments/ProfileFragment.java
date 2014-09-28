package com.sukeban.twitterclient.fragments;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.sukeban.twitterclient.TwitterApplication;
import com.sukeban.twitterclient.TwitterClient;
import com.sukeban.twitterclient.models.Tweet;
import com.sukeban.twitterclient.models.User;

public class ProfileFragment extends TweetsListFragment {

	private ProgressBar progressBar;
	
	private User user;	
	private long maxId;
	private TwitterClient client;	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = null;

        client = TwitterApplication.getRestClient();
        maxId = 0;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		//this.progressShowingActivity = (ProgressFragmentActivity)getActivity();
		return v;
	}
	
	public void setUser(User user){
		this.user = user;
        populateTimeline(true);
	}
	
	public void getMore() {
		this.populateTimeline(false);
	}

	public void populateTimeline(final boolean clear) {
		
		if (user == null) return;
		
		//progressShowingActivity.showProgressBar();

		client.getUserTimeline(maxId, user.getId(), new JsonHttpResponseHandler(){
			@Override
			public void onFailure(Throwable e, String s){
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
				//progressShowingActivity.hideProgressBar();
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
				//progressShowingActivity.hideProgressBar();
			}
		});
	}
}
