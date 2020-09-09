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
 * The Interface BluetoothReaderCallbacks.
 */
public interface BluetoothReaderCallbacks extends BluetoothReaderConnectionCallbacks{
    
    /// <summary>
    /// called when the reader is connected and ready to send
    /// </summary>
    /**
     * Ready to send.
     */
    void readyToSend(); 

    /// <summary>
    /// called after a block was send
    /// </summary>
    /**
     * Did send.
     */
    void didSend();

    /// <summary>
    /// called if a block was recieved from the remote device
    /// </summary>
    /// <param name="block"></param>
    /**
     * Did recieve block.
     *
     * @param block the block
     */
    void didRecieveBlock(byte[] block);


    /// <summary>
    /// only for SAP Protiocoll
    /// </summary>
    /// <param name="block"></param>
    /**
     * Did recieve status.
     *
     * @param block the block
     */
    void didRecieveStatus(byte[] block); 

    /// <summary>
    /// called if an error accoured, errorMessage explaines some errors, the block returns a failure byte array for the error description
    /// </summary>
    /// <param name="errorMessage"></param>
    /// <param name="block"></param>
    /**
     * Did recieve error.
     *
     * @param errorMessage the error message
     * @param block the block
     */
    void didRecieveError(BluetoothErrors errorMessage, byte[] block);

    /// <summary>
    /// called after the disconnect
    /// </summary>
    /// <param name="unhingedService"></param>
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
