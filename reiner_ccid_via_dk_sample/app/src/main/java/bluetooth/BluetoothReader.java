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

// TODO: Auto-generated Javadoc
/**
 * The Class BluetoothReader.
 */
public abstract class BluetoothReader {

        /// <summary>
        /// connect to device with the identifier
        /// </summary>
        /// <param name="readerID"></param>
       /**
         * Connect.
         *
         * @param readerID the reader id
         */
        public abstract void Connect(String readerID);

        /// <summary>
        /// disconnect connected device, or the service
        /// </summary>
       /**
         * Dis connect.
         *
         * @param justDisconnectDevice the just disconnect device
         */
        public abstract void DisConnect(boolean justDisconnectDevice);

        /// <summary>
        /// send a 20byte long block
        /// </summary>
        /// <param name="block"></param>
       /**
         * Send commando.
         *
         * @param block the block
         */
        public abstract void SendCommando(byte[] block);

        /// <summary>
        /// scann for readers
        /// </summary>
        /// <param name="timeout"></param>
       /**
         * Scan readers.
         *
         * @param timeout the timeout
         */
        public abstract void ScanReaders(long timeout);

        /// <summary>
        /// bond the reader with the identifier
        /// </summary>
        /// <param name="readerID"></param>
       /**
         * Bond reader.
         *
         * @param readerID the reader id
         */
        public abstract void BondReader(String readerID);

        /// <summary>
        /// stop scanning for readers
        /// </summary>
       /**
         * Stop scaning.
         */
        public abstract void StopScaning();

        /// <summary>
        /// is the mobile device bluetooth Low Energy 4.0 capable
        /// </summary>
        /// <returns></returns>
       /**
         * Le capable.
         *
         * @return true, if successful
         */
        public abstract boolean LeCapable();
       
        /// <summary>
        /// returns the current GetBluetoothConnectionState()
        /// </summary>
        /// <returns></returns>
       /**
         * Gets the bluetooth connection state.
         *
         * @return the bluetooth connection state
         */
        public abstract BluetoothConnectionState GetBluetoothConnectionState();
       


}
