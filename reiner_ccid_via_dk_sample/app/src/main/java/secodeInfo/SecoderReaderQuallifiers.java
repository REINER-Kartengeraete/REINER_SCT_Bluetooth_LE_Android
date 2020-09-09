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

import utilitis.ByteOperations;


// TODO: Auto-generated Javadoc
/**
 * The Class SecoderReaderQuallifiers.
 */
public class SecoderReaderQuallifiers
{
    
    /** ReaderHasFullNumericKeyPad. */
	private boolean ReaderHasFullNumericKeyPad;
	
	/**
	 * Gets the reader has full numeric key pad.
	 *
	 * @return the reader has full numeric key pad
	 */
	public final boolean getReaderHasFullNumericKeyPad()
	{
		return ReaderHasFullNumericKeyPad;
	}
	
	/**
	 * Sets the reader has full numeric key pad.
	 *
	 * @param value the new reader has full numeric key pad
	 */
	public final void setReaderHasFullNumericKeyPad(boolean value)
	{
		ReaderHasFullNumericKeyPad = value;
	}
	
	/** ReaderHasTwoColumnDisplay. */
	private boolean ReaderHasTwoColumnDisplay;
	
	/**
	 * Gets the reader has two column display.
	 *
	 * @return the reader has two column display
	 */
	public final boolean getReaderHasTwoColumnDisplay()
	{
		return ReaderHasTwoColumnDisplay;
	}
	
	/**
	 * Sets the reader has two column display.
	 *
	 * @param value the new reader has two column display
	 */
	public final void setReaderHasTwoColumnDisplay(boolean value)
	{
		ReaderHasTwoColumnDisplay = value;
	}
	
	/** ReaderSupportsUSB. */
	private boolean ReaderSupportsUSB;
	
	/**
	 * Gets the reader supports usb.
	 *
	 * @return the reader supports usb
	 */
	public final boolean getReaderSupportsUSB()
	{
		return ReaderSupportsUSB;
	}
	
	/**
	 * Sets the reader supports usb.
	 *
	 * @param value the new reader supports usb
	 */
	public final void setReaderSupportsUSB(boolean value)
	{
		ReaderSupportsUSB = value;
	}
	
	/** ReaderSupportsBluetooth. */
	private boolean ReaderSupportsBluetooth;
	
	/**
	 * Gets the reader supports bluetooth.
	 *
	 * @return the reader supports bluetooth
	 */
	public final boolean getReaderSupportsBluetooth()
	{
		return ReaderSupportsBluetooth;
	}
	
	/**
	 * Sets the reader supports bluetooth.
	 *
	 * @param value the new reader supports bluetooth
	 */
	public final void setReaderSupportsBluetooth(boolean value)
	{
		ReaderSupportsBluetooth = value;
	}
	
	/** ReaderHasOpticalInterface. */
	private boolean ReaderHasOpticalInterface;
	
	/**
	 * Gets the reader has optical interface.
	 *
	 * @return the reader has optical interface
	 */
	public final boolean getReaderHasOpticalInterface()
	{
		return ReaderHasOpticalInterface;
	}
	
	/**
	 * Sets the reader has optical interface.
	 *
	 * @param value the new reader has optical interface
	 */
	public final void setReaderHasOpticalInterface(boolean value)
	{
		ReaderHasOpticalInterface = value;
	}
	
	/** ReaderSupportsManualEntryOfTransactionData. */
	private boolean ReaderSupportsManualEntryOfTransactionData;
	
	/**
	 * Gets the reader supports manual entry of transaction data.
	 *
	 * @return the reader supports manual entry of transaction data
	 */
	public final boolean getReaderSupportsManualEntryOfTransactionData()
	{
		return ReaderSupportsManualEntryOfTransactionData;
	}
	
	/**
	 * Sets the reader supports manual entry of transaction data.
	 *
	 * @param value the new reader supports manual entry of transaction data
	 */
	public final void setReaderSupportsManualEntryOfTransactionData(boolean value)
	{
		ReaderSupportsManualEntryOfTransactionData = value;
	}
	
	/** last byte. */
	private boolean LastByte;
	
	/**
	 * Gets the last byte.
	 *
	 * @return the last byte
	 */
	public final boolean getLastByte()
	{
		return LastByte;
	}
	
	/**
	 * Sets the last byte.
	 *
	 * @param value the new last byte
	 */
	public final void setLastByte(boolean value)
	{
		LastByte = value;
	}
	
	/** NextToLastByte. */
	private boolean NextToLastByte;
	
	/**
	 * Gets the next to last byte.
	 *
	 * @return the next to last byte
	 */
	public final boolean getNextToLastByte()
	{
		return NextToLastByte;
	}
	
	/**
	 * Sets the next to last byte.
	 *
	 * @param value the new next to last byte
	 */
	public final void setNextToLastByte(boolean value)
	{
		NextToLastByte = value;
	}

	/**
	 * constructor for SecoderReaderQuallifiers .
	 *
	 * @param info the info
	 */
	public SecoderReaderQuallifiers(byte info)
	{
		boolean[] bools = ByteOperations.getBitsFromByte(info);
		if (bools[0])
		{
			setReaderHasFullNumericKeyPad(bools[0]);
		}
		if (bools[1])
		{
			setReaderHasTwoColumnDisplay(bools[1]);
		}
		if (bools[2])
		{
			setReaderSupportsUSB(bools[2]);
		}
		if (bools[3])
		{
			setReaderSupportsBluetooth(bools[3]);
		}
		if (bools[4])
		{
			setReaderHasOpticalInterface(bools[4]);
		}
		if (bools[5])
		{
			setReaderSupportsManualEntryOfTransactionData(bools[5]);
		}
		if (bools[6])
		{
			setLastByte(bools[6]);
		}
		if (bools[7])
		{
			setNextToLastByte(bools[7]);
		}
	}
}