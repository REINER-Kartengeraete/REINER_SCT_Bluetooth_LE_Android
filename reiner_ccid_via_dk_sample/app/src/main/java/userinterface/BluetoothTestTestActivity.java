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
package userinterface;

import android.app.Activity;

// TODO: Auto-generated Javadoc
/**
 * The Interface BluetoothTestTestActivity.
 */
public interface BluetoothTestTestActivity 
{
    
    /**
     * Open device selection.
     */
    void openDeviceSelection();
    
    /**
     * Start with selected reader.
     * @throws InterruptedException 
     */
    void startWithSelectedReader();
    
    /**
     * Start with closest reader.
     */
    void startWithClosestReader();
    

}
