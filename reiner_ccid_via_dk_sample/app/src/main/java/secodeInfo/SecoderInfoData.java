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


// TODO: Auto-generated Javadoc
/**
 * The Class SecoderInfoData.
 */
public class SecoderInfoData
{
	
	/** The Identifier tlvinfo. */
	public static TLVINFO IdentifierTLVINFO = new TLVINFO((int)0x40, (byte)0x07);
	
	/** The Supported interface versions tlvinfo. */
	public static TLVINFO SupportedInterfaceVersionsTLVINFO = new TLVINFO((int)0x80,(byte) 0x05);
	
	/** The Hersteller name tlvinfo. */
	public static TLVINFO HerstellerNameTLVINFO = new TLVINFO((int)0x81,(byte) 0x00);
	
	/** The Reader properties tlvinfo. */
	public static TLVINFO ReaderPropertiesTLVINFO = new TLVINFO((int)0x91,(byte) 0x09);
	
	/** The Numeric reader idtlvinfo. */
	public static TLVINFO NumericReaderIDTLVINFO = new TLVINFO((int)0x92,(byte) 0x00);
	
	/** The Firmware version tlvinfo. */
	public static TLVINFO FirmwareVersionTLVINFO = new TLVINFO((int)0x83, (byte)0x00);
	
	/** The CSI specific supoorted character sets tlvinfo. */
	public static TLVINFO CSISpecificSupoortedCharacterSetsTLVINFO = new TLVINFO((int)0x90,(byte) 0x01);
	
	/** The Supported secoder applications tlvinfo. */
	public static TLVINFO SupportedSecoderApplicationsTLVINFO = new TLVINFO((int)0xA0, (byte)0x00);
	
	
	/** the identifier string. */
	public String Identifier;
	
	/**
	 * get the identifier string.
	 *
	 * @return the identifier
	 */
	public final String getIdentifier()
	{
		return Identifier;
	}
	
	/**
	 * set the identifier string.
	 *
	 * @param value the new identifier
	 */
	public final void setIdentifier(String value)
	{
		Identifier = value;
	}
	
	/** the SupportedInterfaceVersions. */
	public java.util.ArrayList<String> SupportedInterfaceVersions = new java.util.ArrayList<String>();
	
	/**
	 * get the SupportedInterfaceVersions.
	 *
	 * @return the supported interface versions
	 */
	public final java.util.ArrayList<String> getSupportedInterfaceVersions()
	{
		return SupportedInterfaceVersions;
	}
	
	/**
	 * set the SupportedInterfaceVersions.
	 *
	 * @param value the new supported interface versions
	 */
	public final void setSupportedInterfaceVersions(java.util.ArrayList<String> value)
	{
		SupportedInterfaceVersions = value;
	}
	
	/** the HerstellerName. */
	public String HerstellerName;
	
	/**
	 * gets the HerstellerName.
	 *
	 * @return the hersteller name
	 */
	public final String getHerstellerName()
	{
		return HerstellerName;
	}
	
	/**
	 * sets the HerstellerName.
	 *
	 * @param value the new hersteller name
	 */
	public final void setHerstellerName(String value)
	{
		HerstellerName = value;
	}
	
	/** the ReaderProperties. */
	public SecoderInfoReaderProperties ReaderProperties ;
	
	/**
	 * get the ReaderProperties.
	 *
	 * @return the reader properties
	 */
	public final SecoderInfoReaderProperties getReaderProperties()
	{
		return ReaderProperties;
	}
	
	/**
	 * set the ReaderProperties.
	 *
	 * @param value the new reader properties
	 */
	public final void setReaderProperties(SecoderInfoReaderProperties value)
	{
		ReaderProperties = value;
	}
	
	/** the NumericReaderID. */
	public SecoderInfoNumericReaderID NumericReaderID;
	
	/**
	 * get the NumericReaderID.
	 *
	 * @return the numeric reader id
	 */
	public final SecoderInfoNumericReaderID getNumericReaderID()
	{
		return NumericReaderID;
	}
	
