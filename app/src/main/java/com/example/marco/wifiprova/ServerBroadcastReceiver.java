package com.example.marco.wifiprova;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Marco on 15/12/16.
 */

public class ServerBroadcastReceiver extends BroadcastReceiver {

    MainActivity mainActivity;

    public ServerBroadcastReceiver(MainActivity act){
        mainActivity=act;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //ascolto i 4 intent che mi possono arrivare
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {



        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mainActivity.mManager != null) {
                mainActivity.mManager.requestPeers(mainActivity.mChannel, mPeerListListener);
            }


        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            mainActivity.mManager.requestConnectionInfo(mainActivity.mChannel, new WifiP2pManager.ConnectionInfoListener() {
                @Override
                public void onConnectionInfoAvailable(WifiP2pInfo info) {
                    //Log.d(TAG, ITAG + "info:" + info.toString());
                    if(!info.groupFormed){
                        //TODO gestire numero tentativi
                        //createGroup();
                    }else{
                        mainActivity.mManager.requestGroupInfo(mainActivity.mChannel, new WifiP2pManager.GroupInfoListener() {
                            @Override
                            public void onGroupInfoAvailable(WifiP2pGroup group) {
                                Toast.makeText(mainActivity, "Sono il Group Owner", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }


    private WifiP2pManager.PeerListListener mPeerListListener = new WifiP2pManager.PeerListListener() {
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peers) {
            //Log.d(TAG, ITAG + peers.getDeviceList().size() + " peersAvailable");
            //updateListDevice(peers);
            //notifyListChanged();
        }

    };
}
