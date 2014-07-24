package com.codepath.wangela.apps.rocklizardspock.activities;

import android.content.Intent;
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

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.helpers.ColorTool;

public class TwoPPassActivity extends OnePActivity {
	private RelativeLayout rlBackground;
	private RelativeLayout rlPlayspace;
	private RelativeLayout rlOpponent;
	private ImageView ivGray;
	private ImageView ivOpponentGray;
	private ImageView arrows;
	private ImageView oArrows;
	private int nextImage = 0;
	private int oImage = 0;
	private TextView tvChoice;
	private TextView tvOpponent;
	private TextView tvChoose;
	private Button btnChoose;
	private Button btnFight;
	private String myWeapon;
	private String opponentWeapon;
	
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

	private void setupViews() {
		rlBackground = (RelativeLayout) findViewById(R.id.rlTwoPPass);
		rlPlayspace = (RelativeLayout) findViewById(R.id.rlPPlayspace);
		rlOpponent = (RelativeLayout) findViewById(R.id.rlPOpponent);
		ivGray = (ImageView) findViewById(R.id.ivPChooseGray);
		ivOpponentGray = (ImageView) findViewById(R.id.ivPOpponentChooseGray);
		arrows = (ImageView) findViewById(R.id.ivPArrows);
		oArrows = (ImageView) findViewById(R.id.ivPOpponentArrows);
		tvChoice = (TextView) findViewById(R.id.tvPChoice);
		tvOpponent = (TextView) findViewById(R.id.tvPOpponent);
		tvChoose = (TextView) findViewById(R.id.tvPChoose);
		btnChoose = (Button) findViewById(R.id.btnPChoose);
		btnFight = (Button) findViewById(R.id.btnPFight);

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
					arrows.setImageResource(nextImage);
					tvChoice.setText("P1 chooses " + myWeapon);
				}
				tvChoice.setVisibility(TextView.VISIBLE);
				btnChoose.setVisibility(Button.VISIBLE);
				arrows.setVisibility(ImageView.VISIBLE);
				return true;
			}
		});
	}

	public void onChoose(View v) {
		btnChoose.setVisibility(Button.INVISIBLE);
		ivGray.setOnTouchListener(null);
		rlOpponent.bringToFront();
		rlOpponent.setAlpha(1.0f);
		rlBackground.setBackgroundColor(getResources().getColor(R.color.black));
		oImage = R.drawable.choose_gray;
		tvChoose.setText(R.string.choose_your_weaponP2);
		tvChoose.setTextColor(getResources().getColor(R.color.white));
		
		ivOpponentGray.setOnTouchListener(new OnTouchListener() {
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
					oArrows.setVisibility(ImageView.INVISIBLE);
					int touchColor = getHotspotColor(R.id.ivPOpponentHotspots, evX, evY);
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
						oImage = R.drawable.arrows_rock;
						opponentWeapon = "ROCK";
					} else if (ct
							.closeMatch(Color.GREEN, touchColor, tolerance)) {
						oImage = R.drawable.arrows_lizard;
						opponentWeapon = "LIZARD";
					} else if (ct.closeMatch(Color.BLUE, touchColor, tolerance)) {
						oImage = R.drawable.arrows_spock;
						opponentWeapon = "SPOCK";
					} else if (ct.closeMatch(Color.YELLOW, touchColor,
							tolerance)) {
						oImage = R.drawable.arrows_paper;
						opponentWeapon = "PAPER";
					} else if (ct.closeMatch(Color.MAGENTA, touchColor,
							tolerance)) {
						oImage = R.drawable.arrows_scissors;
						opponentWeapon = "SCISSORS";
					}
					break;
				} // end switch
				if (oImage > 0) {
					oArrows.setImageResource(oImage);
					tvOpponent.setText("P2 chooses " + opponentWeapon);
					tvOpponent.setVisibility(TextView.VISIBLE);
				}
				btnFight.bringToFront();
				btnFight.setVisibility(Button.VISIBLE);
				oArrows.setVisibility(ImageView.VISIBLE);
				return true;
			}
		});
	}

	@Override
	public void onFight(View v) {
//		LayoutInflater inflater = getLayoutInflater();
//		View layout = inflater.inflate(R.layout.toast_layout,
//				(ViewGroup) findViewById(R.id.llToast));
//
//		TextView text = (TextView) layout.findViewById(R.id.tvToast);
//		text.setText("P1 chose " + myWeapon + ". \nP2 chose " + opponentWeapon + ".");
//
//		Toast toast = new Toast(getApplicationContext());
//		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//		toast.setDuration(Toast.LENGTH_SHORT);
//		toast.setView(layout);
//		toast.show();

		expandOpponent();
	}
	
	@Override
	public void expandOpponent() {
		tvChoose.setVisibility(TextView.INVISIBLE);
		btnFight.setVisibility(Button.INVISIBLE);
		
		final Animation animScaleLeft = AnimationUtils.loadAnimation(this, R.anim.scale_and_slide_left2);
		Animation animScaleRight = AnimationUtils.loadAnimation(this,  R.anim.scale_and_slide_right2);
		
		animScaleRight.setAnimationListener(new AnimationListener() {
		    @Override
		    public void onAnimationStart(Animation animation) {
		        // Fires when animation starts
				rlPlayspace.startAnimation(animScaleLeft);
		    }
		
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent i = new Intent(getApplicationContext(),
						TwoPWinActivity.class);
				i.putExtra("myWeapon", myWeapon);
				i.putExtra("opponentWeapon", opponentWeapon);
				i.putExtra("playMode", "pass");
				startActivity(i);
				overridePendingTransition(R.anim.fade_in, R.anim.stay);
				finish();
			}
			
		    @Override
		    public void onAnimationRepeat(Animation animation) {
		       // ...			
		    }
		});
		
		rlOpponent.startAnimation(animScaleRight);
		
//		AnimatorSet animA = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//				R.animator.property_x_to_left);
//		animA.setTarget(rlPlayspace);
//		AnimatorSet animB = (AnimatorSet) AnimatorInflater.loadAnimator(this,
//				R.animator.property_x_to_right);
//		animB.setTarget(rlOpponent);
//		
//		AnimatorSet anim1 = new AnimatorSet();
//		anim1.playTogether(animA, animB, ObjectAnimator.ofFloat(rlPlayspace, "alpha", 0.0f, 1.0f).setDuration(3000));
//
//		AnimatorSet set = new AnimatorSet();
//		set.playSequentially(anim1, ObjectAnimator.ofFloat(oArrows, "rotationBy", 0.0f).setDuration(1000));
//		set.addListener(new AnimatorListenerAdapter() {
//			@Override
//			public void onAnimationEnd(Animator animation) {
//
//				Intent i = new Intent(getApplicationContext(),
//						TwoPWinActivity.class);
//				i.putExtra("myWeapon", myWeapon);
//				i.putExtra("opponentWeapon", opponentWeapon);
//				i.putExtra("playMode", "pass");
//				startActivity(i);
//				overridePendingTransition(R.anim.fade_in, R.anim.stay);
//				finish();
//			}
//		});
//		set.start();
	}

}
