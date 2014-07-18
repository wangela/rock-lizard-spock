/* INCOMPLETE stub for Wifi Direct - to finish when I have two hardware devices to test with */

package com.codepath.wangela.apps.rocklizardspock.helpers;

import com.codepath.wangela.apps.rocklizardspock.TwoPWifiActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.widget.Toast;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

	/**
	 * A BroadcastReceiver that notifies of important Wi-Fi p2p events.
	 */

	private WifiP2pManager mManager;
	private Channel mChannel;
	private TwoPWifiActivity mActivity;

	public WifiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel,
			TwoPWifiActivity activity) {
		super();
		this.mManager = manager;
		this.mChannel = channel;
		this.mActivity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();

		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				// Wifi P2P is enabled
				// notify mActivity and search for peeers
				Toast.makeText(mActivity.getApplicationContext(), "Wifi enabled", Toast.LENGTH_LONG).show();
				mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
				    @Override
				    public void onSuccess() {
						Toast.makeText(mActivity.getApplicationContext(), "Peers found", Toast.LENGTH_LONG).show();
				    }

				    @Override
				    public void onFailure(int reasonCode) {
				    	Toast.makeText(mActivity, "No peers found", Toast.LENGTH_SHORT);
				    }
				});
			} else {
				// Wi-Fi P2P is not enabled
				// notify mActivity and have mActivity request permission to turn on wifi
				Toast.makeText(mActivity.getApplicationContext(), "Wifi not enabled", Toast.LENGTH_LONG).show();
			}
			// Check to see if Wi-Fi is enabled and notify appropriate activity
		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
			// Call WifiP2pManager.requestPeers() to get a list of current peers

		    // Request available peers from the wifi p2p manager. This is an
		    // asynchronous call and the calling activity is notified with a
		    // callback on PeerListListener.onPeersAvailable()
		    if (mManager != null) {
		        mManager.requestPeers(mChannel, new PeerListListener() {
					
					@Override
					public void onPeersAvailable(WifiP2pDeviceList peers) {
						mActivity.foundPeers(peers);
					}
				});
		    }
		} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION
				.equals(action)) {
			// Respond to new connection or disconnections
		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
				.equals(action)) {
			// Respond to this device's wifi state changing
		}
		

	}
	
	public void pickBuddy(WifiP2pDevice device) {
		//obtain a peer from the WifiP2pDeviceList
		final String name = device.deviceName;
		WifiP2pConfig config = new WifiP2pConfig();
		config.deviceAddress = device.deviceAddress;
		mManager.connect(mChannel, config, new ActionListener() {

		    @Override
		    public void onSuccess() {
		        //success logic
		    	Toast.makeText(mActivity, "Successfully paired with " + name, Toast.LENGTH_LONG).show();
		    }

		    @Override
		    public void onFailure(int reason) {
		        //failure logic
		    	Toast.makeText(mActivity, "Successfully paired with " + name + ". Reason " + String.valueOf(reason), Toast.LENGTH_LONG).show();
		    }
		});
	}
}
