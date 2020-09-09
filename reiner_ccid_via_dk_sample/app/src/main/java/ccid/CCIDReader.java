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
package ccid;

import utilitis.AtrParser;
import ccid.CCIDProtocoll.CCIDSecureReturnType;
import ccid.CCIDProtocoll.CardVoltage;
import ccid.CCIDProtocoll.PINOperation;
import ccid.CCIDProtocoll.TransportProtocol;
import bluetooth.BluetoothConnectionState;

// TODO: Auto-generated Javadoc
/**
 * The Class CCIDReader.
 */
public abstract class CCIDReader {

    /**
     * Connect.
     *
     * @param id the id
     */
    public abstract void connect(String id);
    
    /**
     * Power card on.
     */
    public abstract void powerCardOn(CardVoltage voltage, byte slot );
    
    /**
     * Power card off.
     */
    public abstract void powerCardOff( byte slot );
    
    /**
     * Gets the slot status.
     *
     * @return the slot status
     */
    public abstract void getSlotStatus( byte slot );
    
    /**
     * Secure.
     *
     * @param returntype the returntype
     * @param operation the operation
     * @param pinVerificationDataStructure the pin verification data structure
     */
    public abstract void secure(CCIDSecureReturnType returntype, PINOperation operation,String pinVerificationDataStructure, byte slot );
    
    /**
     * Send xfr block.
     *
     * @param data the data
     */
    public abstract void sendXfrBlock(String data, byte slot );


    /**
     * Send esc block.
     *
     * @param data the data
     */
    public abstract void sendESCBlock(String data, byte slot );
    
    /**
     * Send setParameter block.
     *
     * @param data the data
     * @param AtrParser the parsed ATR
     */
    
    public abstract void setParameter(TransportProtocol protocol, AtrParser parser, byte slot );
    
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
