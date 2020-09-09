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

import utilitis.AtrParser;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;
import ccid.CCIDBluetoothReader;
import ccid.CCIDProtocoll;
import ccid.CCIDProtocoll.CCID_AnswerBlock;
import ccid.CCIDProtocoll.CCID_DataBlock;
import ccid.CCIDProtocoll.CCID_SlotStausBlock;
import ccid.CCIDProtocoll.CardVoltage;
import ccid.CCIDProtocoll.TransportProtocol;
import ccid.CCIDReaderCallbacks;
import com.example.reiner_ccid_via_dk_sample.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class CCIDTestActivity.
 */
public class CCIDTestActivity extends Activity implements
	BluetoothTestTestActivity  {

    /** The _open device selection button. */
    private Button _openDeviceSelectionButton = null;

    /** The _start with selected reader button. */
    private Button _startWithSelectedReaderButton = null;

    /** The _start with closest reader button. */
    private Button _startWithClosestReaderButton = null;

    /** The _bluetooth service. */
    private CCIDBluetoothReader _bluetoothService;

    /** The startwith selectedreader. */
    private boolean startwithSelectedreader = true;

    /** The current ccid state. */
    private CCIDState currentCCIDState = CCIDState.PowerOff;

	private final static byte SlotInUse = 0x00;
    /**
     * The Enum CCIDState.
     */
    private enum CCIDState {
	/** The Secure. */
	Secure, /** The Power off. */
	PowerOff, /** The Power on. */
	PowerOn, /** The Connected. */
	Connected, /** The SetParameter. */
	SetParameter
    };

    protected AlertDialog.Builder builder = null;
    protected AlertDialog dialog = null;

    @SuppressLint("HandlerLeak")
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

    /*
     * (non-Javadoc)
     * 
     * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.specific_test_activity);
	this.setTitle("CCID Test");

	builder = new AlertDialog.Builder(this);

	_openDeviceSelectionButton = (Button) findViewById(R.id.buttonSelectReader);
	_startWithSelectedReaderButton = (Button) findViewById(R.id.buttonStartSelected);
	_startWithClosestReaderButton = (Button) findViewById(R.id.buttonStartSimple);

	_openDeviceSelectionButton.setOnClickListener(decider);
	_startWithSelectedReaderButton.setOnClickListener(decider);
	_startWithClosestReaderButton.setOnClickListener(decider);

    }

    /** The decider. */
    OnClickListener decider = new OnClickListener() {
	@Override
	public void onClick(View v) {

	    try {
		if (v.equals(_openDeviceSelectionButton)) {
		    openDeviceSelection();
		} else if (v.equals(_startWithSelectedReaderButton)) {

		    startWithSelectedReader();
		} else if (v.equals(_startWithClosestReaderButton)) {
		    startWithClosestReader();
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }

	}
    };

    /*
     * (non-Javadoc)
     * 
     * @see userinterface.BluetoothTestTestActivity#openDeviceSelection()
     */
    @Override
    public void openDeviceSelection() {
	Intent intent = new Intent(getBaseContext(),
		BluetoothReaderSelection.class);
	startActivity(intent);

    }

    /*
     * (non-Javadoc)
     * 
     * @see userinterface.BluetoothTestTestActivity#startWithSelectedReader()
     */
    @Override
    public void startWithSelectedReader() {
	startwithSelectedreader = true;
	try {
	    initBluetoothReader();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see userinterface.BluetoothTestTestActivity#startWithClosestReader()
     */
    @Override
    public void startWithClosestReader() {
	startwithSelectedreader = false;
	try {
	    initBluetoothReader();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Inits the bluetooth reader.
     * 
     * @throws InterruptedException
     */
    private void initBluetoothReader() throws InterruptedException {
	if (_bluetoothService != null)
	    _bluetoothService.disConnect(true);
	Thread.sleep(2);

	_bluetoothService = new CCIDBluetoothReader(ccidCcallbacks,
		getApplicationContext());

    }

    /**
     * Show card answer.
     * 
     * @param apdu
     *            the apdu
     */
    private void showCardAnswer(String apdu, String title) {

	try {

	    builder.setMessage(apdu).setTitle(title);
	    builder.setPositiveButton("OK",
		    new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			    // finish();
			}
		    });

	    myHandler.sendEmptyMessage(0);
	} catch (Exception e) {

	    e.printStackTrace();
	}
    }


    /** The ccid ccallbacks. */
    private CCIDReaderCallbacks ccidCcallbacks = new CCIDReaderCallbacks() {
	String saveReaderIdForConnectAfterBond = "";

	@Override
	public void readyToSend() {
	    currentCCIDState = CCIDState.PowerOn;
	    _bluetoothService.powerCardOn(CardVoltage.VOLTS_5_CARD_VOLTAGE, SlotInUse);
	}

	@Override
	public void didSendApdu() {

	}

	@Override
	public void didRecieveResponseError(BluetoothErrors errorMessage,
		String respCode) {

	}

	@Override
	public void didFindReaders(List<Bluetooth_ReaderInfo> devices) {
	    _bluetoothService.stopScaning();
	    if (devices.get(0).isBonded()) {
		_bluetoothService.connect(devices.get(0).getReaderID());

	    } else {
		saveReaderIdForConnectAfterBond = devices.get(0).getReaderID();
		_bluetoothService.bondReader(devices.get(0).getReaderID());
	    }
	}

	@Override
	public void Bonded(Bluetooth_ReaderInfo info) {
	    _bluetoothService.connect(saveReaderIdForConnectAfterBond);
	}

	@Override
	public void onScanningFinished() {

	}

	@Override
	public void disconnected() {

	}

	@Override
	public void initiated() {
	    if (startwithSelectedreader) {
		_bluetoothService.connect(BluetoothReaderSelection
			.LoadDefaultReader(getApplicationContext())
			.getReaderID());
	    } else {
		_bluetoothService.scanReaders(20000);
	    }
	}

	@Override
	public void didRecieveCCID_DataBlock(CCID_AnswerBlock ccid_AnswerBlock) {

	    try {
		CCID_SlotStausBlock status = ccid_AnswerBlock.slotStatus;
		CCID_DataBlock answer = ccid_AnswerBlock.dataBlock;

		if (currentCCIDState == CCIDState.PowerOff) {
		    _bluetoothService.disConnect(false);
		    return;
		}

		if (currentCCIDState == CCIDState.PowerOn) {
		    String atr = answer.getCommandoData();
		    currentCCIDState = CCIDState.SetParameter;
		    showCardAnswer(atr, "Card ATR");
		    _bluetoothService.setParameter(TransportProtocol.Automatic,
			    AtrParser.parseATR(answer.getCommandoData()),SlotInUse);
		    return;
		}

		if (currentCCIDState == CCIDState.SetParameter) {
		    currentCCIDState = CCIDState.Connected;
		    _bluetoothService
			    .sendXfrBlock(CCIDProtocoll.getRootCommand, SlotInUse);
		    return;
		}

		if (currentCCIDState == CCIDState.Connected) {
		    currentCCIDState = CCIDState.PowerOff;
		    String masterFile = answer.getCommandoData();
		    showCardAnswer(masterFile, "Master File");
		    _bluetoothService.powerCardOff(SlotInUse);
		    return;
		}

	    } catch (Exception exc) {
		Log.d("CCID UI", exc.toString());
		_bluetoothService.powerCardOff(SlotInUse);
	    }

	}

    };

}
