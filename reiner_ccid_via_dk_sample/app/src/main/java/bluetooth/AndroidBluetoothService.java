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
package bluetooth;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import utilitis.ByteOperations;
import utilitis.SetList;
import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class AndroidBluetoothService.
 */
@SuppressLint("NewApi")
public class AndroidBluetoothService extends Service {

	/** The Constant TAG. */
	public final static String TAG = "RSCT BLUETOOTH LE";
	// Callbacks
	/** The m callback. */
	private BluetoothReaderCallbacks mCallback = null;
	// Set RSCTBluetoothCallbacks
	
	/**
	 * Sets the callbacks.
	 *
	 * @param callback the callback
	 */
	public void SetCallbacks(BluetoothReaderCallbacks callback) {
		Log.i(TAG, "Set Callbacks");
		mCallback = callback;
	}
	
	/**
	 * Instantiates a new android bluetooth service.
	 */
	public AndroidBluetoothService() {

	}
	
	// Service binder
	/** The m binder. */
	private final IBinder mBinder = new LocalBinder();

	/** The m bluetooth adapter. */
	private BluetoothAdapter mBluetoothAdapter;
	
	/** The m bluetooth gatt. */
	private BluetoothGatt mBluetoothGatt;

	// Save the send characteristic
	/** The sender. */
	private BluetoothGattCharacteristic sender = null;



	// GATT connection states
	/** The Constant ACTION_GATT_CONNECTED. */
	public final static String ACTION_GATT_CONNECTED = "ACTION_GATT_CONNECTED";
	
	/** The Constant ACTION_GATT_DISCONNECTED. */
	public final static String ACTION_GATT_DISCONNECTED = "ACTION_GATT_DISCONNECTED";
	
	/** The Constant ACTION_GATT_SERVICES_DISCOVERED. */
	public final static String ACTION_GATT_SERVICES_DISCOVERED = ".ACTION_GATT_SERVICES_DISCOVERED";
	
	/** The Constant ACTION_DATA_AVAILABLE. */
	public final static String ACTION_DATA_AVAILABLE = "ACTION_DATA_AVAILABLE";
	
	/** The Constant EXTRA_DATA. */
	public final static String EXTRA_DATA = "EXTRA_DATA";

	// UUIDs for Bluetooth
	/** The D k_ service_ uui d_ old. */
	private final  UUID DK_Service_UUID_OLD = UUID.fromString(BluetoothUUIDS.DK_Service_UUID);// Service UUID
	
	/** The Rx_ data_ characteristic_ uuid_ old. */
	private final  UUID Rx_Data_Characteristic_Uuid_OLD = UUID.fromString(BluetoothUUIDS.DK_Rx_Data_Characteristic_Uuid);// Characteristic															// UUID
	
	/** The Tx_ data_ characteristic_ uuid_ old. */
	private final  UUID Tx_Data_Characteristic_Uuid_OLD = UUID.fromString(BluetoothUUIDS.DK_Tx_Data_Characteristic_Uuid);// Characteristic														// UUID
	
	/** The Secoder3_ service_ uui d_ new. */
	private final  UUID Secoder3_Service_UUID_NEW = UUID.fromString(BluetoothUUIDS.Secoder_3_UUID);// Service UUID
	
	/** The Rx_ data_ characteristic_ uuid_ new. */
	private final  UUID Rx_Data_Characteristic_Uuid_NEW = UUID.fromString(BluetoothUUIDS.Secoder_3_Rx_Data_Characteristic_Uuid);// Characteristic											// UUID
	
	/** The Tx_ data_ characteristic_ uuid_ new. */
	private final  UUID Tx_Data_Characteristic_Uuid_NEW = UUID.fromString(BluetoothUUIDS.Secoder_3_Tx_Data_Characteristic_Uuid);// Characteristic

	// required variables for connection
	/** The device list. */
	private SetList<Bluetooth_ReaderInfo> deviceList = new SetList<Bluetooth_ReaderInfo>();
	
	/** The device list bt. */
	private SetList<BluetoothDevice> deviceListBt = new SetList<BluetoothDevice>();
	
