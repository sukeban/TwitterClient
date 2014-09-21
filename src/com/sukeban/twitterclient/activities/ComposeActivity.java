package com.sukeban.twitterclient.activities;

import com.loopj.android.image.SmartImageView;
import com.sukeban.twitterclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class ComposeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
				
		SmartImageView ivUserAvatar = (SmartImageView)findViewById(R.id.ivUserAvatar);
		String avatarUrl = getIntent().getStringExtra("avatarUrl");
		ivUserAvatar.setImageUrl(avatarUrl);
		
		TextView tvUserName = (TextView)findViewById(R.id.tvUserName);
		String userName = getIntent().getStringExtra("name");
		tvUserName.setText(userName);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.tweetmenu, menu);
		return true;
	} 
	    
	public void onTweet(MenuItem m) {		
		EditText statusValue = (EditText)findViewById(R.id.etTweetContent);
		String status = statusValue.getText().toString();
		
		Intent i = new Intent();
		i.putExtra("status", status);
		setResult(RESULT_OK,i);
		finish();
	}
}
