package com.codepath.wangela.apps.rocklizardspock.activities;

import com.codepath.wangela.apps.rocklizardspock.R;
import com.codepath.wangela.apps.rocklizardspock.R.drawable;
import com.codepath.wangela.apps.rocklizardspock.R.id;
import com.codepath.wangela.apps.rocklizardspock.R.layout;
import com.codepath.wangela.apps.rocklizardspock.R.menu;

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
		tvOutcome = (TextView) findViewById(R.id.tvOutcome);
		tvOutcome.setText("You " + outcome + "!");

		tvWinRule = (TextView) findViewById(R.id.tvWinRule);
		tvWinRule.setText(winRule);

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
