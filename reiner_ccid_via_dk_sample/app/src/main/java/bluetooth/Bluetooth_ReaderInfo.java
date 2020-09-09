/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       1.03.2017
*   eMail:      mobile-sdk@reiner-sct.com
*/
package bluetooth;

import android.bluetooth.BluetoothDevice;


    // TODO: Auto-generated Javadoc
/// <summary>
    /// class to store Bluetooth readers 
    /// </summary>
/**
     * The Class Bluetooth_ReaderInfo.
     */
    public class Bluetooth_ReaderInfo {

      /** The Reader id. */
      private String ReaderID;
      
      /** The Reader name. */
      private String ReaderName ;
      
      /** The Bonded. */
      private boolean Bonded ;
      
      /**
       * Instantiates a new bluetooth_ reader info.
       */
      public Bluetooth_ReaderInfo()
      {    
          this.ReaderName = "no reader found";
          this.ReaderID = "";
          this.Bonded = false;
       }

      /// <summary>
      /// constructor
      /// </summary>
      /// <param name="name"></param>
      /// <param name="id"></param>
      /// <param name="bonded"></param>
      /**
       * Instantiates a new bluetooth_ reader info.
       *
       * @param name the name
       * @param id the id
       * @param bonded the bonded
       */
      public Bluetooth_ReaderInfo(String name, String id, boolean bonded)
      {
          this.ReaderName = name;
          this.ReaderID = id;
          this.Bonded = bonded;
      }
      
      /**
       * Instantiates a new bluetooth_ reader info.
       *
       * @param device the device
       */
      public Bluetooth_ReaderInfo(BluetoothDevice device)
      {
          this.ReaderName = device.getName();
          this.ReaderID = device.getAddress();
          this.Bonded = device.getBondState() == BluetoothDevice.BOND_BONDED ? true : false  ;
      }
      
      /**
       * Gets the reader id.
       *
       * @return the reader id
       */
      public String getReaderID() {
	        return ReaderID;
	    }

	    /**
    	 * Sets the reader id.
    	 *
    	 * @param readerID the new reader id
    	 */
    	public void setReaderID(String readerID) {
	        ReaderID = readerID;
	    }

	    /**
    	 * Gets the reader name.
    	 *
    	 * @return the reader name
    	 */
    	public String getReaderName() {
	        return ReaderName;
	    }

	    /**
    	 * Sets the reader name.
    	 *
    	 * @param readerName the new reader name
    	 */
    	public void setReaderName(String readerName) {
	        ReaderName = readerName;
	    }

	    /**
    	 * Checks if is bonded.
    	 *
    	 * @return true, if is bonded
    	 */
    	public boolean isBonded() {
	        return Bonded;
	    }

	    /**
    	 * Sets the bonded.
    	 *
    	 * @param bonded the new bonded
    	 */
    	public void setBonded(boolean bonded) {
	        Bonded = bonded;
	    }
      
    }

