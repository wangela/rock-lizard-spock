package com.codepath.wangela.apps.rocklizardspock.activities;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.R.drawable;
import com.codepath.wangela.apps.rocklizardspock.R.id;
import com.codepath.wangela.apps.rocklizardspock.R.layout;
import com.codepath.wangela.apps.rocklizardspock.R.menu;
import com.codepath.wangela.apps.rocklizardspock.helpers.ColorTool;
import com.codepath.wangela.apps.rocklizardspock.models.Weapon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OnePActivity extends Activity {
	private ImageView ivGray;
	private ImageView iv;
	private int nextImage;
	private String myWeapon;
	private Button btnFight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onep);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	private void setupViews() {
		btnFight = (Button) findViewById(R.id.btnFight);
		iv = (ImageView) findViewById(R.id.ivArrows);
		ivGray = (ImageView) findViewById(R.id.ivChooseGray);
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
					int touchColor = getHotspotColor(R.id.ivHotspots, evX, evY);
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
				btnFight.setVisibility(Button.VISIBLE);
				iv.setVisibility(ImageView.VISIBLE);
				return true;
			}
		});
	}

	public int getHotspotColor(int hotspotId, int x, int y) {
		ImageView img = (ImageView) findViewById(hotspotId);
		img.setDrawingCacheEnabled(true);
		Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
		img.setDrawingCacheEnabled(false);
		return hotspots.getPixel(x, y);
	}

	public void onFight(View v) {
		Weapon opponentWeapon = Weapon.pickWeapon();
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) findViewById(R.id.llToast));

		TextView text = (TextView) layout.findViewById(R.id.tvToast);
		text.setText("You choose " + myWeapon + ". \nAndroid chooses "
				+ opponentWeapon.toString() + ".");

		Toast toast = new Toast(getApplicationContext());
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();

		Intent i = new Intent(getApplicationContext(), WinActivity.class);
		i.putExtra("myWeapon", myWeapon);
		i.putExtra("opponentWeapon", opponentWeapon.toString());
		startActivity(i);
		finish();
	}
}
