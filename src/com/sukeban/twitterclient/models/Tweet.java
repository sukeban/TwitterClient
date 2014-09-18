package com.sukeban.twitterclient.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Tweet {
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
    private User user;
    
    private java.util.Date dateCreated;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }
    
    public java.util.Date getDateCreated() {
    	return dateCreated;
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
        	tweet.favorited = jsonObject.getBoolean("favorited");
        	tweet.retweeted = jsonObject.getBoolean("retweeted");
        	
        	 String LARGE_TWITTER_DATE_FORMAT = "EEE MMM dd HH:mm:ss Z yyyy";
        	 
        	 SimpleDateFormat simple = new SimpleDateFormat(LARGE_TWITTER_DATE_FORMAT, Locale.ENGLISH);
        	 String dateString = jsonObject.getString("created_at");
        	 simple.setLenient(false);
        	 tweet.dateCreated = null;
        	 
        	 try {
        		 tweet.dateCreated = simple.parse(dateString);
        		 //Log.d("debug", d.toString());
        	 } catch (Exception e) {
     			e.printStackTrace();
        	 }
        	 tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
 }
