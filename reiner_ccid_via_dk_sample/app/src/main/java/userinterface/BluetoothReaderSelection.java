/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       17.02.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package userinterface;

import java.util.List;
import java.util.Set;
import secode3.SecoderBluetoothReader;
import secode3.SecoderReaderCallbacks;
import secodeInfo.SecoderInfoData;
import utilitis.*;
import bluetooth.BluetoothConnectionState;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;
import com.example.reiner_ccid_via_dk_sample.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class BluetoothReaderSelection.
 */
public class BluetoothReaderSelection extends Activity {

    /** The _bluetooth service. */
    private SecoderBluetoothReader _bluetoothService;
    
    /** The found devices. */
    private ListView foundDevices;
    
    /** The known devices. */
    private ListView knownDevices;
    
    /** The scan button. */
    private Button scanButton;
    
    /** The spinner. */
    private ProgressBar spinner;
    
    /** The m array adapter. */
    private BluetoothReaderInfoAdapter mArrayAdapter;
    
    /** The foundreaders. */
    private SetList<Bluetooth_ReaderInfo> foundreaders = new SetList<Bluetooth_ReaderInfo>();
    
    /** The knownreaders. */
    private SetList<Bluetooth_ReaderInfo> knownreaders = new SetList<Bluetooth_ReaderInfo>();
    
    /** The deviceto bond posittion. */
    private int devicetoBondPosittion = 0;
    
