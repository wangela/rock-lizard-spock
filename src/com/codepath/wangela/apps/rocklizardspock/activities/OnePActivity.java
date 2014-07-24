package com.codepath.wangela.apps.rocklizardspock.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.helpers.ColorTool;
import com.codepath.wangela.apps.rocklizardspock.models.Weapon;

public class OnePActivity extends Activity {
	private RelativeLayout rlPlayspace;
	private RelativeLayout rlOpponent;
	private ImageView ivGray;
	private ImageView ivOpponentGray;
	private ImageView arrows;
	private ImageView oArrows;
	private int nextImage = 0;
	private TextView tvChoice;
	private TextView tvOpponent;
	private TextView tvChoose;
	private Button btnFight;
	private String myWeapon;
	Weapon opponentWeapon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onep);
		setupViews();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// Ignore screen rotation
		super.onConfigurationChanged(newConfig);
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
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
	
    @Override
    public void onBackPressed() {
    	finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
    

	private void setupViews() {
		rlPlayspace = (RelativeLayout) findViewById(R.id.srlPlayspace);
		rlOpponent = (RelativeLayout) findViewById(R.id.srlOpponent);
		ivGray = (ImageView) findViewById(R.id.ivChooseGray);
		ivOpponentGray = (ImageView) findViewById(R.id.ivOpponentChooseGray);
		arrows = (ImageView) findViewById(R.id.ivArrows);
		oArrows = (ImageView) findViewById(R.id.ivOpponentArrows);
		tvChoice = (TextView) findViewById(R.id.tvChoice);
		tvOpponent = (TextView) findViewById(R.id.tvOpponent);
		tvChoose = (TextView) findViewById(R.id.tvChoose);
		btnFight = (Button) findViewById(R.id.btnFight);

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
					arrows.setVisibility(ImageView.INVISIBLE);
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
					arrows.setImageResource(nextImage);
					tvChoice.setText("You choose " + myWeapon);
					tvChoice.setVisibility(TextView.VISIBLE);
				}
				arrows.setVisibility(ImageView.VISIBLE);
				btnFight.setVisibility(Button.VISIBLE);
				return true;
			}
		});
	}

	public void onFight(View v) {
		btnFight.setVisibility(Button.INVISIBLE);
		tvChoose.setVisibility(TextView.INVISIBLE);
		ivGray.setOnTouchListener(null);

		opponentWeapon = Weapon.pickWeapon();
		String oWeapon = opponentWeapon.toString();
		int oImage = R.drawable.choose_gray;

		if (oWeapon == "ROCK") {
			oImage = R.drawable.arrows_rock;
		} else if (oWeapon == "LIZARD") {
			oImage = R.drawable.arrows_lizard;
		} else if (oWeapon == "SPOCK") {
			oImage = R.drawable.arrows_spock;
		} else if (oWeapon == "PAPER") {
			oImage = R.drawable.arrows_paper;
		} else if (oWeapon == "SCISSORS") {
			oImage = R.drawable.arrows_scissors;
		}
		if (oImage > 0) {
			oArrows.setImageResource(oImage);
		}

		tvOpponent.setText("Android chooses "
				+ oWeapon);

		expandOpponent();

	}

	public void expandOpponent() {

		
		Animation animScaleLeft = AnimationUtils.loadAnimation(this, R.anim.scale_and_slide_left);
		final Animation animScaleRight = AnimationUtils.loadAnimation(this, R.anim.scale_and_slide_right);
		final AnimatorSet anim3 = new AnimatorSet();
		anim3.playTogether(
				ObjectAnimator.ofFloat(oArrows, "alpha", 1.0f).setDuration(0),
				ObjectAnimator.ofFloat(tvOpponent, "alpha", 1.0f)
						.setDuration(0),
				ObjectAnimator.ofFloat(oArrows, "rotationBy", 0.0f).setDuration(
						1000));
		anim3.setStartDelay(3000);
		

		
		anim3.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				Intent i = new Intent(getApplicationContext(),
						WinActivity.class);
				i.putExtra("myWeapon", myWeapon);
				i.putExtra("opponentWeapon", opponentWeapon.toString());
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.stay);
				finish();
			}
		});
		
		animScaleLeft.setAnimationListener(new AnimationListener() {
		    @Override
		    public void onAnimationStart(Animation animation) {
		        // Fires when animation starts
		    	rlOpponent.startAnimation(animScaleRight);
		    }
		
			@Override
			public void onAnimationEnd(Animation animation) {
				animate(ivOpponentGray).rotation(720.0f).setDuration(3000);
				anim3.start();
			}
			
		    @Override
		    public void onAnimationRepeat(Animation animation) {		
		    }
		});
		

		rlPlayspace.startAnimation(animScaleLeft);
		
//		AnimatorSet animA = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//				R.animator.property_x_to_left);
//		animA.setTarget(rlPlayspace);
//		AnimatorSet animB = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//				R.animator.property_x_to_right);
//		animB.setTarget(rlOpponent);
//		
//		AnimatorSet anim1 = new AnimatorSet();
//		anim1.playTogether(animA, animB, ObjectAnimator.ofFloat(rlOpponent, "alpha", 0.0f, 1.0f).setDuration(3000));
//
//		AnimatorSet anim3 = new AnimatorSet();
//		anim3.playTogether(
//				ObjectAnimator.ofFloat(oArrows, "alpha", 1.0f).setDuration(0),
//				ObjectAnimator.ofFloat(tvOpponent, "alpha", 1.0f)
//						.setDuration(0),
//				ObjectAnimator.ofFloat(oArrows, "rotationBy", 0.0f).setDuration(
//						1000));
//
//		AnimatorSet set = new AnimatorSet();
//		set.playSequentially(anim1,
//				ObjectAnimator.ofFloat(ivOpponentGray, "rotation", 720.0f)
//						.setDuration(3000), anim3);
//		set.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//
//				Intent i = new Intent(getApplicationContext(),
//						WinActivity.class);
//				i.putExtra("myWeapon", myWeapon);
//				i.putExtra("opponentWeapon", opponentWeapon.toString());
//				startActivity(i);
//				overridePendingTransition(R.anim.fade_in, R.anim.stay);
//				finish();
//			}
//		});
//		set.start();
	}

	public int getHotspotColor(int hotspotId, int x, int y) {
		ImageView img = (ImageView) findViewById(hotspotId);
		img.setDrawingCacheEnabled(true);
		Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
		img.setDrawingCacheEnabled(false);
		return hotspots.getPixel(x, y);
	}
}
