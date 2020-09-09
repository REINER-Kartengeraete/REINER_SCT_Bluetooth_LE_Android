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

import java.io.UnsupportedEncodingException;
import java.util.List;

import secodeInfo.TLV.TLVException;





// TODO: Auto-generated Javadoc
/**
 * The Class SecoderApplications.
 */
public class SecoderApplications
{
    	
	    /** ID off the application. */
	private String ApplicationID;
	
	/**
	 * get the Application ID.
	 *
	 * @return the application id
	 */
	public final String getApplicationID()
	{
		return ApplicationID;
	}
	
	/**
	 * set the applicationID.
	 *
	 * @param value the new application id
	 */
	public final void setApplicationID(String value)
	{
		ApplicationID = value;
	}
	
	/** the ApplicationInterfaceVersion. */
	private String ApplicationInterfaceVersion;
	
	/**
	 * gets the ApplicationInterfaceVersion.
	 *
	 * @return the application interface version
	 */
	public final String getApplicationInterfaceVersion()
	{
		return ApplicationInterfaceVersion;
	}
	
	/**
	 * sets the ApplicationInterfaceVersion.
	 *
	 * @param value the new application interface version
	 */
	public final void setApplicationInterfaceVersion(String value)
	{
		ApplicationInterfaceVersion = value;
	}
	
	/** the ApplicationImplementationVersion. */
	private String ApplicationImplementationVersion;
	
	/**
	 * gets the ApplicationImplementationVersion.
	 *
	 * @return the application implementation version
	 */
	public final String getApplicationImplementationVersion()
	{
		return ApplicationImplementationVersion;
	}
	
	/**
	 * sets the ApplicationImplementationVersion.
	 *
	 * @param value the new application implementation version
	 */
	public final void setApplicationImplementationVersion(String value)
	{
		ApplicationImplementationVersion = value;
	}
	
	/** some AdittionalData if available. */
	private String AdittionalData;
	
	/**
	 * gets the AdittionalData.
	 *
	 * @return the adittional data
	 */
	public final String getAdittionalData()
	{
		return AdittionalData;
	}
	
	/**
	 * sets the AdittionalData.
	 *
	 * @param value the new adittional data
	 */
	public final void setAdittionalData(String value)
	{
		AdittionalData = value;
	}
	
	
	/** The Application idtlvinfo. */
	public static TLVINFO ApplicationIDTLVINFO = new TLVINFO((int)0x40, (byte)0x07);
	
	/** The Application interface version tlvinfo. */
	public static TLVINFO ApplicationInterfaceVersionTLVINFO = new TLVINFO((int)0x80,(byte) 0x05);
	
	/** The Application implementation version tlvinfo. */
	public static TLVINFO ApplicationImplementationVersionTLVINFO = new TLVINFO((int)0x81, (byte)0x00);
	
	/** The Adittional data tlvinfo. */
	public static TLVINFO AdittionalDataTLVINFO = new TLVINFO((int)0x00, (byte)0x00);
	
	/**
	 * constructor for the secoder application data.
	 *
	 * @param data the data
	 */
	public SecoderApplications(byte[] data)
	{
	    
	    TLV tlv;
	    try {
		tlv = new TLV(data);
	  
	    
	    
	    List<TLV> tags =  tlv.getChildren();

            for(TLV tag : tags) 
            {
                if (tag.getTag() == SecoderApplications.ApplicationIDTLVINFO.TAG)
                {
                    ApplicationID = new String(tag.getValue(), "US-ASCII");
                }
                if (tag.getTag() == SecoderApplications.ApplicationInterfaceVersionTLVINFO.TAG)
                {
                    ApplicationInterfaceVersion = new String(tag.getValue(), "US-ASCII");
                }
                if (tag.getTag() == SecoderApplications.ApplicationImplementationVersionTLVINFO.TAG)
                {
                    ApplicationImplementationVersion = new String(tag.getValue(), "US-ASCII");
                }
                if (tag.getTag() == SecoderApplications.AdittionalDataTLVINFO.TAG)
                {
                    AdittionalData = new String(tag.getValue(), "US-ASCII");
                }
            }
            
	    } catch (TLVException e) {
		
		e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	
		e.printStackTrace();
	    }
	}
}