package com.codepath.wangela.apps.rocklizardspock.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class TwoPWinActivity extends WinActivity {
	private String myWeapon;
	private String opponentWeapon;
	private String playMode;
	private String outcome;
	private String winRule;
	private TextView tvOutcome;
	private TextView tvWinRule;
	private int nextImage;
	private ImageView ivOutcome;
	private UiLifecycleHelper uiHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_pwin);
		myWeapon = getIntent().getStringExtra("myWeapon");
		opponentWeapon = getIntent().getStringExtra("opponentWeapon");
		playMode = getIntent().getStringExtra("playMode");
		pickWinner();
		setupViews();

		uiHelper = new UiLifecycleHelper(this, null);
		uiHelper.onCreate(savedInstanceState);
	}

	private void pickWinner() {
		switch (myWeapon) {
		case "SPOCK":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "tie";
				winRule = "SPOCK matches SPOCK";
				nextImage = R.drawable.choose;
				break;
			case "LIZARD":
				outcome = "lose";
				winRule = "LIZARD poisons SPOCK";
				nextImage = R.drawable.lose_spock_lizard;
				break;
			case "PAPER":
				outcome = "lose";
				winRule = "PAPER disproves SPOCK";
				nextImage = R.drawable.lose_spock_paper;
				break;
			case "ROCK":
				outcome = "win";
				winRule = "SPOCK vaporizes ROCK";
				nextImage = R.drawable.win_spock_rock;
				break;
			case "SCISSORS":
				outcome = "win";
				winRule = "SPOCK smashes SCISSORS";
				nextImage = R.drawable.win_spock_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "LIZARD":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "win";
				winRule = "LIZARD poisons SPOCK";
				nextImage = R.drawable.win_lizard_spock;
				break;
			case "LIZARD":
				outcome = "tie";
				winRule = "LIZARD matches LIZARD";
				nextImage = R.drawable.choose;
				break;
			case "PAPER":
				outcome = "win";
				winRule = "LIZARD eats PAPER";
				nextImage = R.drawable.win_lizard_paper;
				break;
			case "ROCK":
				outcome = "lose";
				winRule = "ROCK crushes LIZARD";
				nextImage = R.drawable.lose_lizard_rock;
				break;
			case "SCISSORS":
				outcome = "lose";
				winRule = "SCISSORS decapitate LIZARD";
				nextImage = R.drawable.lose_lizard_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "ROCK":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "lose";
				winRule = "SPOCK vaporizes ROCK";
				nextImage = R.drawable.lose_rock_spock;
				break;
			case "LIZARD":
				outcome = "win";
				winRule = "ROCK crushes LIZARD";
				nextImage = R.drawable.win_rock_lizard;
				break;
			case "PAPER":
				outcome = "lose";
				winRule = "PAPER covers ROCK";
				nextImage = R.drawable.lose_rock_paper;
				break;
			case "ROCK":
				outcome = "tie";
				winRule = "ROCK matches ROCK";
				nextImage = R.drawable.choose;
				break;
			case "SCISSORS":
				outcome = "win";
				winRule = "ROCK crushes SCISSORS";
				nextImage = R.drawable.win_rock_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "PAPER":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "win";
				winRule = "PAPER disproves SPOCK";
				nextImage = R.drawable.win_paper_spock;
				break;
			case "LIZARD":
				outcome = "lose";
				winRule = "LIZARD eats PAPER";
				nextImage = R.drawable.lose_paper_lizard;
				break;
			case "PAPER":
				outcome = "tie";
				winRule = "PAPER matches PAPER";
				nextImage = R.drawable.choose;
				break;
			case "ROCK":
				outcome = "win";
				winRule = "PAPER covers ROCK";
				nextImage = R.drawable.win_paper_rock;
				break;
			case "SCISSORS":
				outcome = "lose";
				winRule = "SCISSORS cut PAPER";
				nextImage = R.drawable.lose_paper_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "SCISSORS":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "lose";
				winRule = "SPOCK smashes SCISSORS";
				nextImage = R.drawable.lose_scissors_spock;
				break;
			case "LIZARD":
				outcome = "win";
				winRule = "SCISSORS decapitate LIZARD";
				nextImage = R.drawable.win_scissors_lizard;
				break;
			case "PAPER":
				outcome = "win";
				winRule = "SCISSORS cut PAPER";
				nextImage = R.drawable.win_scissors_paper;
				break;
			case "ROCK":
				outcome = "lose";
				winRule = "ROCK crushes SCISSORS";
				nextImage = R.drawable.lose_scissors_rock;
				break;
			case "SCISSORS":
				outcome = "tie";
				winRule = "SCISSORS matches SCISSORS";
				nextImage = R.drawable.choose;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		default:
			outcome = "tie";
			winRule = "NOTHING matches NOTHING";
			nextImage = R.drawable.choose;
			break;
		}

	}

	private void setupViews() {
		tvOutcome = (TextView) findViewById(R.id.tvTOutcome);
		switch (outcome) {
		case "win":
			outcome = "Player 1 wins!";
			break;
		case "lose":
			outcome = "Player 2 wins!";
			break;
		case "tie":
			outcome = "It's a draw.";
			break;
		default:
			outcome = "This shouldn't have happened...";
			break;
		}
		tvOutcome.setText(outcome);

		tvWinRule = (TextView) findViewById(R.id.tvTWinRule);
		tvWinRule.setText(winRule);

		ivOutcome = (ImageView) findViewById(R.id.ivTWinImage);
		ivOutcome.setImageResource(nextImage);
		ivOutcome.setVisibility(ImageView.VISIBLE);
	}

	@Override
	public void onAgain(View v) {
		ivOutcome.setVisibility(ImageView.INVISIBLE);
		switch (playMode) {
		case "pass":
			Intent i = new Intent(this, TwoPPassActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
		default:
			finish();
		}

	}

	public void onShare(View v) {

		// start Facebook Login
		Session.openActiveSession(this, true, new Session.StatusCallback() {

			// callback when session changes state
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (session.isOpened()) {

					// make request to the /me API
					Request.newMeRequest(session,
							new Request.GraphUserCallback() {

								// callback after Graph API response with user
								// object
								@Override
								public void onCompleted(GraphUser user,
										Response response) {
									if (user != null) {
										TextView welcome = (TextView) findViewById(R.id.welcome);
										welcome.setText("Hello "
												+ user.getName() + "!");
										Toast.makeText(
												getApplicationContext(),
												"Hello " + user.getName() + "!",
												Toast.LENGTH_SHORT).show();
									}
								}
							}).executeAsync();
				}
			}
		});

		if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					this)
					.setLink("https://developers.facebook.com/android")
					.setName("Rock Paper Scissors Lizard Spock")
					.setCaption("The app for Android devices")
					.setDescription(
							"This game popularized by television show The Big Bang Theory reduces the chances of a tie by adding two new weapons to the classic game of Rock Paper Scissors.")
					.setPicture(
							"https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xfp1/t39.2081-0/p128x128/10173502_275529322631924_1885431941_n.png")
					.build();
			uiHelper.trackPendingDialogCall(shareDialog.present());

		} else {
			// Fallback. For example, publish the post using the Feed Dialog
			publishFeedDialog();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						Log.e("Activity",
								String.format("Error: %s", error.toString()));
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Toast.makeText(getApplicationContext(), "Posted!",
								Toast.LENGTH_LONG).show();
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", "Rock Paper Scissors Lizard Spock - the App");
		params.putString("caption", "The app for Android devices");
		params.putString(
				"description",
				"This game popularized by television show The Big Bang Theory reduces the chances of a tie by adding two new weapons to the classic game of Rock Paper Scissors.");
		params.putString("link", "https://developers.facebook.com/android");
		params.putString(
				"picture",
				"https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xfp1/t39.2081-0/p128x128/10173502_275529322631924_1885431941_n.png");

		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
				getApplicationContext(), Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {
						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							if (postId != null) {
								Toast.makeText(getApplicationContext(),
										"Posted story, id: " + postId,
										Toast.LENGTH_SHORT).show();
							} else {
								// User clicked the Cancel button
								Toast.makeText(getApplicationContext(),
										"Publish cancelled", Toast.LENGTH_SHORT)
										.show();
							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Toast.makeText(getApplicationContext(),
									"Publish cancelled", Toast.LENGTH_SHORT)
									.show();
						} else {
							// Generic, ex: network error
							Toast.makeText(getApplicationContext(),
									"Error posting story", Toast.LENGTH_SHORT)
									.show();
						}
					}

				}).build();
		feedDialog.show();
	}
}
