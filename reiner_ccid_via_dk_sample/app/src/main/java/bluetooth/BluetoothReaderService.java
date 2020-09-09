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

import bluetooth.AndroidBluetoothService.LocalBinder;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;


// TODO: Auto-generated Javadoc
/**
 * The Class BluetoothReaderService.
 */
public class BluetoothReaderService extends BluetoothReader implements BluetoothReaderCallbacks {
 
    /** The ctx. */
    private Context ctx = null;
    
    /** The callback. */
    private BluetoothReaderCallbacks callback = null;
    
    /** The m service. */
    private AndroidBluetoothService mService;
    
    /** The m bound. */
    private boolean mBound = false;
    
    /** The reciever. */
    private BluetoothReaderBondedReciever reciever;
    
    /** The state. */
    private BluetoothConnectionState state = BluetoothConnectionState.Disconnected;
    
    
    /**
     * Instantiates a new bluetooth reader service.
     *
     * @param ctx the ctx
     * @param callback the callback
     */
    public BluetoothReaderService(Context ctx, BluetoothReaderCallbacks callback)
    {
	this.ctx = ctx; 
	this.callback = callback;
	reciever = new BluetoothReaderBondedReciever(this);
	ctx.registerReceiver(reciever, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
	Intent intent = new Intent(ctx, AndroidBluetoothService.class);
	ctx.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
    
    
    /**  Defines callbacks for service binding, passed to bindService(). */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            initiated();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    
    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#initiated()
     */
    @Override
    public void initiated() {
	mService.SetCallbacks(this);
	callback.initiated();
    }
    
    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderConnectionCallbacks#Bonded(bluetooth.Bluetooth_ReaderInfo)
     */
    @Override
    public void Bonded(Bluetooth_ReaderInfo info) {
	callback.Bonded(info);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderConnectionCallbacks#onScanningFinished()
     */
    @Override
    public void onScanningFinished() {
	callback.onScanningFinished();
	state = BluetoothConnectionState.Disconnected;
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#readyToSend()
     */
    @Override
    public void readyToSend() {
	callback.readyToSend();
	state = BluetoothConnectionState.StableConnected;
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#didSend()
     */
    @Override
    public void didSend() {
	callback.didSend();
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#didRecieveBlock(byte[])
     */
    @Override
    public void didRecieveBlock(byte[] block) {
	callback.didRecieveBlock(block);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#didRecieveStatus(byte[])
     */
    @Override
    public void didRecieveStatus(byte[] block) {
	callback.didRecieveStatus(block);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#didRecieveError(bluetooth.BluetoothErrors, byte[])
     */
    @Override
    public void didRecieveError(BluetoothErrors errorMessage, byte[] block) {
	callback.didRecieveError(errorMessage, block);
	state = BluetoothConnectionState.Disconnecting;
    }
    
    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderConnectionCallbacks#onfound_BluetoothReader(java.util.List)
     */
    @Override
    public void onfound_BluetoothReader(List<Bluetooth_ReaderInfo> devices) {
	if(state != BluetoothConnectionState.Bonding && state != BluetoothConnectionState.Connecting)
	    callback.onfound_BluetoothReader(devices);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReaderCallbacks#disconnected()
     */
    @Override
    public void disconnected() {
	
	 if (mBound)
	 {
	     ctx.unbindService(mConnection);
	     mBound = false;
	 }
	 state = BluetoothConnectionState.Disconnected;
	 callback.disconnected();
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#Connect(java.lang.String)
     */
    @Override
    public void Connect(String readerID) {
	state = BluetoothConnectionState.Connecting;
	if(mService != null)  
	mService.connectToDevice(readerID);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#DisConnect(boolean)
     */
    @Override
    public void DisConnect(boolean justDisconnectDevice) {
	state = BluetoothConnectionState.Disconnecting;
	mService.disconnect(justDisconnectDevice);
	
	if(!justDisconnectDevice){
	    ctx.stopService(new Intent(ctx, AndroidBluetoothService.class));
	    ctx.unbindService(mConnection);
	}
	    
	ctx.unregisterReceiver(reciever);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#SendCommando(byte[])
     */
    @Override
    public void SendCommando(byte[] block){
	mService.send(block);
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#ScanReaders(long)
     */
    @Override
    public void ScanReaders(long timeout) {
	state = BluetoothConnectionState.Scanning;
	mService.scanForReaders(timeout);
    }
    
    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#BondReader(java.lang.String)
     */
    @Override
    public void BondReader(String readerID) {
	state = BluetoothConnectionState.Bonding;
	mService.bondReader(readerID);
    }
    
    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#StopScaning()
     */
    @Override
    public void StopScaning() {
	state = BluetoothConnectionState.Disconnected;
	mService.stopScanForReaders();
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#LeCapable()
     */
    @Override
    public boolean LeCapable() {
	return mService.isLeCapable();
    }

    /* (non-Javadoc)
     * @see bluetooth.BluetoothReader#GetBluetoothConnectionState()
     */
    @Override
    public BluetoothConnectionState GetBluetoothConnectionState() {
	return state;
    }
}
