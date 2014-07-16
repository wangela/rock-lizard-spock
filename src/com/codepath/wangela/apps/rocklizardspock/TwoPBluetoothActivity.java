package com.codepath.wangela.apps.rocklizardspock;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TwoPBluetoothActivity extends Activity {
	BluetoothAdapter bluetooth;
	String status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two_pbluetooth);
		
		bluetooth = BluetoothAdapter.getDefaultAdapter();
		
		if(bluetooth != null)
		{
		    checkBluetoothEnabled();
		} else {
			Toast.makeText(this, "Sorry, your device doesn't support Bluetooth", Toast.LENGTH_LONG).show();
		}
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

	private void checkBluetoothEnabled() {
		if (bluetooth.isEnabled()) {
		    // Enabled. Work with Bluetooth.
		    String mydeviceaddress = bluetooth.getAddress();
		    String mydevicename = bluetooth.getName();
		    String state = String.valueOf(bluetooth.getState());
		    status = mydevicename + " : "+ mydeviceaddress + " : " + state;
		}
		else
		{
		    // Disabled. Do something else.
			status = "Bluetooth is not Enabled.";
		}
		
		Toast.makeText(this, status, Toast.LENGTH_LONG).show();
	}
}
