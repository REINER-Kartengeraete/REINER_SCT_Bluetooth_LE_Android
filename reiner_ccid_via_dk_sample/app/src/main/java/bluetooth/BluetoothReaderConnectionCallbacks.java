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

import java.util.List;


    // TODO: Auto-generated Javadoc
/**
     * The Interface BluetoothReaderConnectionCallbacks.
     */
    public interface BluetoothReaderConnectionCallbacks
    {
        /// <summary>
        /// list of devices foaund while scanning 
        /// called if new device is found
        /// </summary>
        /// <param name="devices"></param>
        /**
         * Onfound_ bluetooth reader.
         *
         * @param devices the devices
         */
        void onfound_BluetoothReader(List<Bluetooth_ReaderInfo> devices);

        /// <summary>
        /// called if device is bonded
        /// </summary>
        /**
         * Bonded.
         *
         * @param info the info
         */
        void Bonded(Bluetooth_ReaderInfo info);

        /// <summary>
        /// called if scanning timeout runns out
        /// </summary>
        /**
         * On scanning finished.
         */
        void onScanningFinished();
    }


