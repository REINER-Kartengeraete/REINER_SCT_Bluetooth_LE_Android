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
 * The Class HHDAnswer.
 */
public class HHDAnswer {
    
    /**
     * Gets the len tan.
     *
     * @return the len tan
     */
    public int getLenTAN() {
        return lenTAN;
    }


    /**
     * Gets the tan bcd.
     *
     * @return the tan bcd
     */
    public String getTAN_BCD() {
        return TAN_BCD;
    }


    /**
     * Gets the len atc.
     *
     * @return the len atc
     */
    public int getLenATC() {
        return lenATC;
    }


    /**
     * Gets the atc.
     *
     * @return the atc
     */
    public int getATC() {
        return ATC;
    }


    /**
     * Gets the len token.
     *
     * @return the len token
     */
    public int getLenTOKEN() {
        return lenTOKEN;
    }


    /**
     * Gets the token.
     *
     * @return the token
     */
    public String getTOKEN() {
        return TOKEN;
    }


    /**
     * Gets the len car d_ data.
     *
     * @return the len car d_ data
     */
    public int getLenCARD_DATA() {
        return lenCARD_DATA;
    }


    /**
     * Gets the card data ef id.
     *
     * @return the card data ef id
     */
    public String getCARD_DATA_EF_ID() {
        return CARD_DATA_EF_ID;
    }


    /**
     * Gets the len cv r_ data.
     *
     * @return the len cv r_ data
     */
    public int getLenCVR_DATA() {
        return lenCVR_DATA;
    }


    /**
     * Gets the cvr data.
     *
     * @return the cvr data
     */
    public String getCVR_DATA() {
        return CVR_DATA;
    }


    /**
     * Gets the len versioninfo.
     *
     * @return the len versioninfo
     */
    public int getLenVERSIONINFO() {
        return lenVERSIONINFO;
    }


    /**
     * Gets the versioninfo.
     *
     * @return the versioninfo
     */
    public String getVERSIONINFO() {
        return VERSIONINFO;
    }


	/** The len tan. */
	private int lenTAN ;
        
        /** The tan bcd. */
        private String TAN_BCD ;
        
        /** The len atc. */
        private int lenATC ;
        
        /** The atc. */
        private int ATC ;
        
        /** The len token. */
        private int lenTOKEN ;
        
        /** The token. */
        private String TOKEN ;
        
        /** The len car d_ data. */
        private int lenCARD_DATA ;
        
        /** The card data ef id. */
        private String CARD_DATA_EF_ID ;
        
        /** The len cv r_ data. */
        private int lenCVR_DATA ;
        
        /** The cvr data. */
        private String CVR_DATA ;
        
        /** The len versioninfo. */
        private int lenVERSIONINFO ;
        
        /** The versioninfo. */
        private String VERSIONINFO ;


        /**
         * Instantiates a new HHD answer.
         *
         * @param rsp the rsp
         */
        public HHDAnswer(String rsp)
        {
            try
            {
                int pointer = 0;

                rsp = ByteOperations.removeSpacesFromString(rsp);
                
                lenTAN = Integer.parseInt(rsp.substring(0, 2), 16) * 2;
                pointer += 2;
                TAN_BCD = rsp.substring(pointer, pointer + lenTAN);
                pointer += lenTAN;

                lenATC = Integer.parseInt(rsp.substring(pointer, pointer + 2), 16) * 2;
                pointer += 2;
                ATC = Integer.parseInt(rsp.substring(pointer, pointer + lenATC), 16);
                pointer += lenATC;

                lenTOKEN = Integer.parseInt(rsp.substring(pointer, pointer + 2), 16) * 2;
                pointer += 2;
                TOKEN = rsp.substring(pointer, pointer + lenTOKEN);
                pointer += lenTOKEN;

                lenCARD_DATA = Integer.parseInt(rsp.substring(pointer, pointer + 2), 16) * 2;
                pointer += 2;
                CARD_DATA_EF_ID = rsp.substring(pointer, pointer + lenCARD_DATA);
                pointer += lenCARD_DATA;

                lenCVR_DATA = Integer.parseInt(rsp.substring(pointer, pointer + 2), 16) * 2;
                pointer += 2;
                CVR_DATA = rsp.substring(pointer, pointer + lenCVR_DATA);
                pointer += lenCVR_DATA;

                lenVERSIONINFO = Integer.parseInt(rsp.substring(pointer, pointer + 2), 16) * 2;
                pointer += 2;
                VERSIONINFO = rsp.substring(pointer, pointer + lenVERSIONINFO);
                pointer += lenVERSIONINFO;
            }
            catch (Exception ex)
            {

            }


        }

    }