    /** The used reader text view. */
    private TextView usedReaderTextView;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.reader_selection);

	foundDevices = (ListView) findViewById(R.id.listViewFoundDevices);
	knownDevices = (ListView) findViewById(R.id.listViewKnownDevices);
	scanButton = (Button) findViewById(R.id.scanButton);
	spinner = (ProgressBar) findViewById(R.id.spinnerProgress);
	spinner.setVisibility(View.INVISIBLE);
	usedReaderTextView = (TextView) findViewById(R.id.textViewUsedReader);
	//foundreaders.add(new Bluetooth_ReaderInfo());
	knownreaders.add(new Bluetooth_ReaderInfo());

	updateKnownReaders();

	scanButton.setOnClickListener(listener);
	scanButton.setEnabled(false);
	LoadDefaultReader();
	initBluetoothReader();
    }

    /** The listener. */
    OnClickListener listener = new OnClickListener() {
	@Override
	public void onClick(View v) {
	    spinner.setVisibility(View.VISIBLE);
	    _bluetoothService.scanReaders(9999999);
	}
    };

    /**
     * Inits the bluetooth reader.
     */
    private void initBluetoothReader() {
	_bluetoothService = new SecoderBluetoothReader(secoderCallbacks,
		getApplicationContext());
    }

    /**
     * Load bonded bluetooth devices.
     */
    private void LoadBondedBluetoothDevices() {
	knownreaders.clear();
	final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
	Set<BluetoothDevice> pairedDevices = null;

	pairedDevices = bluetoothManager.getAdapter().getBondedDevices();

	// If there are paired devices
	if (pairedDevices.size() > 0) {
	    // Loop through paired devices
	    for (BluetoothDevice device : pairedDevices) {
		knownreaders.add(new Bluetooth_ReaderInfo(device.getName(), device.getAddress(), true));
	    }
	}

	if (knownreaders.size() <= 0)
	    knownreaders.add(new Bluetooth_ReaderInfo());
    }

    /**
     * Update known readers.
     */
    private synchronized void updateKnownReaders() {
	LoadBondedBluetoothDevices();
	mArrayAdapter = new BluetoothReaderInfoAdapter(this, knownreaders);
	knownDevices.setAdapter(mArrayAdapter);
	knownDevices.setOnItemClickListener(tableClickListener2);

    }

    /**
     * Update found readers.
     */
    private synchronized void updateFoundReaders() {
	if(mArrayAdapter != null) mArrayAdapter.clear();
	
	mArrayAdapter = new BluetoothReaderInfoAdapter(this, foundreaders);
	foundDevices.setAdapter(mArrayAdapter);
	foundDevices.setOnItemClickListener(tableClickListener);

    }

    /** The table click listener. */
    private OnItemClickListener tableClickListener = new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
	  
	    if (parent == foundDevices) {
		
		devicetoBondPosittion = position;
		if(_bluetoothService.getBluetoothConnectionState() == BluetoothConnectionState.Scanning){
		    _bluetoothService.stopScaning();
		}
		else
		{
		    startBonding();
		}
	    }
	}
    };
    
    /** The table click listener2. */
    private OnItemClickListener tableClickListener2 = new OnItemClickListener() {

   	@Override
   	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
   	    if (parent == knownDevices) {
   		Bluetooth_ReaderInfo reader = knownreaders.get(position);
   		saveDefaultReader(reader);
   	    }
   	}
       };
    
    /**
     * Start bonding.
     */
    private synchronized void startBonding()
    {
	 Bluetooth_ReaderInfo reader = foundreaders.get(devicetoBondPosittion);
	 _bluetoothService.bondReader(reader.getReaderID());
    }

    /**
     * Save default reader.
     *
     * @param info the info
     */
    private void saveDefaultReader(Bluetooth_ReaderInfo info) {
	
	usedReaderTextView.setText("Used Reader:" + info.getReaderName());
	
	SharedPreferences preferences = PreferenceManager
		.getDefaultSharedPreferences(this);
	SharedPreferences.Editor preferencesEditor = preferences.edit();
	preferencesEditor.putString("RSCTActiveBluetoothDeviceName",
		info.getReaderName());
	preferencesEditor.putString("RSCTActiveBluetoothDeviceId",
		info.getReaderID());
	preferencesEditor.commit();
    }
    
    /**
     * Load default reader.
     *
     * @return the bluetooth_ reader info
     */
    private Bluetooth_ReaderInfo LoadDefaultReader() {
	Bluetooth_ReaderInfo info = new Bluetooth_ReaderInfo();
	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	info.setReaderName(preferences.getString("RSCTActiveBluetoothDeviceName", "no reader found"));
	info.setReaderID(preferences.getString("RSCTActiveBluetoothDeviceId",""));
	usedReaderTextView.setText("Used Reader:" + info.getReaderName());
	return info;
    }
    
    /**
     * Load default reader.
     *
     * @param ctx the ctx
     * @return the bluetooth_ reader info
     */
    public static Bluetooth_ReaderInfo LoadDefaultReader(Context ctx) {
   	Bluetooth_ReaderInfo info = new Bluetooth_ReaderInfo();
   	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
   	info.setReaderName(preferences.getString("RSCTActiveBluetoothDeviceName", "no reader found"));
   	info.setReaderID(preferences.getString("RSCTActiveBluetoothDeviceId",""));
   	return info;
       }
    
    /**
     * Show device selected dialog.
     *
     * @param info the info
     */
    private void showDeviceSelectedDialog( Bluetooth_ReaderInfo info)
    {
	try {

		AlertDialog.Builder builder = null;
		builder = new AlertDialog.Builder(this);
		builder.setMessage("You selected the Device "+ info.getReaderName() + ".\nMake Sure the device is Bond in the Android bluetooth menu!").setTitle("Choosen Reader");
		builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	} catch (Exception e) {

		e.printStackTrace();
	}
    }

    /** The secoder callbacks. */
    private SecoderReaderCallbacks secoderCallbacks = new SecoderReaderCallbacks() {

	@Override
	public void didRecieveApdu(String answer) {

	}

	@Override
	public void didRecieveSecoderInfo(SecoderInfoData info) {

	}

	@Override
	public void didRecieveResponseError(BluetoothErrors errorMessage,
		String respCode) {
	}

	@Override
	public void didFindReaders(List<Bluetooth_ReaderInfo> devices) {
	    foundreaders.addAll(devices);
	    updateFoundReaders();
	}


	@Override
	public void onScanningFinished() {
	    spinner.setVisibility(View.INVISIBLE);
	    startBonding();
	}

	@Override
	public void disconnected() {
	    // TODO Auto-generated method stub

	}

	@Override
	public void initiated() {
	    scanButton.setEnabled(true);
	}

	@Override
	public void Bonded(Bluetooth_ReaderInfo info) {
	    saveDefaultReader(info);
	    showDeviceSelectedDialog(info);
	}

	@Override
	public void readyToSend() {
	    // TODO Auto-generated method stub
	    
	}

    };

}
