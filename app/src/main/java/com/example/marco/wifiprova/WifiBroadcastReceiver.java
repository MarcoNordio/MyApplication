package com.example.marco.wifiprova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by Marco on 15/12/16.
 */

public class WifiBroadcastReceiver extends BroadcastReceiver {

    MainActivity mainActivity;

    public WifiBroadcastReceiver(MainActivity act){
        mainActivity=act;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //ascolto i 4 intent che mi possono arrivare
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {



        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed!  We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed!  We should probably do something about
            // that.

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
