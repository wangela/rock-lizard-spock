package com.codepath.wangela.apps.rocklizardspock.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.facebook.AppLinkData;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
	    AppLinkData appLinkData = AppLinkData.createFromActivity(this);
	    if (appLinkData != null) {
	        Bundle arguments = appLinkData.getArgumentBundle();
	        if (arguments != null) {
	            String targetUrl = arguments.getString("target_url");
	            if (targetUrl != null) {
	              Log.i("Activity", "Target URL: " + targetUrl);
	            }
	        }
	    }

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miAbout:
                Intent intent = new Intent(this, RulesActivity.class);
                startActivity(intent);
    			overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    	
	public void onOnePlayer(View v) {
		Intent i = new Intent(this, OnePActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	public void onTwoPlayer(View v) {
		Intent i = new Intent(this, TwoPPassActivity.class);
		// Intent i = new Intent(this, TwoPBluetoothActivity.class);
		// Intent i = new Intent(this, TwoPWifiActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
