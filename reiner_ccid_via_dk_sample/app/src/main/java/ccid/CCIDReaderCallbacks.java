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
import ccid.CCIDProtocoll.CCID_AnswerBlock;
import bluetooth.BluetoothErrors;
import bluetooth.Bluetooth_ReaderInfo;


// TODO: Auto-generated Javadoc
/**
 * The Interface CCIDReaderCallbacks.
 */
public interface CCIDReaderCallbacks {

    /// <summary>
    /// called if connection is stable
    /// </summary>
    /**
     * Ready to send.
     */
    void readyToSend();


    /// <summary>
    /// called if a n apdu was send
    /// </summary>
     /**
     * Did send apdu.
     */
    void didSendApdu();

    /// <summary>
    /// called if an apdu was rescieved
    /// </summary>
    /// <param name="answer"></param>
     /**
     * Did recieve cci d_ data block.
     *
     * @param ccid_AnswerBlock the ccid_ answer block
     */
    void didRecieveCCID_DataBlock(CCID_AnswerBlock  ccid_AnswerBlock);     

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


}
