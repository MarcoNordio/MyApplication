package com.example.marco.wifiprova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

/**
 * Created by Marco on 19/12/16.
 */

public class ClientBroadcastReceiver extends BroadcastReceiver {
    MainActivity mainActivity;

    public ClientBroadcastReceiver(MainActivity act){
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
            if(mainActivity.mManager!=null)
            {
                if (mainActivity.mManager != null) {
                    mainActivity.mManager.requestPeers(mainActivity.mChannel, mPeerListListener);
                }
            }


        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            mainActivity.mManager.requestConnectionInfo(mainActivity.mChannel, new WifiP2pManager.ConnectionInfoListener() {
                @Override
                public void onConnectionInfoAvailable(WifiP2pInfo info) {
                    //Log.d(TAG, ITAG + "info:" + info.toString());
                    if (info.groupFormed){
                        mainActivity.info=info;
                        //device connected
                        //searchService();
                        //mClient = new Client(info.groupOwnerAddress,Server.PORT);
                    }
                }
            });
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }

    private WifiP2pManager.PeerListListener mPeerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            mainActivity.RefreshDeviceListView(peers);
        }
    };

}
