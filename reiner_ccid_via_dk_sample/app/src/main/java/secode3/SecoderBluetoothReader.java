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
package secode3;

import java.util.ArrayList;
import java.util.List;

import secodeInfo.SecoderInfo;
import utilitis.ByteOperations;

import android.content.Context;
import bluetooth.*;



// TODO: Auto-generated Javadoc
/**
 * The Class SecoderBluetoothReader.
 */
public class SecoderBluetoothReader  extends SecoderReader implements BluetoothReaderCallbacks{
    
        /** The _card reader. */
        private static BluetoothReader _cardReader;
        
        /** The _callbacks. */
        private static SecoderReaderCallbacks _callbacks;
        
        /** The recieved. */
        private List<byte[]> recieved = new ArrayList<byte[]>();
        
        /** The send. */
        private List<byte[]> send = new ArrayList<byte[]>();
        
        /** The send counter max. */
        private int sendCounterMax = 0;
        
        /** The send counter. */
        private int sendCounter = 0;


        /** The first block. */
        private static boolean firstBlock = true;
        
        /** The first not ready to send. */
        private static boolean firstNotReadyToSend = false;

        /** The blocks to wait for. */
        private int blocksToWaitFor = 0;
        
        /** The errorblock. */
        private int errorblock = -1;
        
        /** The cmd sequenz. */
        private int cmdSequenz = 1;

        /** The waiting for secoder info. */
        private boolean waitingForSecoderInfo = false;
        
        /** The _ctx. */
        private Context _ctx ;
        
        /** The didsendLastBlock. */
        private boolean didsendLastBlock = false;
        
        /** The didRecieveLastBlock. */
        private boolean didRecieveLastBlock = false;


        /**
         * Instantiates a new secoder bluetooth reader.
         *
         * @param callbacks the callbacks
         * @param ctx the ctx
         */
        public SecoderBluetoothReader(SecoderReaderCallbacks callbacks, Context ctx)
        {
            _callbacks = callbacks;
            _ctx = ctx;
            _cardReader = new BluetoothReaderService( _ctx , this );
        }

        /* (non-Javadoc)
         * @see secode3.SecoderReader#sendAPDU(java.lang.String, boolean)
         */
        @Override
        public void sendCommand(String data, boolean transparrent)
        {
        	recieved = new ArrayList<byte[]>();
        	send = new ArrayList<byte[]>();
            sendCounterMax = 0;
            sendCounter = 0;
            firstBlock = true;
            didsendLastBlock = false;
            didRecieveLastBlock = false;
            
            try {
            if(transparrent)
            {
        	send = SecoderProtocoll.BuildTransParentSendBlocks(data);
            }
            else{
        	send = SecoderProtocoll.BuildSendBlocks(data, cmdSequenz);
            }
            
            sendCounterMax = send.size();
            if (_cardReader.GetBluetoothConnectionState() == BluetoothConnectionState.StableConnected)
            {
                _cardReader.SendCommando(send.get(0));
            }
            else
            {
                firstNotReadyToSend = true;
            }  
            
            } catch (Exception e) {
        	_callbacks.didRecieveResponseError(BluetoothErrors.ProtocollError, null);
		e.printStackTrace();
	    }
        }

        /**
         * Reset.
         */
        private void reset()
        {
            sendCounterMax = 0;
            sendCounter = 0;
            blocksToWaitFor = 0;
            errorblock = -1;
            cmdSequenz = 1;
            firstBlock = true;
            firstNotReadyToSend = false;
        }

        /* (non-Javadoc)
         * @see secode3.SecoderReader#disConnect(boolean)
         */
        @Override
        public void disConnect(boolean justDisconnectDevice)
        {
            reset();
            _cardReader.DisConnect(justDisconnectDevice);
        }

        /* (non-Javadoc)
         * @see secode3.SecoderReader#connect(java.lang.String)
         */
        @Override
        public void connect(String id)
        {
            _cardReader.Connect(id);
        }

        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#readyToSend()
         */
        @Override
        public void readyToSend()
        {
             _callbacks.readyToSend();

            if (firstNotReadyToSend)
            {
        	if(send.size() > 0)
        	    _cardReader.SendCommando(send.get(0));
            }
        }
        
        /* (non-Javadoc)
         * @see secode3.SecoderReader#scanReaders(long)
         */
        @Override
        public void scanReaders(long timeout)
        {
            _cardReader.ScanReaders(timeout);
        }
        
        /* (non-Javadoc)
         * @see secode3.SecoderReader#requestSecoderInfo()
         */
        @Override
        public void requestSecoderInfo()
        {
            waitingForSecoderInfo = true;
            sendCommand(SecoderInfo.GetSecoderInfoCommandString, false);
        }
        
        /* (non-Javadoc)
         * @see secode3.SecoderReader#bondReader(java.lang.String)
         */
        @Override
        public void bondReader(String readerID)
        {
            _cardReader.BondReader(readerID);
        }
        
        /* (non-Javadoc)
         * @see secode3.SecoderReader#stopScaning()
         */
        @Override
        public  void stopScaning()
        {
            _cardReader.StopScaning();
        }
        
