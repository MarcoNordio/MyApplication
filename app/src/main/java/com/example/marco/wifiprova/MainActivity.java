package com.example.marco.wifiprova;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener{

    ListView listView;
    Button btnScan;

    ArrayAdapter<String> adapter;
    ArrayList<WifiP2pDevice> DeviceList = new ArrayList<>();
    ArrayList<String> DeviceListString = new ArrayList<>();

    WifiP2pManager.Channel mChannel;
    WifiP2pManager mManager;

    WifiBroadcastReceiver receiver;

    private final IntentFilter intentFilter = new IntentFilter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SetIntentWifiManager();

        SetListView();
        SetBtnScan();


        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);


    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver = new WifiBroadcastReceiver(this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    public void SetIntentWifiManager(){

        //  Indicates a change in the Wi-Fi P2P status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi P2P connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        // Indicates this device's details have changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }


    public void SetBtnScan(){
        btnScan= (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
                  @Override
                  public void onSuccess() {
                      Toast.makeText(MainActivity.this, "Scan started", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onFailure(int reason) {
                      Toast.makeText(MainActivity.this, "Discovery Failed", Toast.LENGTH_SHORT).show();
                  }
              });

            }
        });
    }

    public void SetListView(){
        listView=(ListView) findViewById(R.id.listView);
        adapter= new ArrayAdapter<String>(getApplicationContext(),R.layout.row,R.id.textViewList,DeviceListString);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //TODO implementare click
                final WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = DeviceList.get(position).deviceAddress;
                config.wps.setup = WpsInfo.PBC;
            }
        });
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        //chiamato quando ci sono dei nuovi peers
        if(peers!= null && !peers.getDeviceList().isEmpty()) {
            Collection<WifiP2pDevice> list = peers.getDeviceList();
            DeviceList.clear();
            DeviceList.addAll(list);

            DeviceListString.clear();
            for (WifiP2pDevice elem : peers.getDeviceList()) {
                DeviceListString.add(elem.deviceName);
            }

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "NO DEVICE FOUND", Toast.LENGTH_SHORT).show();
        }
    }
}
