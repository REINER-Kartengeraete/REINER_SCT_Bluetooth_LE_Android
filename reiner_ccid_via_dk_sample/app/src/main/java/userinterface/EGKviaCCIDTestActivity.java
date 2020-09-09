/*
* 
*   
* 
*   Created by Steve Spormann on 10.11.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       17.02.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package userinterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import ccid.CCIDProtocoll;
import utilitis.AtrParser;
import utilitis.ByteOperations;
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
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;
import ccid.CCIDBluetoothReader;
import ccid.CCIDReaderCallbacks;
import ccid.CCIDProtocoll.CCID_AnswerBlock;
import ccid.CCIDProtocoll.CCID_DataBlock;
import ccid.CCIDProtocoll.CCID_SlotStausBlock;
import ccid.CCIDProtocoll.CardVoltage;
import ccid.CCIDProtocoll.TransportProtocol;

import com.example.reiner_ccid_via_dk_sample.R;

public class EGKviaCCIDTestActivity extends Activity implements
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

    private Integer ReadStatus = 2;
    
    private String patientenDatenFile ="";
    private String VersicherungsDatenFile ="";

	private final static byte SlotInUse = 0x00;
    
    
    /**
     * 
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
    private void showCardAnswer(String cardAnswer, String title) {

	try {

	    builder.setMessage(cardAnswer).setTitle(title);
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
	public void didRecieveResponseError(BluetoothErrors errorMessage,String respCode) {
 
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
	
	
	    CCID_SlotStausBlock status = ccid_AnswerBlock.slotStatus;
	    CCID_DataBlock answer = ccid_AnswerBlock.dataBlock;
		CCIDProtocoll.CCID_ESCBlock esc = ccid_AnswerBlock.escBlock;

		if (currentCCIDState == CCIDState.PowerOff) {
		    _bluetoothService.disConnect(false);
		    parseData();
		    return;
		}

		if (currentCCIDState == CCIDState.PowerOn) {
		    String atr = answer.getCommandoData();
		    currentCCIDState = CCIDState.SetParameter;
		   // showCardAnswer(atr, "Card ATR");
		    _bluetoothService.setParameter(TransportProtocol.T_Equals_1,
			    AtrParser.parseATR(answer.getCommandoData()), SlotInUse);
		    return;
		}

		if (currentCCIDState == CCIDState.SetParameter) {
		    currentCCIDState = CCIDState.Connected;
		    ReadStatus = 0;
		}
		
	    if(currentCCIDState == CCIDState.Connected)
	    {
	    	
	    	ReadStatus++;
	        Log.d("ReceivedCCID_DataBlock", "Connected");

	        if (ReadStatus==1) {
				_bluetoothService.sendXfrBlock("00 A4 04 0C 07 D2 76 00 01 44 80 00", SlotInUse);
				return;
			}
	        
			if (ReadStatus==2) {
				_bluetoothService.sendXfrBlock("00 A4 04 0C 06 D2 76 00 00 01 02", SlotInUse);
				return;
			}
			if (ReadStatus==3) {
				_bluetoothService.sendXfrBlock("00 B0 81 00 00 00 00", SlotInUse);
				return;
			}
			
			if (ReadStatus==4) {
				
				patientenDatenFile = answer.getCommandoData();
				_bluetoothService.sendXfrBlock("00 B0 82 00 00 00 00",SlotInUse);
				return;
			}

			if (ReadStatus==5) {
				currentCCIDState = CCIDState.PowerOff;
				VersicherungsDatenFile = answer.getCommandoData();
				_bluetoothService.powerCardOff(SlotInUse);
				
				return;
			}
	    }
}

    private void parseData()
    {
    		EGKFile pd = new EGKFile(patientenDatenFile,true);
		String d1 = xmlFromGzip(pd.dataBytes,2, pd.len);
		
		EGKFile vd= new EGKFile(VersicherungsDatenFile,false);
		String d2 = xmlFromGzip(vd.dataBytes,8, vd.len + 8);
		
		
		showCardAnswer(d1 + d2 , "Versicherungsdaten");
    }
    
    class EGKFile
    {
    	String data;
    	byte[] dataBytes;
    	int len;
    	
    	public EGKFile(String data, boolean pd)
    	{
    		this.data = data;
    		this.dataBytes = ByteOperations.hexStringToByteArray(data);
	    		if(pd)
	    		{
	    			len = (int)toShort(dataBytes[0], dataBytes[1]) ;
	    		}else
	    		{
	    		    len = (int) toShort(dataBytes[2], dataBytes[3]) - toShort(dataBytes[0], dataBytes[1]) ;
	    		}
    		
    	}

    }
    
    
public String xmlFromGzip(byte[] message, int start, int length) {

	ByteArrayInputStream bStream = new ByteArrayInputStream(message, start, length);
	// Unzip everything.
	GZIPInputStream gStream = null;

	try {
		if (message != null && start > 0) {
			gStream = new GZIPInputStream(bStream);
			// parse the XML as a W3C Document
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setNamespaceAware(false);
			
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			bStream = null;
			String xmlWithoutNamespaces = toString(builder.parse(gStream));
			xmlWithoutNamespaces = removeXmlStringNamespaceAndPreamble(xmlWithoutNamespaces);
			
			return (xmlWithoutNamespaces);
			
			//return builder.parse(gStream);
		} else {

		}

	} catch (IOException e) {
		Log.e("Error", "IOException while reading xml from gZip. Should never occur.", e);
	} catch (ParserConfigurationException e) {
		Log.e("Error", "Error while reading xml from gZip", e);
	} catch (SAXException e) {
		Log.e("Error", "Error while reading xml from gZip", e);
	}
	Log.e("Error", "Resetting state and returning null as XML.");
	return null;
}

private Document stringToDocument(String xmlString){
	
	DocumentBuilder db;
	try {
		db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xmlString));
		Document doc = db.parse(is);
		return doc;
	} catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null;
	
}



private short toShort(byte highByte, byte lowByte) {
	return (short) ((short) (highByte & 0x00FF) << 8 | (lowByte & 0x00FF));
}

public String toString(Document doc) {
    try {
        StringWriter sw = new StringWriter();
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        transformer.transform(new DOMSource(doc), new StreamResult(sw));
        return sw.toString();
    } catch (Exception ex) {
        throw new RuntimeException("Error converting to String", ex);
    }
}

public  String removeXmlStringNamespaceAndPreamble(String xmlString) {
	  return xmlString.replaceAll("(<\\?[^<]*\\?>)?", ""). /* remove preamble */
	  replaceAll("xmlns.*?(\"|\').*?(\"|\')", "") /* remove xmlns declaration */
	  .replaceAll("(<)(\\w+:)(.*?>)", "$1$3") /* remove opening tag prefix */
	  .replaceAll("(</)(\\w+:)(.*?>)", "$1$3"); /* remove closing tags prefix */
	}


    };
}

