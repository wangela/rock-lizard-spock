package com.codepath.wangela.apps.rocklizardspock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WinActivity extends Activity {
	private String myWeapon;
	private String opponentWeapon;
	private String outcome;
	private String winRule;
	private String winTag;
	private TextView tvOutcome;
	private TextView tvWinRule;
	private ImageView ivOutcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_win);
		myWeapon = getIntent().getStringExtra("myWeapon");
		opponentWeapon = getIntent().getStringExtra("opponentWeapon");
		pickWinner();
		setupViews();
	}

	private void pickWinner() {
		switch (myWeapon) {
		case "SPOCK":
			switch (opponentWeapon) {
			case "SPOCK":
				outcome = "tie";
				winRule = "SPOCK matches SPOCK";
				break;
			case "LIZARD":
				outcome = "lose";
				winRule = "LIZARD poisons SPOCK";
				break;
			case "PAPER":
				outcome = "lose";
				winRule = "PAPER disproves SPOCK";
				break;
			case "ROCK":
				outcome = "win";
				winRule = "SPOCK vaporizes ROCK";
				break;
			case "SCISSORS":
				outcome = "win";
				winRule = "SPOCK smashes SCISSORS";
				break;
			default:
				outcome = "tie";
				winRule = "NOTHING matches NOTHING";
				break;
			}
			break;
		default:
			outcome = "tie";
			winRule = "NOTHING matches NOTHING";
			break;
		}

	}

	private void setupViews() {
		tvOutcome = (TextView) findViewById(R.id.tvOutcome);
		tvOutcome.setText("You " + outcome + "!");

		tvWinRule = (TextView) findViewById(R.id.tvWinRule);
		tvWinRule.setText(winRule);

		winTag = "iv" + myWeapon + opponentWeapon;
		int outId = getResources()
				.getIdentifier(winTag, "id", getPackageName());
		ivOutcome = (ImageView) findViewById(outId);
		ivOutcome.setVisibility(ImageView.VISIBLE);
	}

	public void onAgain(View v) {
		ivOutcome.setVisibility(ImageView.INVISIBLE);
		Intent i = new Intent(this, OnePActivity.class);
		startActivity(i);
		this.finish();
	}
}
