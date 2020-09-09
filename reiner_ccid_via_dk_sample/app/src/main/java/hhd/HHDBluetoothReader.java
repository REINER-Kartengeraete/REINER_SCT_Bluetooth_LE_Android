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
package hhd;

import java.util.List;
import secode3.SecoderBluetoothReader;
import secode3.SecoderReaderCallbacks;
import secodeInfo.SecoderInfoData;
import android.content.Context;
import bluetooth.BluetoothConnectionState;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;

// TODO: Auto-generated Javadoc
/**
 * The Class HHDBluetoothReader.
 */
public class HHDBluetoothReader extends HHDReader implements SecoderReaderCallbacks {

    /** The _callbacks. */
    HHDReaderCallbacks _callbacks;
    
    /** The _reader. */
    SecoderBluetoothReader _reader;
    
    /** The _ctx. */
    Context _ctx ;
    
    /** The hhd finalize. */
    String hhdFinalize;
    
    /**
     * Instantiates a new HHD bluetooth reader.
     *
     * @param callbacks the callbacks
     * @param ctx the ctx
     */
    public HHDBluetoothReader(HHDReaderCallbacks callbacks, Context ctx)
    {
	_callbacks = callbacks;
	_ctx = ctx;
	_reader = new SecoderBluetoothReader(this,_ctx);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#connect(java.lang.String)
     */
    @Override
    public void connect(String id) {
	_reader.connect(id);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#sendHHDCommand(java.lang.String, boolean)
     */
    @Override
    public void sendHHDCommand(String data, boolean hasFollowingTrasmittion){
	hhdFinalize = HHDProtocoll.Generatefinalize(hasFollowingTrasmittion);
	_reader.sendCommand(HHDProtocoll.generateHHDCommand(data), false);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#requestSecoderInfo()
     */
    @Override
    public void requestSecoderInfo() {
	_reader.requestSecoderInfo();
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#disConnect(boolean)
     */
    @Override
    public void disConnect(boolean justDisconnectDevice) {
	_reader.disConnect(justDisconnectDevice);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#scanReaders(long)
     */
    @Override
    public void scanReaders(long timeout) {
	_reader.scanReaders(timeout);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#bondReader(java.lang.String)
     */
    @Override
    public void bondReader(String readerID) {
	_reader.bondReader(readerID);
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#stopScaning()
     */
    @Override
    public void stopScaning() {
	_reader.stopScaning();
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#leCapable()
     */
    @Override
    public boolean leCapable() {
	return _reader.leCapable();
    }

    /* (non-Javadoc)
     * @see HHD.HHDReader#getBluetoothConnectionState()
     */
    @Override
    public BluetoothConnectionState getBluetoothConnectionState() {
	return _reader.getBluetoothConnectionState();
    }
    
    
    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveApdu(java.lang.String)
     */
    @Override
    public void didRecieveApdu(String answer) {
	if(answer.contains("010001"))
	{
	    try {
        		Thread.sleep(1000);
        	} catch (InterruptedException e) {
        		e.printStackTrace();
        	}
        _reader.disConnect(true);
	return;
	}
	_reader.sendCommand(hhdFinalize, false);
	_callbacks.didRecieveHHDAnswer(new HHDAnswer(answer));
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveSecoderInfo(secodeInfo.SecoderInfoData)
     */
    @Override
    public void didRecieveSecoderInfo(SecoderInfoData info) {
	_callbacks.didRecieveSecoderInfo(info);
    }

    /* (non-Javadoc)
     * @see secode3.SecoderReaderCallbacks#didRecieveResponseError(bluetooth.BluetoothErrors, java.lang.String)
     */
    @Override
    public void didRecieveResponseError(BluetoothErrors errorMessage, String respCode) {
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
