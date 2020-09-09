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

import java.util.List;

import ccid.CCIDProtocoll.CCIDSecureReturnType;
import ccid.CCIDProtocoll.CardVoltage;
import ccid.CCIDProtocoll.PINOperation;
import ccid.CCIDProtocoll.TransportProtocol;

import secode3.SecoderBluetoothReader;
import secode3.SecoderReaderCallbacks;
import secodeInfo.SecoderInfoData;
import utilitis.AtrParser;
import utilitis.ByteOperations;
import android.content.Context;
import bluetooth.BluetoothConnectionState;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class CCIDBluetoothReader.
 */
public class CCIDBluetoothReader extends CCIDReader implements SecoderReaderCallbacks{

    /** The _callbacks. */
    private CCIDReaderCallbacks _callbacks;
    
    /** The _reader. */
    private SecoderBluetoothReader _reader;
    
    /** The _ctx. */
    private Context _ctx;
    
    /** The sequence number */
    private int sqn = 0;
    
    
    private void increaseSequenceNumber()
    {
	if(sqn >= 255){sqn = 0;}sqn++;
    }
    /**
     * Instantiates a new CCID bluetooth reader.
     *
     * @param callbacks the callbacks
     * @param ctx the ctx
     */
    public CCIDBluetoothReader(CCIDReaderCallbacks callbacks, Context ctx)
    {
	_callbacks = callbacks;
	_ctx = ctx;
	_reader = new SecoderBluetoothReader(this,_ctx);
    }
    
    /* (non-Javadoc)
     * @see ccid.CCIDReader#connect(java.lang.String)
     */
    @Override
    public void connect(String id) {
	_reader.connect(id);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#disConnect(boolean)
     */
    @Override
    public void disConnect(boolean justDisconnectDevice) {
	_reader.disConnect(justDisconnectDevice);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#scanReaders(long)
     */
    @Override
    public void scanReaders(long timeout) {
	_reader.scanReaders(timeout);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#bondReader(java.lang.String)
     */
    @Override
    public void bondReader(String readerID) {
	_reader.bondReader(readerID);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#stopScaning()
     */
    @Override
    public void stopScaning() {
	_reader.stopScaning();
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#leCapable()
     */
    @Override
    public boolean leCapable() {
	return _reader.leCapable();
    }
    
    /* (non-Javadoc)
     * @see ccid.CCIDReader#getBluetoothConnectionState()
     */
    @Override
    public BluetoothConnectionState getBluetoothConnectionState() {
	return _reader.getBluetoothConnectionState();
    }
    
    /* (non-Javadoc)
     * @see ccid.CCIDReader#powerCardOn()
     */
    @Override
    public void powerCardOn(CardVoltage voltage, byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generatePowerOn(sqn,voltage, slot), true);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#powerCardOff()
     */
    @Override
    public void powerCardOff( byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generatePowerOff(sqn, slot), true);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#getSlotStatus()
     */
    @Override
    public void getSlotStatus(byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generateGetSlotStatus(sqn, slot), true);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#sendXfrBlock(java.lang.String)
     */
    @Override
    public void sendXfrBlock(String data,  byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generateXferBlock(data, sqn, slot), true);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#sendESCBlock(java.lang.String)
     */
    @Override
    public void sendESCBlock(String data,  byte slot) {
        increaseSequenceNumber();
        _reader.sendCommand(CCIDProtocoll.generateESCBlock(data, sqn, slot), true);
    }
    
    /* (non-Javadoc)
     * @see ccid.CCIDReader#setParameter(TransportProtocol)
     */
    @Override
    public void setParameter(TransportProtocol protocol, AtrParser parser,  byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generateSetParameters(protocol, sqn, parser, slot), true);
    }

    /* (non-Javadoc)
     * @see ccid.CCIDReader#secure(ccid.CCIDProtocoll.CCIDSecureReturnType, ccid.CCIDProtocoll.PINOperation, java.lang.String)
     */
    @Override
    public void secure(CCIDSecureReturnType returntype, PINOperation operation,
	    String pinVerificationDataStructure, byte slot) {
	increaseSequenceNumber();
	_reader.sendCommand(CCIDProtocoll.generateGetSecure(pinVerificationDataStructure, sqn, returntype, operation, slot), true);
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveApdu(java.lang.String)
     */
    @Override
    public void didRecieveApdu(String answer) {
    			_callbacks.didRecieveCCID_DataBlock(CCIDProtocoll.parseCCIDResponse(ByteOperations.hexStringToByteArray(answer)));
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveResponseError(bluetooth.BluetoothErrors, java.lang.String)
     */
    @Override
    public void didRecieveResponseError(BluetoothErrors errorMessage,
	    String respCode) {
	_callbacks.didRecieveResponseError(errorMessage, respCode);
	
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didFindReaders(java.util.List)
     */
    @Override
    public void didFindReaders(List<Bluetooth_ReaderInfo> devices) {
	_callbacks.didFindReaders(devices);
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#Bonded(bluetooth.Bluetooth_ReaderInfo)
     */
    @Override
    public void Bonded(Bluetooth_ReaderInfo info) {
	_callbacks.Bonded(info);
	
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#onScanningFinished()
     */
    @Override
    public void onScanningFinished() {
	_callbacks.onScanningFinished();
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#disconnected()
     */
    @Override
    public void disconnected() {
	_callbacks.disconnected();
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveSecoderInfo(secodeInfo.SecoderInfoData)
     */
    @Override
    public void didRecieveSecoderInfo(SecoderInfoData info) {	
		// Noting to do here
	
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#initiated()
     */
    @Override
    public void initiated() {

	_callbacks.initiated();
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#readyToSend()
     */
    @Override
    public void readyToSend() {
	_callbacks.readyToSend();
    }




}
