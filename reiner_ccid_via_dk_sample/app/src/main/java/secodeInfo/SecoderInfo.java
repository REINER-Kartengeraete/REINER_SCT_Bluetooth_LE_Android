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


import utilitis.ByteOperations;


// TODO: Auto-generated Javadoc
/**
 * The Class SecoderInfo.
 */
public class SecoderInfo
{

	/** String to get secoder info. */
		public static String GetSecoderInfoCommandString = "20 70 00 00 00 00 01 00 00 00";
		
		/** The actual secoder info data. */
		public SecoderInfoData Data = new SecoderInfoData();
		
		/**
		 *  constructor with string.
		 *
		 * @param data the data
		 */
		public SecoderInfo(String data)
		{
			Data = ParseSecoderInfo(ByteOperations.hexStringToByteArray(data));
		}

		/**
		 *  constructor with java.util.ArrayList<byte[]>
		 *
		 * @param data the data
		 */
		public SecoderInfo(java.util.List<byte[]> data)
		{
		    
		    StringBuilder builder = new StringBuilder();
		    
		    for(byte[] b :data)
			builder.append(ByteOperations.byteArrayToHexString(b));
			
		    byte[] hex = ByteOperations.hexStringToByteArray(builder.toString());
		    Data = ParseSecoderInfo(hex);
		}
		
		/**
		 *  constructor with byte[].
		 *
		 * @param data the data
		 */
		public SecoderInfo(byte[] data)
		{
			Data = ParseSecoderInfo(data);
		}
		
		/**
		 * parses the secoder info data.
		 *
		 * @param data the data
		 * @return the secoder info data
		 */
		private final SecoderInfoData ParseSecoderInfo(byte[] data)
		{
		    TLV tlv;
		    try {
			
			tlv = new TLV(data);
			List<TLV> tags =  tlv.getChildren();

	            for(TLV tag : tags) 
	            {
	                if (tag.getTag() == SecoderInfoData.IdentifierTLVINFO.TAG) 
	                {
	                    Data.Identifier= new String(tag.getValue(), "US-ASCII");
	                }
	                if (tag.getTag() == SecoderInfoData.SupportedInterfaceVersionsTLVINFO.TAG)
	                {
	                    Data.SupportedInterfaceVersions.add ( new String(tag.getValue(), "US-ASCII"));
	                }
	                if (tag.getTag() == SecoderInfoData.HerstellerNameTLVINFO.TAG)
	                {
	                    Data.HerstellerName = new String(tag.getValue(), "US-ASCII");
	                }
	                if (tag.getTag() == SecoderInfoData.ReaderPropertiesTLVINFO.TAG)
	                {
	                    Data.ReaderProperties = new SecoderInfoReaderProperties(tag.getValue());
	                }
	                if (tag.getTag() == SecoderInfoData.NumericReaderIDTLVINFO.TAG)
	                {
	                    Data.NumericReaderID = new SecoderInfoNumericReaderID(tag.getValue());
	                }
	                if (tag.getTag() == SecoderInfoData.FirmwareVersionTLVINFO.TAG)
	                {
	                    Data.FirmwareVersion = new String(tag.getValue(), "US-ASCII");
	                }
	                if (tag.getTag() == SecoderInfoData.CSISpecificSupoortedCharacterSetsTLVINFO.TAG)
	                {
	                    Data.CSISpecificSupoortedCharacterSets.add(new String(tag.getValue(), "US-ASCII"));
	                }
	                if (tag.getTag() == SecoderInfoData.SupportedSecoderApplicationsTLVINFO.TAG)
	                {
	                    Data.SupportedSecoderApplications.add(new SecoderApplications(tag.getValue()));
	                }
	            }
	            
		    } catch (secodeInfo.TLV.TLVException e) {
			
			e.printStackTrace();
		    } catch (UnsupportedEncodingException e) {
		
			e.printStackTrace();
		    }
			return Data;
		}
}




	
	
	
	
	
