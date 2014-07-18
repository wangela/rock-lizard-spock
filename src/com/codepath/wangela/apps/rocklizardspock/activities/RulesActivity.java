package com.codepath.wangela.apps.rocklizardspock.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.R.drawable;
import com.codepath.wangela.apps.rocklizardspock.R.id;
import com.codepath.wangela.apps.rocklizardspock.R.layout;
import com.codepath.wangela.apps.rocklizardspock.helpers.ColorTool;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;

public class RulesActivity extends Activity {
	private ImageView ivGray;
	private ImageView iv;
	private int nextImage;
	private String myWeapon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rules);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setupViews();
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
            	finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
	private void setupViews() {

		iv = (ImageView) findViewById(R.id.ivRArrows); // tie to the Arrows
														// ImageView
		ivGray = (ImageView) findViewById(R.id.ivRChooseGray);
		ivGray.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				final int action = ev.getAction();
				// Get coordinates of touch location
				final int evX = (int) ev.getX();
				final int evY = (int) ev.getY();
				switch (action) {
				case MotionEvent.ACTION_UP:
					iv.setVisibility(ImageView.INVISIBLE);
					int touchColor = getHotspotColor(R.id.ivRHotspots, evX, evY);
					ColorTool ct = new ColorTool();
					int tolerance = 25;
					if (ct.closeMatch(Color.RED, touchColor, tolerance)) {
						// Do the action associated with the RED region
						nextImage = R.drawable.arrows_rock;
						myWeapon = "ROCK";
						break;
					} else if (ct
							.closeMatch(Color.GREEN, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_lizard;
						myWeapon = "LIZARD";
						break;
					} else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
						nextImage = R.drawable.arrows_spock;
						myWeapon = "SPOCK";
						break;
					} else if (ct.closeMatch(Color.YELLOW, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_paper;
						myWeapon = "PAPER";
						break;
					} else if (ct.closeMatch(Color.MAGENTA, touchColor,
							tolerance)) {
						nextImage = R.drawable.arrows_scissors;
						myWeapon = "SCISSORS";
						break;
					}
					nextImage = R.drawable.choose;
					break;
				} // end switch
				if (nextImage > 0) {
					iv.setImageResource(nextImage);
				}
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

}
