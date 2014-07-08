package com.codepath.wangela.apps.rocklizardspock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class OnePActivity extends Activity {
	private ImageView ivArrowsSpock;
	private Button btnFight;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onep);
		setupViews();
	}

	private void setupViews() {
		ivArrowsSpock = (ImageView) findViewById(R.id.ivArrowsSpock);
		btnFight = (Button) findViewById(R.id.btnFight);
		
	}
	
	public void onChoose(View v) {
		ivArrowsSpock.setVisibility(ImageView.VISIBLE);
		btnFight.setVisibility(Button.VISIBLE);
	}
	
	public void onFight(View v) {
		Weapon opponentWeapon = Weapon.pickWeapon();
		Toast.makeText(getApplicationContext(), "You choose SPOCK. Android chooses " + opponentWeapon.toString(), Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getApplicationContext(), WinActivity.class);
		i.putExtra("myWeapon", "SPOCK");
		i.putExtra("opponentWeapon", opponentWeapon.toString());
		startActivity(i);
	}
}
