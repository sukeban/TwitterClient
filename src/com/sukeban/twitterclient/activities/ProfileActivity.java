package com.sukeban.twitterclient.activities;


import com.loopj.android.image.SmartImageView;

import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.fragments.ProfileFragment;
import com.sukeban.twitterclient.models.User;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.widget.TextView;

public class ProfileActivity extends FragmentActivity {
	
	private User user;	
	private ProfileFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);	
		
		user = (User)getIntent().getSerializableExtra("user");
        setupViews();
	}
	
	private void setupViews() {
		
        fragment = (ProfileFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_profile);   
		fragment.setUser(user);
		
		SmartImageView ivUserAvatar = (SmartImageView)findViewById(R.id.ivUserProfileAvatar);
		String avatarUrl = user.getProfileImageUrl();
		ivUserAvatar.setImageUrl(avatarUrl);
		
		TextView tvUserName = (TextView)findViewById(R.id.tvUserProfileName);
		String userName = user.getName();
		tvUserName.setText(userName);	
		
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		String followers = "" + user.getFollowersCount();
		tvFollowers.setText(followers);
		
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		String following = "" + user.getFriendsCount();
		tvFollowing.setText(following);
		
		TextView tvTagLine = (TextView)findViewById(R.id.tvTagLine);
		String tagLine = user.getTagLine();
		tvTagLine.setText(tagLine);
	}
}
