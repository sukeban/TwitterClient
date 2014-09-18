package com.sukeban.twitterclient.adapters;

import java.util.Date;
import java.util.List;

import com.loopj.android.image.SmartImageView;
import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.models.Tweet;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.text.format.DateUtils;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	
	public TweetAdapter(Context context, List<Tweet> tweets) {
		super(context,R.layout.item_tweet,tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Tweet tweetInfo = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
		}
				
		SmartImageView ivUserAvatar = (SmartImageView)convertView.findViewById(R.id.ivUserAvatar);
		ivUserAvatar.setImageUrl(tweetInfo.getUser().getProfileImageUrl());

		TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
		tvUserName.setText(tweetInfo.getUser().getName());

		TextView tvTweetDate = (TextView)convertView.findViewById(R.id.tvTweetDate);
		Date date = tweetInfo.getDateCreated();
		tvTweetDate.setText(DateUtils.getRelativeTimeSpanString(date.getTime(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, 0));
		
		TextView tvTweetContent = (TextView)convertView.findViewById(R.id.tvTweetContent);	
		tvTweetContent.setText(Html.fromHtml(tweetInfo.getBody()));
		
		return convertView;
	}
}