	/** The device list strings. */
	private SetList<String> deviceListStrings = new SetList<String>();
	
	/** The is scanning. */
	private boolean isScanning = false;
	
	/** The device to connect. */
	private BluetoothDevice deviceToConnect = null;

	
	 /**
 	 * The Class LocalBinder.
 	 */
 	public class LocalBinder extends Binder {
	     
	     /**
     	 * Gets the service.
     	 *
     	 * @return the service
     	 */
     	AndroidBluetoothService getService() {
	            // Return this instance of LocalService so clients can call public methods
	            return AndroidBluetoothService.this;
	        }
	    }
	 
	 
	 /* (non-Javadoc)
 	 * @see android.app.Service#onBind(android.content.Intent)
 	 */
 	@Override
	 public IBinder onBind(Intent intent) {
	        return mBinder;
	  }
	
	
	
	/**
	 * Callback methods defined by Android's BT LE API.
	 */
	private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

		// //////////////////////////////////////////////////////////////////////////////
		// BlueTooth callback methods
		// //////////////////////////////////////////////////////////////////////////////

		/**
		 * BT Callbacks
		 * 
		 * Listens for changes in the Bluetooth Connection State called from the
		 * Android BT Stack
		 * 
		 * 
		 * @param gatt
		 * @param status
		 * @param newState
		 */
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status,
				int newState) {
			String intentAction;

			try{
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				intentAction = ACTION_GATT_CONNECTED;

				broadcastUpdate(intentAction);
				Log.i(TAG, "Connected to GATT server.");
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				

				
				List<BluetoothGattService> chara = gatt.getServices();
				
				if (chara.size()>0) {
					broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
				
				} else {
				gatt.discoverServices();
				}
			//}
			
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
		
			        mCallback.disconnected();
				Log.i(TAG, "Disconnected from GATT server.");
				disconnect(true);
			}
			}catch (Exception exc)
			{
			    
			}
		}

		
		
		
		
		/**
		 * BT Callbacks if characteristic changed
		 * 
		 * Listens if a characteristic changed called from the Android BT Stack
		 * 
		 * 
		 * @param gatt
		 * @param characteristic
		 */
		@Override
		public void onCharacteristicChanged(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic) {
			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
		}

		/**
		 * BT Callbacks if a new services was discovered
		 * 
		 * Listens if a services was discovered called from the Android BT Stack
		 * 
		 * 
		 * @param gattVorsichrtVorsicht
		 * @param characteristic
		 */
		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
			} else {
				Log.w(TAG, "onServicesDiscovered received: " + status);
			}

			if (status == BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION) {
				mCallback.didRecieveError(BluetoothErrors.BluetoothPairingCorrupted, null);
			}
		}

		/**
		 * BT Callbacks if a BT descriptor was over written
		 * 
		 * Listens if a descriptor was written called from the Android BT Stack
		 * 
		 * 
		 * @param gatt
		 * @param descriptor
		 * @param status
		 */
		@Override
		public void onDescriptorWrite(BluetoothGatt gatt,
				BluetoothGattDescriptor descriptor, int status) {	
			
			if (status == BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION) {
				mCallback.didRecieveError(BluetoothErrors.BluetoothPairingCorrupted, null);
			}
			mCallback.readyToSend();
		}

		/**
		 * BT Callbacks contains the result of a characteristic read operation
		 * 
		 * In this callback all the incoming data transfer is handled.
		 * 
		 * 
		 * @param gatt
		 * @param characteristic
		 * @param status
		 */
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {

				broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
			}
			if (status == BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION) {
				mCallback.didRecieveError(BluetoothErrors.BluetoothPairingCorrupted, null);
			}

		}

		/**
		 * BT Callbacks is called if the value of a local characteristic is
		 * written
		 * 
		 * In this callback all the outgoing data can be handled before actually
		 * sending them
		 * 
		 * @param gatt
		 * @param characteristic
		 * @param status
		 */
		@Override
		public void onCharacteristicWrite(BluetoothGatt gatt,
				BluetoothGattCharacteristic characteristic, int status) {

			if (status == BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION) {
				mCallback.didRecieveError(BluetoothErrors.BluetoothPairingCorrupted, null);
			}
			mCallback.didSend();
		}

	};


	/**
	 * Handles ACTION_GATT_SERVICES_DISCOVERED calls
	 * 
	 * In this callback all the we check if a reader is connected and look for
	 * the expected characteristics.
	 *
	 * @param action the action
	 */

	private void broadcastUpdate(final String action) {
		

		if (ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
			List<BluetoothGattService> services = mBluetoothGatt.getServices();
			for (BluetoothGattService service : services) {
				if (service != null) {
					List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
					if (service.getUuid().compareTo(DK_Service_UUID_OLD) == 0 || service.getUuid().compareTo(Secoder3_Service_UUID_NEW) == 0) {
        					for (BluetoothGattCharacteristic characteristic : characteristics) {
        						if (characteristic != null)
        							broadcastUpdate("ACTION_GATT_CONNECTED",characteristic);
        					}
					}
				}
			}
		}

	}

	/**
	 * Handles incoming data and outgoing data
	 * 
	 * sets bluetooth funktions.
	 *
	 * @param action the action
	 * @param characteristic the characteristic
	 */
	private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {

		// intent to pass data from the service to UI thread
		final Intent intent = new Intent(action);

		// the characteristic value has changed
		if (ACTION_DATA_AVAILABLE.equals(action)) {

			// the reader sent a message to the device
			if (characteristic.getUuid().compareTo(Rx_Data_Characteristic_Uuid_OLD) != 0 || characteristic.getUuid().compareTo(Rx_Data_Characteristic_Uuid_NEW) != 0) {
				inputRecieved(characteristic.getValue());
			}

		}

		// the characteristic has been discovered
		if (ACTION_GATT_CONNECTED.equals(action)) {

	

			// the sender characteristic was discovered, save for further use
			if (characteristic.getUuid().compareTo(Rx_Data_Characteristic_Uuid_OLD) == 0 || characteristic.getUuid().compareTo(Rx_Data_Characteristic_Uuid_NEW) == 0) {

				Log.w("Permissions", " " + characteristic.getPermissions());
				sender = characteristic;
				sender.setWriteType(2);
			}

			// the Bluetooth receiver UUID was discovered, set the listener to
			// this UUID
			if (characteristic.getUuid().compareTo(Tx_Data_Characteristic_Uuid_OLD) == 0 || characteristic.getUuid().compareTo(Tx_Data_Characteristic_Uuid_NEW) == 0) {
				// set the characteristic notification/ indication
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				
				}
				mBluetoothGatt.setCharacteristicNotification(characteristic,
						true);
				UUID descriptorUUID = UUID
						.fromString("00002902-0000-1000-8000-00805F9B34FB");
				BluetoothGattDescriptor descriptor = characteristic
						.getDescriptor(descriptorUUID);
				descriptor
						.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
				mBluetoothGatt.writeDescriptor(descriptor);
			}
		}

		sendBroadcast(intent);
	}

	
	
	/**
	 *  is sending the actual data.
	 *
	 * @param value the value
	 */
	public void send(byte[] value){
		
		if(sender.setValue(value)){

			if(mBluetoothGatt.writeCharacteristic(sender )){
				Log.w(TAG, "Send chuncks:  " + ByteOperations.byteArrayToHexString(value));
			}else{
				
				Log.w(TAG,"die Charackterisitk konnte nicht geschreiben werden");
				return;
			}
		   
		}else{
			Log.w(TAG,"die Charackterisitk konnte nicht gesetzt werden");
			return;
		}
		
		
	}



	/**
	 * Input recieved.
	 *
	 * @param value the value
	 */
	private void inputRecieved(byte[] value) {

		Log.w(TAG, "Recieved:  " + ByteOperations.byteArrayToHexString(value));
		mCallback.didRecieveBlock(value);
	}
		
	

	/**
	 * function to disconnect the BT connection
	 * 
	 * !!!!!! IMPORTANT !!!!!!!! call this method at the end of the connection
	 * to make sure the GATT service is closed, otherwise you have to wait up to
	 * 2 minutes till you are able to reconnect
	 * 
	 * Warning !!!! some devices might not respond to this call.
	 *
	 * @param justDisconnectDevice the just disconnect device
	 */
	public void disconnect( boolean justDisconnectDevice) {
	   
		if(mBluetoothGatt!= null){
			mBluetoothGatt.disconnect();
			mBluetoothGatt.close();
			
			if(!justDisconnectDevice)
			{
			    
			    
			}
		}
		
		Log.w(TAG, "disconnected successfully");
	}

	/**
	 * This function connects the BT server to the selected device
	 * 
	 * the selected device is the device stored in SharedPreferences under the
	 * string "RSCTActiveBluetoothDevice" stored is just the device Address
	 * given to the device by the Android system.
	 */
	public void connectToDevice() {

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

		mBluetoothAdapter = bluetoothManager.getAdapter();
		Set<BluetoothDevice> pairedDevices = null;
		
		String deviceAddress = PreferenceManager.getDefaultSharedPreferences(
				this).getString("RSCTActiveBluetoothDevice", "noReaderFound");

		if (deviceAddress.equals("noReaderFound")) {
			mCallback.didRecieveError(BluetoothErrors.NoReaderFound, null);
			return;
		}

		try {
			pairedDevices = mBluetoothAdapter.getBondedDevices();

			if (pairedDevices.size() == 0) {
			    mCallback.didRecieveError(BluetoothErrors.NoReaderBondedInAndroidSystem, null);
				return;
			}

			for (BluetoothDevice device : pairedDevices) {
				if (device != null) {
					if (device.getAddress().equals(deviceAddress)) {
						deviceToConnect = device;
						break;
					}
				}
			}

			if (deviceToConnect != null) {
				mBluetoothGatt = deviceToConnect.connectGatt(this, true,
						mGattCallback);
			} else {
			    mCallback.didRecieveError(BluetoothErrors.NoReaderFound, null);
			}

		} catch (Exception e) {

			Log.i(TAG, "connectToDevice () failed");
			Log.d("Debugg: ",
					"mBluetoothAdapter:" + mBluetoothAdapter.toString());
			Log.d("Debugg: ", "pairedDevices:" + pairedDevices.toString());
			Log.d("Debugg: ", "mBinder:" + mBinder.toString());
			e.printStackTrace();

		}

	}

	

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////
	// / Connection related functions
	// ///////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Scan for readers.
	 *
	 * @param timeout the timeout
	 */
	public void scanForReaders(long timeout) {

	    deviceList.clear();
		deviceListStrings.clear();
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		scanLeDevice(true);
		
		new CountDownTimer(timeout, 1000) {

		     public void onTick(long millisUntilFinished) {
			
		     }

		     public void onFinish() {
			 scanLeDevice(false);
		     }
		  }.start();
		
	}

	/**
	 * Stop scan for readers.
	 */
	public void stopScanForReaders() {
		scanLeDevice(false);
	}

	/**
	 * Scan le device.
	 *
	 * @param enable the enable
	 */
	private void scanLeDevice(final boolean enable) {
		
	        if (enable) {
		    	if(!isScanning){
		    	    isScanning = true;
		    	    mBluetoothAdapter.getBluetoothLeScanner().startScan(mLeScanCallback);
		    	}
		} else 
		{
		    if(isScanning){
			isScanning = false;
			mBluetoothAdapter.getBluetoothLeScanner().stopScan(mLeScanCallback);
			mCallback.onScanningFinished();
		    }
		}

	}

	/**
	 * this funktion is to connect with a specific bluetooth device.
	 *
	 * @param deviceAddress the device address
	 */
	public void connectToDevice(String deviceAddress) {

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		Set<BluetoothDevice> pairedDevices = null;

		try {
			pairedDevices = mBluetoothAdapter.getBondedDevices();

			for (BluetoothDevice device : pairedDevices) {
				if (device != null) {
					if (device.getAddress().equals(deviceAddress)) {
						deviceToConnect = device;
						break;
					}
				}
			}

			if (deviceToConnect != null) {
				mBluetoothGatt = deviceToConnect.connectGatt(this, true,
						mGattCallback);
			}

		} catch (Exception e) {

			// Log.i(TAG, "connectToDevice () failed");
			// Log.d("Debugg: ", "mBluetoothAdapter:"
			// +mBluetoothAdapter.toString() );
			// Log.d("Debugg: ", "pairedDevices:" +pairedDevices.toString() );
			// Log.d("Debugg: ", "mBinder:" +mBinder.toString() );
			// e.printStackTrace();

		}
	}

	// Device scan callback, called if new device was found
	/** The m le scan callback. */
	private ScanCallback mLeScanCallback = new ScanCallback() {
		@Override
		
		  public void onScanResult(int callbackType, ScanResult result) {
		  
		
		//public void onScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
		  
			List<UUID> uuids = parseUuids(result.getScanRecord().getBytes());

			//
			for (UUID uuid : uuids) {
				if (uuid.compareTo(Secoder3_Service_UUID_NEW) == 0 || uuid.compareTo(DK_Service_UUID_OLD) == 0) {

					int x = deviceListStrings.indexOf(result.getDevice().getName());
					// replace device if already saved
					if (x != -1) {
						deviceList.remove(x);
						deviceListStrings.remove(x);
						deviceListStrings.add(result.getDevice().getName());
						deviceList.add(new Bluetooth_ReaderInfo (result.getDevice()));
						deviceListBt.add(result.getDevice());

					} else {
					    
						// add new devices
						deviceListStrings.add(result.getDevice().getName());
						deviceList.add(new Bluetooth_ReaderInfo (result.getDevice()));
						deviceListBt.add(result.getDevice());
					}

					if (deviceList.get(0) != null) {
						mCallback.onfound_BluetoothReader(deviceList);
					}
					break;
				}
			}
		}
	};

	/**
	 * Checks if is le capable.
	 *
	 * @return true, if is le capable
	 */
	public boolean isLeCapable()
	{
	    if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) 
	    {
		return true;
	    }
	    	return false;
	}
	
	/**
	 * Bond reader.
	 *
	 * @param id the id
	 */
	public void bondReader(String id)
	{
	    for(int x = 0; x < deviceListBt.size()+1; x++)
	    {
		
		BluetoothDevice device = deviceListBt.get(x);
		
		if(device.getAddress().equals(id)){
		    if(device.getBondState() ==  BluetoothDevice.BOND_BONDED)
		    {
			 mCallback.Bonded(new Bluetooth_ReaderInfo(device.getName(), device.getAddress(), true));
			 return;
		    }
		    else{
			deviceListBt.get(x).createBond();
			return;
		    }
		}
	    }
	}
	
	// The method, which checks the characteristics of the device found
	// It is to be called when a new Device is detected, hence in the method
	// "onLeScan"
	/**
	 * Parses the uuids.
	 *
	 * @param advertisedData the advertised data
	 * @return the list
	 */
	private List<UUID> parseUuids(byte[] advertisedData) {
		List<UUID> uuids = new ArrayList<UUID>();

		ByteBuffer buffer = ByteBuffer.wrap(advertisedData).order(
				ByteOrder.LITTLE_ENDIAN);
		while (buffer.remaining() > 2) {
			byte length = buffer.get();
			if (length == 0)
				break;

			byte type = buffer.get();
			switch (type) {
			case 0x02: // Partial list of 16-bit UUIDs
			case 0x03: // Complete list of 16-bit UUIDs
				while (length >= 2) {
					uuids.add(UUID.fromString(String.format(
							"%08x-0000-1000-8000-00805f9b34fb",
							buffer.getShort())));
					length -= 2;
				}
				break;

			case 0x06: // Partial list of 128-bit UUIDs
			case 0x07: // Complete list of 128-bit UUIDs
				while (length >= 16) {
					long lsb = buffer.getLong();
					long msb = buffer.getLong();
					uuids.add(new UUID(msb, lsb));
					length -= 16;
				}
				break;

			default:
				buffer.position(buffer.position() + length - 1);
				break;
			}
		}

		return uuids;
	}

}