	/**
	 * set the NumericReaderID.
	 *
	 * @param value the new numeric reader id
	 */
	public final void setNumericReaderID(SecoderInfoNumericReaderID value)
	{
		NumericReaderID = value;
	}
	
	/** the FirmwareVersion. */
	public String FirmwareVersion;
	
	/**
	 * get the FirmwareVersion.
	 *
	 * @return the firmware version
	 */
	public final String getFirmwareVersion()
	{
		return FirmwareVersion;
	}
	
	/**
	 * set the FirmwareVersion.
	 *
	 * @param value the new firmware version
	 */
	public final void setFirmwareVersion(String value)
	{
		FirmwareVersion = value;
	}
	
	/** the SupportedLanguages. */
	public java.util.ArrayList<String> SupportedLanguages = new java.util.ArrayList<String>();
	
	/**
	 * get the SupportedLanguages.
	 *
	 * @return the supported languages
	 */
	public final java.util.ArrayList<String> getSupportedLanguages()
	{
		return SupportedLanguages;
	}
	
	/**
	 * set the SupportedLanguages .
	 *
	 * @param value the new supported languages
	 */
	public final void setSupportedLanguages(java.util.ArrayList<String> value)
	{
		SupportedLanguages = value;
	}
	
	/** the CSISpecificSupoortedCharacterSets. */
	public java.util.ArrayList<String> CSISpecificSupoortedCharacterSets = new java.util.ArrayList<String>(); ;
	
	/**
	 * get the CSISpecificSupoortedCharacterSets.
	 *
	 * @return the CSI specific supoorted character sets
	 */
	public final java.util.ArrayList<String> getCSISpecificSupoortedCharacterSets()
	{
		return CSISpecificSupoortedCharacterSets;
	}
	
	/**
	 * set the CSISpecificSupoortedCharacterSets.
	 *
	 * @param value the new CSI specific supoorted character sets
	 */
	public final void setCSISpecificSupoortedCharacterSets(java.util.ArrayList<String> value)
	{
		CSISpecificSupoortedCharacterSets = value;
	}
	
	/** the SupportedSecoderApplications. */
	public java.util.ArrayList<SecoderApplications> SupportedSecoderApplications = new java.util.ArrayList<SecoderApplications>();
	
	/**
	 * get the SupportedSecoderApplications.
	 *
	 * @return the supported secoder applications
	 */
	public final java.util.ArrayList<SecoderApplications> getSupportedSecoderApplications()
	{
		return SupportedSecoderApplications;
	}
	
	/**
	 * set the SupportedSecoderApplications.
	 *
	 * @param value the new supported secoder applications
	 */
	public final void setSupportedSecoderApplications(java.util.ArrayList<SecoderApplications> value)
	{
		SupportedSecoderApplications = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
	    StringBuilder returnValue = new StringBuilder();
	    
	    returnValue.append("Identifier: " + Identifier + "\n");
	    
	    returnValue.append("SupportedInterfaceVersions\n");
	    for(String s :SupportedInterfaceVersions)
	    {
		 returnValue.append(s + "\t");
	    }
	    returnValue.append("Producer: " + HerstellerName + "\n");
	    returnValue.append("ReaderProperties: \n");
	    returnValue.append("MaxApduLenInternal: " + ReaderProperties.getMaxApduLenInternal() + "\n");
	    returnValue.append("MaxApduLenTransparent: " +ReaderProperties.getMaxApduLenTransparent() + "\n");
	    returnValue.append("DisplayLineSize: " + ReaderProperties.getDisplayLineSize() + "\n");
	    returnValue.append("DisplayLineNumber: " + ReaderProperties.getDisplayLineNumber() + "\n");
	    
	   /* returnValue.append("SupportedLanguages\n");
	    for(String s : SupportedLanguages)
	    {
		 returnValue.append(s + "\t");
	    }*/
	    
	    returnValue.append("FirmwareVersion: " + FirmwareVersion + "\n");
	    
	    returnValue.append("SupportedSecoderApplications\n");
	    for(SecoderApplications s : SupportedSecoderApplications)
	    {
		 returnValue.append(s.getApplicationID() + ", ");
	    }

	    
	    return returnValue.toString();
	}
	
}
