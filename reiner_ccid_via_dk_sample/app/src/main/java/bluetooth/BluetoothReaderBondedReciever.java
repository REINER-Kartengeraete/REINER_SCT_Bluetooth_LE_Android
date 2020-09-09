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
package bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// TODO: Auto-generated Javadoc
/**
 * The Class BluetoothReaderBondedReciever.
 */
public class BluetoothReaderBondedReciever extends BroadcastReceiver{
 

    	/** The callback. */
	    BluetoothReaderService callback;

        /**
         * Instantiates a new bluetooth reader bonded reciever.
         *
         * @param callback the callback
         */
        public BluetoothReaderBondedReciever(BluetoothReaderService callback)
        {
            this.callback = callback;
        }


	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
	    BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);

            if (state == 12)
            {
                if (device != null)
                {
                    if (callback != null)
                        callback.Bonded(new Bluetooth_ReaderInfo(device.getName(), device.getAddress(), true));
                }
            }
            if (state == 10)
            {
                if (callback != null)
                    callback.didRecieveError(BluetoothErrors.BluetoothPairingFailed, null);
            }
	}
}
