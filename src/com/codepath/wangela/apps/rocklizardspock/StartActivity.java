package com.codepath.wangela.apps.rocklizardspock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	}
	
	public void onOnePlayer(View v) {
		Intent i = new Intent(this, OnePActivity.class);
		startActivity(i);
	}
}