        /* (non-Javadoc)
         * @see secode3.SecoderReader#leCapable()
         */
        @Override
        public boolean leCapable()
        {
            return _cardReader.LeCapable();
        }

        /**
         * Send recieved callback.
         */
        private void SendRecievedCallback() 
        {
        	
        	try {
                if (waitingForSecoderInfo == true)
                {
                    _callbacks.didRecieveSecoderInfo(new SecoderInfo(SecoderProtocoll.HandleInput(recieved)).Data);
                }
                else
                {
                    if(recieved != null){
                	_callbacks.didRecieveApdu(SecoderProtocoll.HandleInput(recieved));
                    }
                } 
                } catch (Exception ex) {
                    String error = ex.getMessage();
                    firstBlock = true;

                    if (error.contains("Packet Missing"))
                    {
                        error = error.replace("Packet Missing", "");
                        errorblock = Integer.parseInt(error);
                        recieved.remove(0);
                        sendCounter = errorblock;
                        _cardReader.SendCommando(send.get(sendCounter));
                    }
                    else
                    {
                        error = error.replace("Card Error", "");
                        _callbacks.didRecieveResponseError(BluetoothErrors.CardRelatedError, error);
                    }
                }
        }

        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#didSend()
         */
        @Override
        public void didSend()
        {
            sendCounter++;
            if (sendCounterMax > sendCounter)
            {
                _cardReader.SendCommando(send.get(sendCounter));
            }
            else
            {
        	didsendLastBlock = true; 
                cmdSequenz++;
                if(didRecieveLastBlock || sendCounterMax == 1)
                {
                    SendRecievedCallback();
                }
            }
        }



        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#didRecieveBlock(byte[])
         */
        @Override
        public synchronized void didRecieveBlock(byte[] block)
        {
            recieved.add(block);
            
            try
            {
        	
                if (firstBlock)
                {
                    firstBlock = false;
                    blocksToWaitFor = SecoderProtocoll.CheckHeader(block);
                    
                    if(blocksToWaitFor == 0)
                    {
                	didRecieveLastBlock = true;
                	if(didsendLastBlock){
                	    SendRecievedCallback();
                	}
                	waitingForSecoderInfo = false;
                    }
                }
                
                else
                {
                    if(blocksToWaitFor + 1 == recieved.size())
                    {
                	didRecieveLastBlock = true;
                	if(didsendLastBlock)
                	{
                            SendRecievedCallback();
                	}
                            waitingForSecoderInfo = false;
                    }
                }
            }
            catch (Exception ex)
            {
        	   String error = ex.getMessage();
                   firstBlock = true;

                   if (error.contains("Packet Missing"))
                   {
                       error = error.replace("Packet Missing", "");
                       errorblock = Integer.parseInt(error);
                       sendCounter = errorblock;
                       _cardReader.SendCommando(send.get(sendCounter));
                   }
                   else
                   {
                       error = error.replace("Card Error", "");
                       _callbacks.didRecieveResponseError(BluetoothErrors.CardRelatedError, error);
                   }
             
            }
        }
        
        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#didRecieveStatus(byte[])
         */
        @Override
        public void didRecieveStatus(byte[] block)
        {
        }
        
        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#didRecieveError(bluetooth.BluetoothErrors, byte[])
         */
        @Override
        public void didRecieveError(BluetoothErrors errorMessage, byte[] block)
        {
            if (block != null)
                _callbacks.didRecieveResponseError(errorMessage, ByteOperations.byteArrayToHexString(block));
            else
                _callbacks.didRecieveResponseError(errorMessage, null);
        }

        
        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderConnectionCallbacks#Bonded(bluetooth.Bluetooth_ReaderInfo)
         */
        @Override
        public void Bonded(Bluetooth_ReaderInfo info)
        {
            _callbacks.Bonded(info);
        }
        
        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderConnectionCallbacks#onScanningFinished()
         */
        @Override
        public void onScanningFinished()
        {
            _callbacks.onScanningFinished();
        }

        /* (non-Javadoc)
         * @see bluetooth.BluetoothReaderCallbacks#disconnected()
         */
        @Override
        public void disconnected()
        {
            _callbacks.disconnected();
        }

	/* (non-Javadoc)
	 * @see bluetooth.BluetoothReaderConnectionCallbacks#onfound_BluetoothReader(java.util.List)
	 */
	@Override
	public void onfound_BluetoothReader(List<Bluetooth_ReaderInfo> devices) {
	    if(_cardReader.GetBluetoothConnectionState() == BluetoothConnectionState.Scanning)
		_callbacks.didFindReaders(devices);
	}

	/* (non-Javadoc)
	 * @see bluetooth.BluetoothReaderCallbacks#initiated()
	 */
	@Override
	public void initiated() {
	    _callbacks.initiated();
	}


	/* (non-Javadoc)
	 * @see secode3.SecoderReader#getBluetoothConnectionState()
	 */
	@Override
	public BluetoothConnectionState getBluetoothConnectionState() {
	    return _cardReader.GetBluetoothConnectionState();
	}


	
}
