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
 * The Class SecoderApplicationsCapabilitys.
 */
public class SecoderApplicationsCapabilitys
{
    
    /**  the last byte. */
	private boolean lastbyte;
	
	/**
	 * gets the last byte.
	 *
	 * @return the lastbyte
	 */
	public final boolean getlastbyte()
	{
		return lastbyte;
	}
	
	/**
	 * sets the last byte.
	 *
	 * @param value the new lastbyte
	 */
	public final void setlastbyte(boolean value)
	{
		lastbyte = value;
	}
	
	/** ICCardComandsBlockedCompletely value. */
	private boolean ICCardComandsBlockedCompletely;
	
	/**
	 * gets the ICCardComandsBlockedCompletely value.
	 *
	 * @return the IC card comands blocked completely
	 */
	public final boolean getICCardComandsBlockedCompletely()
	{
		return ICCardComandsBlockedCompletely;
	}
	
	/**
	 * sets the ICCardComandsBlockedCompletely value.
	 *
	 * @param value the new IC card comands blocked completely
	 */
	public final void setICCardComandsBlockedCompletely(boolean value)
	{
		ICCardComandsBlockedCompletely = value;
	}
	
	/** the PinHandlingFunktionsBlockedCompletely value. */
	private boolean PinHandlingFunktionsBlockedCompletely;
	
	/**
	 * gets the PinHandlingFunktionsBlockedCompletely value.
	 *
	 * @return the pin handling funktions blocked completely
	 */
	public final boolean getPinHandlingFunktionsBlockedCompletely()
	{
		return PinHandlingFunktionsBlockedCompletely;
	}
	
	/**
	 * sets PinHandlingFunktionsBlockedCompletely value.
	 *
	 * @param value the new pin handling funktions blocked completely
	 */
	public final void setPinHandlingFunktionsBlockedCompletely(boolean value)
	{
		PinHandlingFunktionsBlockedCompletely = value;
	}
	
	/** the rfu values. */
	public java.util.ArrayList<Boolean> RFU;
	
	/**
	 * gets the rfu values.
	 *
	 * @return the rfu
	 */
	public final java.util.ArrayList<Boolean> getRFU()
	{
		return RFU;
	}
	
	/**
	 * sets the rfu values.
	 *
	 * @param value the new rfu
	 */
	public final void setRFU(java.util.ArrayList<Boolean> value)
	{
		RFU = value;
	}
}