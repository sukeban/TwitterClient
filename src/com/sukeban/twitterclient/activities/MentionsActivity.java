package com.sukeban.twitterclient.activities;

import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.fragments.MentionsFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MentionsActivity extends FragmentActivity {
	
	private MentionsFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentions);  
        fragment = (MentionsFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_mentions);   
	}
	
	public void onImageClick(View v){
    	fragment.onImageClick(v);
    }
}
