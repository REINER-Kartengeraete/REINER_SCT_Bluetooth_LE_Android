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

import java.util.List;

import secodeInfo.SecoderInfoData;

import bluetooth.*;



// TODO: Auto-generated Javadoc
/**
 * The Interface SecoderReaderCallbacks.
 */
public interface SecoderReaderCallbacks {


       /// <summary>
       /// called if an apdu was rescieved
       /// </summary>
       /// <param name="answer"></param>
        /**
        * Did recieve apdu.
        *
        * @param answer the answer
        */
       void didRecieveApdu(String  answer);

       /// <summary>
       /// called if secoderinfo was recieved
       /// </summary>
       /// <param name="info"></param>
        /**
        * Did recieve secoder info.
        *
        * @param info the info
        */
       void didRecieveSecoderInfo(SecoderInfoData info);

       /// <summary>
       /// called if an error was rescieved
       /// </summary>
       /// <param name="errorMessage"></param>
       /// <param name="respCode"></param>
        /**
        * Did recieve response error.
        *
        * @param errorMessage the error message
        * @param respCode the resp code
        */
       void didRecieveResponseError(BluetoothErrors errorMessage, String respCode);


       /// <summary>
       /// called if readers are found called multiple times if readers get detected
       /// </summary>
       /// <param name="devices"></param>
        /**
        * Did find readers.
        *
        * @param devices the devices
        */
       void didFindReaders(List<Bluetooth_ReaderInfo> devices);


       /// <summary>
       /// called after a sucsessfull bond
       /// </summary>
        /**
        * Bonded.
        *
        * @param info the info
        */
       void Bonded(Bluetooth_ReaderInfo info);

       /// <summary>
       /// if scaning timed out 
       /// </summary>
        /**
        * On scanning finished.
        */
       void onScanningFinished();

       /// <summary>
       /// called after the disconnect, if service was killed 
       /// </summary>
       /**
        * Disconnected.
        */
       void disconnected();
       
       /// <summary>
       /// called if the service is initiated
       /// </summary>
       /// <returns></returns>
       /**
        * Initiated.
        */
       void initiated();
       
       /// <summary>
       /// called when reader is ready to recieve
       /// </summary>
       /// <returns></returns>
       /**
        * Ready to send.
        */
       void readyToSend();


}
