package com.codepath.wangela.apps.rocklizardspock.activities;

import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.models.OpenGraphUtils.RLSAction;
import com.codepath.wangela.apps.rocklizardspock.models.OpenGraphUtils.RLSWeapon;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class TwoPWinActivity extends WinActivity {
	private String myWeapon;
	private String opponentWeapon;
	private String playMode;
	private String outcome;
	private String winRule;
	private String winVerb;
	private TextView tvOutcome;
	private TextView tvWinRule;
	private ProgressBar pbShare;
	private int nextImage;
	private ImageView ivOutcome;
	private UiLifecycleHelper uiHelper;
	private Activity myActivity;

	// Listen for session opening and then check for permissions
	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
	    public void call(Session session, SessionState state, Exception exception) {
	    	if (session.isOpened()) {
	    		if (session.getPermissions().contains("publish_actions")) {
				composeSharePost();
				} else {
				requestSharePermissions(session);
				}
	    	}
	    }
	}

	// Listen for changes to session state, to aid uiHelper
	private Session.StatusCallback uiCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};
	
	// Listen for when publish permissions are granted
	private Session.StatusCallback statusCallback = new SessionStatusCallback() {
		// TODO Handle when session opens
	};
	
	private Session.StatusCallback newPermissionsCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (exception != null && !session.isOpened()
					&& session.getPermissions().contains("publish_actions")) {
				composeSharePost();
			}
		}
	};

	// public void onLogin(View v) {
	//
	// try {
	// PackageInfo info = getPackageManager().getPackageInfo(
	// "com.codepath.wangela.apps.rocklizardspock",
	// PackageManager.GET_SIGNATURES);
	// for (Signature signature : info.signatures) {
	// MessageDigest md = MessageDigest.getInstance("SHA");
	// md.update(signature.toByteArray());
	// TextView welcome = (TextView) findViewById(R.id.welcome);
	// welcome.setText("KeyHash:"
	// + Base64.encodeToString(md.digest(), Base64.DEFAULT));
	// }
	// } catch (NameNotFoundException e) {
	// Toast.makeText(myActivity, "Name not found", Toast.LENGTH_SHORT)
	// .show();
	//
	// } catch (NoSuchAlgorithmException e) {
	// Toast.makeText(myActivity, "No such algorithm", Toast.LENGTH_SHORT)
	// .show();
	//
	// }
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_pwin);
		myWeapon = getIntent().getStringExtra("myWeapon");
		opponentWeapon = getIntent().getStringExtra("opponentWeapon");
		playMode = getIntent().getStringExtra("playMode");
		pickWinner();
		setupViews();

		myActivity = this;
		
		uiHelper = new UiLifecycleHelper(this, uiCallback);
		uiHelper.onCreate(savedInstanceState);
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
		
		pbShare = (ProgressBar) findViewById(R.id.pbTShare);
	}

	private void pickWinner() {
		switch (myWeapon) {
		case "SPOCK":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "tie";
				winRule = "SPOCK matches SPOCK";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			case "LIZARD":
				outcome = "lose";
				winRule = "LIZARD poisons SPOCK";
				winVerb = "lose";
				nextImage = R.drawable.lose_spock_lizard;
				break;
			case "PAPER":
				outcome = "lose";
				winRule = "PAPER disproves SPOCK";
				winVerb = "lose";
				nextImage = R.drawable.lose_spock_paper;
				break;
			case "ROCK":
				outcome = "win";
				winRule = "SPOCK vaporizes ROCK";
				winVerb = "vaporize";
				nextImage = R.drawable.win_spock_rock;
				break;
			case "SCISSORS":
				outcome = "win";
				winRule = "SPOCK smashes SCISSORS";
				winVerb = "win";
				nextImage = R.drawable.win_spock_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				winVerb = "win";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "LIZARD":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "win";
				winRule = "LIZARD poisons SPOCK";
				winVerb = "poison";
				nextImage = R.drawable.win_lizard_spock;
				break;
			case "LIZARD":
				outcome = "tie";
				winRule = "LIZARD matches LIZARD";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			case "PAPER":
				outcome = "win";
				winRule = "LIZARD eats PAPER";
				winVerb = "win";
				nextImage = R.drawable.win_lizard_paper;
				break;
			case "ROCK":
				outcome = "lose";
				winRule = "ROCK crushes LIZARD";
				winVerb = "lose";
				nextImage = R.drawable.lose_lizard_rock;
				break;
			case "SCISSORS":
				outcome = "lose";
				winRule = "SCISSORS decapitate LIZARD";
				winVerb = "lose";
				nextImage = R.drawable.lose_lizard_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "ROCK":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "lose";
				winRule = "SPOCK vaporizes ROCK";
				winVerb = "lose";
				nextImage = R.drawable.lose_rock_spock;
				break;
			case "LIZARD":
				outcome = "win";
				winRule = "ROCK crushes LIZARD";
				winVerb = "crush";
				nextImage = R.drawable.win_rock_lizard;
				break;
			case "PAPER":
				outcome = "lose";
				winRule = "PAPER covers ROCK";
				winVerb = "lose";
				nextImage = R.drawable.lose_rock_paper;
				break;
			case "ROCK":
				outcome = "tie";
				winRule = "ROCK matches ROCK";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			case "SCISSORS":
				outcome = "win";
				winRule = "ROCK crushes SCISSORS";
				winVerb = "crush";
				nextImage = R.drawable.win_rock_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "PAPER":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "win";
				winRule = "PAPER disproves SPOCK";
				winVerb = "disprove";
				nextImage = R.drawable.win_paper_spock;
				break;
			case "LIZARD":
				outcome = "lose";
				winRule = "LIZARD eats PAPER";
				winVerb = "lose";
				nextImage = R.drawable.lose_paper_lizard;
				break;
			case "PAPER":
				outcome = "tie";
				winRule = "PAPER matches PAPER";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			case "ROCK":
				outcome = "win";
				winRule = "PAPER covers ROCK";
				winVerb = "win";
				nextImage = R.drawable.win_paper_rock;
				break;
			case "SCISSORS":
				outcome = "lose";
				winRule = "SCISSORS cut PAPER";
				winVerb = "lose";
				nextImage = R.drawable.lose_paper_scissors;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		case "SCISSORS":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "lose";
				winRule = "SPOCK smashes SCISSORS";
				winVerb = "lose";
				nextImage = R.drawable.lose_scissors_spock;
				break;
			case "LIZARD":
				outcome = "win";
				winRule = "SCISSORS decapitate LIZARD";
				winVerb = "decapitate";
				nextImage = R.drawable.win_scissors_lizard;
				break;
			case "PAPER":
				outcome = "win";
				winRule = "SCISSORS cut PAPER";
				winVerb = "win";
				nextImage = R.drawable.win_scissors_paper;
				break;
			case "ROCK":
				outcome = "lose";
				winRule = "ROCK crushes SCISSORS";
				winVerb = "lose";
				nextImage = R.drawable.lose_scissors_rock;
				break;
			case "SCISSORS":
				outcome = "tie";
				winRule = "SCISSORS matches SCISSORS";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				winVerb = "tie";
				nextImage = R.drawable.choose;
				break;
			}
			break;
		default:
			outcome = "tie";
			winRule = "NOTHING matches NOTHING";
			winVerb = "tie";
			nextImage = R.drawable.choose;
			break;
		}

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
	
	public void onShare (View v) {
		Toast.makeText(this, "Facebook posting will be live in August 2014 when we get Facebook approval. Update the app then to be able to share!", Toast.LENGTH_LONG).show();
	}

	public void onFBShare(View v) {
		pbShare.setVisibility(ProgressBar.VISIBLE);
		
		Session existingSession = Session.getActiveSession();
		// If we have a valid existing session, we'll use it; if not, open one
		// using the provided Intent
		// but do not cache the token (we don't want to use the same user
		// identity the next time the
		// app is run).
		if (existingSession == null || !existingSession.isOpened()) {
			Session.openActiveSession(myActivity, true, statusCallback);
		} // TODO Check for / request permissions if session is already open

		// /* make the API call */
		// new Request(session, "/{app-link-host-id}", null,
		// HttpMethod.GET, new Request.Callback() {
		// public void onCompleted(Response response) {
		// /* handle the result */
		// Toast.makeText(getApplicationContext(),
		// response.getRawResponse(),
		// Toast.LENGTH_LONG).show();
		// }
		// }).executeAsync();

	}

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			// Toast.makeText(this, "Logged in...", Toast.LENGTH_SHORT).show();
		} else if (state.isClosed()) {
			// Toast.makeText(this, "Logged out...", Toast.LENGTH_SHORT).show();
		}
	}

	private void requestSharePermissions(Session session) {
		Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
				this, Arrays.asList("publish_actions"))
				.setCallback(newPermissionsCallback);
		session.requestNewPublishPermissions(newPermissionsRequest);
	}

	private void composeSharePost() {
		if (FacebookDialog.canPresentShareDialog(myActivity,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					myActivity)
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

	private void composeOGSharePost() {
		if (FacebookDialog.canPresentOpenGraphActionDialog(myActivity,
				FacebookDialog.OpenGraphActionDialogFeature.OG_ACTION_DIALOG)) {
			// Publish the post using the Share Dialog
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					myActivity)
					.setLink("https://developers.facebook.com/android")
					.setName("Rock Paper Scissors Lizard Spock")
					.setCaption("The app for Android devices")
					.setDescription(
							"This game popularized by television show The Big Bang Theory reduces the chances of a tie by adding two new weapons to the classic game of Rock Paper Scissors.")
					.setPicture(
							"https://fbcdn-photos-b-a.akamaihd.net/hphotos-ak-xfp1/t39.2081-0/p128x128/10173502_275529322631924_1885431941_n.png")
					.build();

			OpenGraphAction action = GraphObject.Factory
					.create(OpenGraphAction.class);
			action.setType("decapitate");
			action.setProperty("lizard",
					"http://samples.ogp.me/276908059160717");
			OpenGraphObject object = GraphObject.Factory
					.create(OpenGraphObject.class);
			object.setType("lizard");

			FacebookDialog openDialog = new FacebookDialog.OpenGraphActionDialogBuilder(
					this, action, "lizard").build();
			uiHelper.trackPendingDialogCall(openDialog.present());
		}
		//

		if (winVerb == "lose" || winVerb == "tie" || winVerb == "win") {
			opponentWeapon = "round";
		}

		RLSWeapon rlsWeapon = createRlsWeapon(opponentWeapon);
		RLSAction rlsAction = createRlsAction(winVerb);

		FacebookDialog.OpenGraphActionDialogBuilder builder = new FacebookDialog.OpenGraphActionDialogBuilder(
				myActivity, rlsAction, rlsAction.PREVIEW_PROPERTY_NAME);

		// share the game play
		if (builder.canPresent()) {
			builder.build().present();
			//
			// } else {
			// // Fallback. For example, publish the post using the Feed Dialog
			// publishFeedDialog();
			// }
		}
	}

	private void publishFeedDialog() {
		Bundle params = new Bundle();
		params.putString("name", "Rock Paper Scissors Lizard Spock");
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

	private RLSWeapon createRlsWeapon(String type) {
		RLSWeapon weapon = OpenGraphObject.Factory.createForPost(
				RLSWeapon.class, opponentWeapon);
		weapon.setProperty("TYPE", "objects/rock-lizard-spock:" + type);
		return weapon;
	}

	private RLSAction createRlsAction(String verb) {
		RLSAction action = OpenGraphAction.Factory.createForPost(
				RLSAction.class, verb);
		action.setProperty("TYPE", "rock-lizard-spock:" + verb);
		action.setProperty("PATH", "me/" + action.TYPE);
		return action;
	}

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
		pbShare.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

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

}
