package com.codepath.wangela.apps.rocklizardspock.activities;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.R.drawable;
import com.codepath.wangela.apps.rocklizardspock.R.id;
import com.codepath.wangela.apps.rocklizardspock.R.layout;
import com.codepath.wangela.apps.rocklizardspock.R.menu;
import com.codepath.wangela.apps.rocklizardspock.R.string;
import com.codepath.wangela.apps.rocklizardspock.helpers.ColorTool;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TwoPPassActivity extends OnePActivity {
	private ImageView ivGray;
	private ImageView iv;
	private LinearLayout llBackground;
	private int nextImage;
	private String myWeapon;
	private String opponentWeapon;
	private TextView tvPass;
	private Button btnChoose;
	private Button btnPass;
	private Button btnFight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_ppass);
		setupViews();
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.miHome:
                Intent i = new Intent(this, StartActivity.class);
                startActivity(i);
                finish();
                return true;
            case R.id.miRules:
                Intent intent = new Intent(this, RulesActivity.class);
                startActivity(intent);
    			overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	private void setupViews() {
		btnChoose = (Button) findViewById(R.id.btnPChoose);
		btnFight = (Button) findViewById(R.id.btnPFight);
		tvPass = (TextView) findViewById(R.id.tvPChoose);
		llBackground = (LinearLayout) findViewById(R.id.llPBackground);
		iv = (ImageView) findViewById(R.id.ivPArrows);
		ivGray = (ImageView) findViewById(R.id.ivPChooseGray);
		ivGray.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				final int action = ev.getAction();
				// Get coordinates of touch location
				final int evX = (int) ev.getX();
				final int evY = (int) ev.getY();
				switch (action) {
				case MotionEvent.ACTION_UP:
					// On the UP, we do the click action.
					// The hidden image (hotspots.png) has five different
					// hotspots on it.
					// The colors are red, green, blue, magenta, and yellow.
					iv.setVisibility(ImageView.INVISIBLE);
					int touchColor = getHotspotColor(R.id.ivPHotspots, evX, evY);
					// Compare the touchColor to the expected values.
					// Switch to a different image, depending on what color was
					// touched.
					// Note that we use a Color Tool object to test whether the
					// observed color is close enough to the real color to
					// count as a match. We do this because colors on the screen
					// do
					// not match the map exactly because of scaling and
					// varying pixel density.
					ColorTool ct = new ColorTool();
					int tolerance = 25;
					if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
						// Do the action associated with the RED region
						nextImage = R.drawable.arrows_rock;
						myWeapon = "ROCK";
					} else if (ct
							.closeMatch(Color.GREEN, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_lizard;
						myWeapon = "LIZARD";
					} else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_spock;
						myWeapon = "SPOCK";
					} else if (ct.closeMatch(Color.YELLOW, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_paper;
						myWeapon = "PAPER";
					} else if (ct.closeMatch(Color.MAGENTA, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_scissors;
						myWeapon = "SCISSORS";
					}
					break;
				} // end switch
				if (nextImage > 0) {
					iv.setImageResource(nextImage);
				}
				btnChoose.setVisibility(Button.VISIBLE);
				iv.setVisibility(ImageView.VISIBLE);
				return true;
			}
		});
	}

	public void onChoose(View v) {
		btnChoose.setVisibility(Button.INVISIBLE);
		llBackground.setBackgroundColor(getResources().getColor(R.color.black));
		iv.setImageResource(R.drawable.choose_gray);
		nextImage = R.drawable.choose_gray;
		ivGray.setOnTouchListener(null);
		tvPass.setText(R.string.choose_your_weaponP2);
		tvPass.setTextColor(getResources().getColor(R.color.white));
		
		ivGray.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				final int action = ev.getAction();
				// Get coordinates of touch location
				final int evX = (int) ev.getX();
				final int evY = (int) ev.getY();
				switch (action) {
				case MotionEvent.ACTION_UP:
					// On the UP, we do the click action.
					// The hidden image (hotspots.png) has five different
					// hotspots on it.
					// The colors are red, green, blue, magenta, and yellow.
					iv.setVisibility(ImageView.INVISIBLE);
					int touchColor = getHotspotColor(R.id.ivPHotspots, evX, evY);
					// Compare the touchColor to the expected values.
					// Switch to a different image, depending on what color was
					// touched.
					// Note that we use a Color Tool object to test whether the
					// observed color is close enough to the real color to
					// count as a match. We do this because colors on the screen
					// do
					// not match the map exactly because of scaling and
					// varying pixel density.
					ColorTool ct = new ColorTool();
					int tolerance = 25;
					if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
						// Do the action associated with the RED region
						nextImage = R.drawable.arrows_rock;
						opponentWeapon = "ROCK";
					} else if (ct
							.closeMatch(Color.GREEN, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_lizard;
						opponentWeapon = "LIZARD";
					} else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_spock;
						opponentWeapon = "SPOCK";
					} else if (ct.closeMatch(Color.YELLOW, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_paper;
						opponentWeapon = "PAPER";
					} else if (ct.closeMatch(Color.MAGENTA, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_scissors;
						opponentWeapon = "SCISSORS";
					}
					break;
				} // end switch
				if (nextImage > 0) {
					iv.setImageResource(nextImage);
				}
				btnFight.bringToFront();
				btnFight.setVisibility(Button.VISIBLE);
				iv.setVisibility(ImageView.VISIBLE);
				return true;
			}
		});
	}

	@Override
	public void onFight(View v) {
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
				(ViewGroup) findViewById(R.id.llToast));

		TextView text = (TextView) layout.findViewById(R.id.tvToast);
		text.setText("P1 chose " + myWeapon + ". \nP2 chose " + opponentWeapon + ".");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();

		Intent i = new Intent(getApplicationContext(), TwoPWinActivity.class);
		i.putExtra("myWeapon", myWeapon);
		i.putExtra("opponentWeapon", opponentWeapon);
		i.putExtra("playMode", "pass");
		startActivity(i);
		overridePendingTransition(R.anim.fade_in, R.anim.stay);
		finish();
	}
}
