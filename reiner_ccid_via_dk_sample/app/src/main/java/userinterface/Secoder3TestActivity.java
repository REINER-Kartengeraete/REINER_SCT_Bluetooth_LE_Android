/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       1.03.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package userinterface;

import java.util.List;

import secode3.SecoderBluetoothReader;
import secode3.SecoderReaderCallbacks;
import secodeInfo.SecoderInfoData;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;
import com.example.reiner_ccid_via_dk_sample.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


// TODO: Auto-generated Javadoc
/**
 * The Class Secoder3TestActivity.
 */
public class Secoder3TestActivity extends Activity implements BluetoothTestTestActivity{

    /** The _open device selection button. */
    private Button _openDeviceSelectionButton  = null;
    
    /** The _start with selected reader button. */
    private Button _startWithSelectedReaderButton  = null;
    
    /** The _start with closest reader button. */
    private Button _startWithClosestReaderButton  = null;
    
    /** The startwith selectedreader. */
    private boolean startwithSelectedreader = true;
    
    /** The _bluetooth service. */
    private SecoderBluetoothReader _bluetoothService ;
    
    protected AlertDialog.Builder builder = null;
    protected AlertDialog dialog = null;
       
    
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
   	 try {
   	     	dialog = builder.create();
   		dialog.show();
   	} catch (Exception e) {

   		e.printStackTrace();
   	}
        }
      };
    
    /* (non-Javadoc)
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.specific_test_activity);
        this.setTitle("Secoder Info Test");
        
        builder = new AlertDialog.Builder(this);
        
        _openDeviceSelectionButton  = (Button) findViewById(R.id.buttonSelectReader);
        _startWithSelectedReaderButton  = (Button) findViewById(R.id.buttonStartSelected);
        _startWithClosestReaderButton  = (Button) findViewById(R.id.buttonStartSimple);
      
        
        _openDeviceSelectionButton.setOnClickListener(decider);
        _startWithSelectedReaderButton.setOnClickListener(decider);
        _startWithClosestReaderButton.setOnClickListener(decider);
    
        
    }

    /** The decider. */
    OnClickListener decider = new OnClickListener()
    {
	@Override
	public void onClick(View v) 
	{
	   if(v.equals(_openDeviceSelectionButton))
	   {
	       openDeviceSelection();
	   }
	   else if(v.equals(_startWithSelectedReaderButton))
	   {
	       startWithSelectedReader();
	   }
	   else if(v.equals(_startWithClosestReaderButton))
	   {
	       startWithClosestReader();
	   }
	 
	   
	}
    };
    
    
    /* (non-Javadoc)
     * @see userinterface.BluetoothTestTestActivity#openDeviceSelection()
     */
    @Override
    public void openDeviceSelection() {
	Intent intent = new Intent(getBaseContext(), BluetoothReaderSelection.class);
	startActivity(intent);
    }

    /* (non-Javadoc)
     * @see userinterface.BluetoothTestTestActivity#startWithSelectedReader()
     */
    @Override
    public void startWithSelectedReader() {
	startwithSelectedreader = true;
	initBluetoothReader();
	
	
    }

    /* (non-Javadoc)
     * @see userinterface.BluetoothTestTestActivity#startWithClosestReader()
     */
    @Override
    public void startWithClosestReader() {
	startwithSelectedreader = false;
	initBluetoothReader();
    }

   
    
    /**
     * Inits the bluetooth reader.
     */
    private void initBluetoothReader()
    {
	_bluetoothService = new SecoderBluetoothReader(secoderCallbacks, getApplicationContext());
    }
    
    /**
     * Show secoder info.
     *
     * @param info the info
     */
    private void showSecoderInfo(SecoderInfoData info)
    {
	try {

		builder.setMessage(info.toString()).setTitle("Secoder Info");
		builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//finish();
					}
				});

		myHandler.sendEmptyMessage(0);
		_bluetoothService.disConnect(false);
		
	} catch (Exception e) {

		e.printStackTrace();
	}

    }
    
    /** The secoder callbacks. */
    private SecoderReaderCallbacks secoderCallbacks = new SecoderReaderCallbacks()
    {
	String saveReaderIdForConnectAfterBond = "";

	
	@Override
	public void didRecieveApdu(String answer) {
	    
	}

	@Override
	public void didRecieveSecoderInfo(SecoderInfoData info) {
	    showSecoderInfo(info);
	}

	@Override
	public void didRecieveResponseError(BluetoothErrors errorMessage,
		String respCode) {
	}

	@Override
	public void didFindReaders(List<Bluetooth_ReaderInfo> devices) {
	    _bluetoothService.stopScaning();
	    if(devices.get(0).isBonded())
	    {
		_bluetoothService.connect(devices.get(0).getReaderID());
		_bluetoothService.requestSecoderInfo();
	    }
	    else
	    {
		saveReaderIdForConnectAfterBond = devices.get(0).getReaderID();
		_bluetoothService.bondReader(devices.get(0).getReaderID());
	    }
	}

	@Override
	public void Bonded(Bluetooth_ReaderInfo info) {
	    _bluetoothService.connect(saveReaderIdForConnectAfterBond);
	    _bluetoothService.requestSecoderInfo();
	}

	@Override
	public void onScanningFinished() {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void disconnected() {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void initiated() {
	    if(startwithSelectedreader){
		_bluetoothService.connect(BluetoothReaderSelection.LoadDefaultReader(getApplicationContext()).getReaderID());
		_bluetoothService.requestSecoderInfo();
	    }else{
		_bluetoothService.scanReaders(20000);
	    }
	}

	@Override
	public void readyToSend() {
	    // TODO Auto-generated method stub
	    
	}
	
    };
    
    
    
}
