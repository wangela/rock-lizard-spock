package com.codepath.wangela.apps.rocklizardspock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WinActivity extends Activity {
	private String myWeapon;
	private String opponentWeapon;
	private String outcome;
	private String winRule;
	// private String winTag;
	private TextView tvOutcome;
	private TextView tvWinRule;
	private int nextImage;
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
		default:
			outcome = "tie";
			winRule = "NOTHING matches NOTHING";
			nextImage = R.drawable.choose;
			break;
		}

	}

	private void setupViews() {
		tvOutcome = (TextView) findViewById(R.id.tvOutcome);
		tvOutcome.setText("You " + outcome + "!");

		tvWinRule = (TextView) findViewById(R.id.tvWinRule);
		tvWinRule.setText(winRule);

		// winTag = "iv" + myWeapon + opponentWeapon;
		// int outId = getResources().getIdentifier(winTag, "id", getPackageName());
		ivOutcome = (ImageView) findViewById(R.id.ivWinImage);
		ivOutcome.setImageResource(nextImage);
		ivOutcome.setVisibility(ImageView.VISIBLE);
	}

	public void onAgain(View v) {
		ivOutcome.setVisibility(ImageView.INVISIBLE);
		Intent i = new Intent(this, OnePActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
		finish();
	}
}
