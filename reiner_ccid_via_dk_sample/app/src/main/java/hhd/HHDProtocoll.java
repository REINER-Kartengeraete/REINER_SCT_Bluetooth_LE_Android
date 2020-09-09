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
package hhd;

import utilitis.ByteOperations;

// TODO: Auto-generated Javadoc
/**
 * The Class HHDProtocoll.
 */
public class HHDProtocoll {
    
    /**
     * Generate hhd command.
     *
     * @param apdu the apdu
     * @return the string
     */
    public static String generateHHDCommand(String apdu)
    {
        apdu = apdu.replace(" ", "");
        apdu = "20 76 00 00 00 00 " + ByteOperations.IntToHEXString((apdu.length() / 2), 1, true) + apdu + " 00 00";
        return apdu;
    }

    /**
     * Generatefinalize.
     *
     * @param following the following
     * @return the string
     */
    public static String Generatefinalize(boolean following)
    {
        String FINALIZE_DONE = "20 77 00 00 00 00 06 00 00 00 00 00 00 00 00 ";
        String FINALIZE_FOLLOWING = "20 77 00 00 00 00 06 00 00 00 00 01 00 00 00";

        if (following)
        {
            return FINALIZE_DONE;
        }
        else
        {
            return FINALIZE_FOLLOWING;
        }
    }

}
