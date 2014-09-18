package com.sukeban.twitterclient.adapters;

import java.util.List;

import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.models.Tweet;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetAdapter extends ArrayAdapter<Tweet> {
	
	public TweetAdapter(Context context, List<Tweet> tweets) {

		// TODO: initialize the image loader
		super(context,R.layout.item_tweet,tweets);

	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		Tweet tweetInfo = getItem(position);
		if (convertView == null){
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
		}
				
		ImageView ivUserAvatar = (ImageView)convertView.findViewById(R.id.ivUserAvatar);
		TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
		TextView tvTweetDate = (TextView)convertView.findViewById(R.id.tvTweetDate);
		TextView tvTweetContent = (TextView)convertView.findViewById(R.id.tvTweetContent);
		
		tvUserName.setText(Html.fromHtml(tweetInfo.getUser().getName()));
		tvTweetContent.setText(Html.fromHtml(tweetInfo.getBody())); // TODO: this should render as html

		return convertView;
	}
}
