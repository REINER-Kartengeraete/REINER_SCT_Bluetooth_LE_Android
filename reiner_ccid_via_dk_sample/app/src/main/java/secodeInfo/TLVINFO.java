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
 * The Class TLVINFO.
 */
public class TLVINFO
{

/** the tag. */
	public int TAG;

/**
 * gets the tag.
 *
 * @return the tag
 */
	public final int getTAG()
	{
		return TAG;
	}

/**
 * sets the tag.
 *
 * @param value the new tag
 */
	public final void setTAG(int value)
	{
		TAG = value;
	}

/** the len. */
	public byte LEN;

/**
 * gets the len.
 *
 * @return the len
 */
	public final byte getLEN()
	{
		return LEN;
	}

/**
 * sets the len.
 *
 * @param value the new len
 */
	public final void setLEN(byte value)
	{
		LEN = value;
	}

/**
 * constructor for TLVINFO.
 *
 * @param tag the tag
 * @param len the len
 */
	public TLVINFO(int tag, byte len)
	{
		this.setTAG(tag);
		this.setLEN(len);
	}
}