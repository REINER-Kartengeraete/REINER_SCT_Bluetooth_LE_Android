/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
*   Version:    0.5.3
*   Date:       1.03.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package hhd;

import bluetooth.BluetoothConnectionState;

// TODO: Auto-generated Javadoc
/**
 * The Class HHDReader.
 */
public abstract class HHDReader {


        /**
         * Connect.
         *
         * @param id the id
         */
        public abstract void connect(String id);
        
        /**
         * Send hhd command.
         *
         * @param data the data
         * @param hasFollowingTrasmittion the has following trasmittion
         */
        public abstract void sendHHDCommand(String data, boolean hasFollowingTrasmittion);
        
        /**
         * Request secoder info.
         */
        public abstract void requestSecoderInfo();
        
        /**
         * Dis connect.
         *
         * @param justDisconnectDevice the just disconnect device
         */
        public abstract void disConnect(boolean justDisconnectDevice);
        
        /**
         * Scan readers.
         *
         * @param timeout the timeout
         */
        public abstract void scanReaders(long timeout);
        
        /**
         * Bond reader.
         *
         * @param readerID the reader id
         */
        public abstract void bondReader(String readerID);
        
        /**
         * Stop scaning.
         */
        public abstract void stopScaning();
        
        /**
         * Le capable.
         *
         * @return true, if successful
         */
        public abstract boolean leCapable();
        
        /**
         * Gets the bluetooth connection state.
         *
         * @return the bluetooth connection state
         */
        public abstract BluetoothConnectionState getBluetoothConnectionState();

}
