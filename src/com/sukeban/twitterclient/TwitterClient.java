package com.sukeban.twitterclient;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1/"; 
	public static final String REST_CONSUMER_KEY = "sqEL4M9Oxfu1voXeQ52Zgl1KT";
	public static final String REST_CONSUMER_SECRET = "5graH0XE7FECqawNxoyPyw3lDo0ovfGY0g4yFItrZPIjdkmgH8"; 
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets";
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeFeed(long maxId, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		RequestParams params = new RequestParams();
		params.put("format", "json");
		if (maxId !=0){
			String maxIDString = "" + maxId;
			params.put("max_id",maxIDString);
		}
		client.get(apiUrl, params, handler);
	}
	
	public void postTweet(AsyncHttpResponseHandler handler, String status){
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("format", "json");
		params.put("status", status);
		client.post(apiUrl, params, handler);
	}
	
	public void getLoggedInUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}
	
	public void getUserTimeline(long maxId, long userId,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		RequestParams params = new RequestParams();
		params.put("format", "json");
		String userIDString = "" + userId;
		params.put("user_id",userIDString);
		if (maxId !=0){
			String maxIDString = "" + maxId;
			params.put("max_id",maxIDString);
		}
		client.get(apiUrl, params, handler);
	}
	
	public void getMentions(long maxId, AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		RequestParams params = new RequestParams();
		params.put("format", "json");
		if (maxId !=0){
			String maxIDString = "" + maxId;
			params.put("max_id",maxIDString);
		}
		client.get(apiUrl, params, handler);
	}
}