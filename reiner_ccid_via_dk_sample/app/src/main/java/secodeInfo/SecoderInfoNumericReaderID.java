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

package secodeInfo;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class SecoderInfoNumericReaderID.
 */
public class SecoderInfoNumericReaderID
{
    	
	    /** the VendorID. */
	private byte[] VendorID;
	
	/**
	 * get thte VendorID.
	 *
	 * @return the vendor id
	 */
	public final byte[] getVendorID()
	{
		return VendorID;
	}
	
	/**
	 * set the VendorID.
	 *
	 * @param value the new vendor id
	 */
	public final void setVendorID(byte[] value)
	{
		VendorID = value;
	}

	/** ProductID. */
	private byte[] ProductID;

	/**
	 * get the ProductID.
	 *
	 * @return the product id
	 */
	public final byte[] getProductID()
	{
		return ProductID;
	}
	
	/**
	 * set the ProductID.
	 *
	 * @param value the new product id
	 */
	public final void setProductID(byte[] value)
	{
		ProductID = value;
	}
	
	/** SerialNumber. */
	private byte[] SerialNumber;
	
	/**
	 * get the SerialNumber.
	 *
	 * @return the serial number
	 */
	public final byte[] getSerialNumber()
	{
		return SerialNumber;
	}
	
	/**
	 * set the SerialNumber.
	 *
	 * @param value the new serial number
	 */
	public final void setSerialNumber(byte[] value)
	{
		SerialNumber = value;
	}
	
	/**
	 * constructor for SecoderInfoNumericReaderID.
	 *
	 * @param readerID the reader id
	 */
	public SecoderInfoNumericReaderID(byte[] readerID)
	{
	    
		setVendorID(Arrays.copyOfRange(readerID, 0, 3));
		setVendorID(Arrays.copyOfRange(readerID, 4, 7));
		setVendorID(Arrays.copyOfRange(readerID, 8, readerID.length));
	}
}