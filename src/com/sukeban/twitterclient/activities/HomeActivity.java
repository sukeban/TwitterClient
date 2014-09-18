package com.sukeban.twitterclient.activities;

import com.sukeban.twitterclient.R;
import com.sukeban.twitterclient.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

// TODO: make an array adapter, parse the json the way we did for the google image search

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
     // TODO: get tweets for the authorized user
     // TODO: a refresh button on the action bar will force a reload

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.composemenu, menu);
    	return true;
	} 
    
    public void onAddItem(MenuItem m) {
    	Intent i = new Intent(this, ComposeActivity.class);
    	startActivityForResult(i,5);// make it a constant
    	
    	// TODO: make a new scratch tweet
    	// pass it to the compose intent
    	// get it back when its closed to sent it up
    	
    }
}
