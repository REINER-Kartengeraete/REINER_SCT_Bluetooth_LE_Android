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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;


// TODO: Auto-generated Javadoc
/**
 * The Class SecoderInfoReaderProperties.
 */
public class SecoderInfoReaderProperties
{
    
    /** Quallifiers. */
	private SecoderReaderQuallifiers Quallifiers;
	
	/**
	 * get the Quallifiers.
	 *
	 * @return the quallifiers
	 */
	public final SecoderReaderQuallifiers getQuallifiers()
	{
		return Quallifiers;
	}
	
	/**
	 * set the Quallifiers.
	 *
	 * @param value the new quallifiers
	 */
	public final void setQuallifiers(SecoderReaderQuallifiers value)
	{
		Quallifiers = value;
	}

	/** the DisplayLineSize. */
	private byte DisplayLineSize;
	
	/**
	 * get the DisplayLineSize.
	 *
	 * @return the display line size
	 */
	public final byte getDisplayLineSize()
	{
		return DisplayLineSize;
	}
	
	/**
	 * set the DisplayLineSize.
	 *
	 * @param value the new display line size
	 */
	public final void setDisplayLineSize(byte value)
	{
		DisplayLineSize = value;
	}
	
	/** the DisplayLineNumber. */
	private byte DisplayLineNumber;
	
	
	/**
	 * get the DisplayLineNumber.
	 *
	 * @return the display line number
	 */
	public final byte getDisplayLineNumber()
	{
		return DisplayLineNumber;
	}
	
	/**
	 * set the DisplayLineNumber.
	 *
	 * @param value the new display line number
	 */
	public final void setDisplayLineNumber(byte value)
	{
		DisplayLineNumber = value;
	}
	
	/** the VisDataBuffer. */
	private byte VisDataBuffer;
	
	/**
	 * get the VisDataBuffer .
	 *
	 * @return the vis data buffer
	 */
	public final byte getVisDataBuffer()
	{
		return VisDataBuffer;
	}
	
	/**
	 * set the VisDataBuffer.
	 *
	 * @param value the new vis data buffer
	 */
	public final void setVisDataBuffer(byte value)
	{
		VisDataBuffer = value;
	}
	
	/** the MaxApduLenTransparent. */
	private int MaxApduLenTransparent;
	
	/**
	 * get the MaxApduLenTransparent.
	 *
	 * @return the max apdu len transparent
	 */
	public final int getMaxApduLenTransparent()
	{
		return MaxApduLenTransparent;
	}
	
	/**
	 * set the MaxApduLenTransparent.
	 *
	 * @param value the new max apdu len transparent
	 */
	public final void setMaxApduLenTransparent(int value)
	{
		MaxApduLenTransparent = value;
	}
	
	/** the MaxApduLenInternal. */
	private int MaxApduLenInternal;
	
	/**
	 * get the MaxApduLenInternal.
	 *
	 * @return the max apdu len internal
	 */
	public final int getMaxApduLenInternal()
	{
		return MaxApduLenInternal;
	}
	
	/**
	 * set the MaxApduLenInternal.
	 *
	 * @param value the new max apdu len internal
	 */
	public final void setMaxApduLenInternal(int value)
	{
		MaxApduLenInternal = value;
	}

	/**
	 * constructor for SecoderInfoReaderProperties.
	 *
	 * @param properties the properties
	 */
	public SecoderInfoReaderProperties(byte[] properties)
	{
		if (properties.length == 9)
		{
			setQuallifiers(new SecoderReaderQuallifiers(properties[0]));
			setDisplayLineSize(properties[1]);
			setDisplayLineNumber(properties[2]);
			setVisDataBuffer(properties[3]);

			byte[] temp = new byte[2];
			temp[0] = properties[4];
			temp[1] = properties[5];
			setMaxApduLenTransparent(ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN).getShort());
			temp[0] = properties[6];
			temp[1] = properties[7];
			setMaxApduLenInternal(ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN).getShort());
		}
		else
		{
			throw new RuntimeException("Not a Valid Value");
		}
	}
}