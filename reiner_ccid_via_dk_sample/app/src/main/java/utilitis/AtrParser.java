/*
* 
*   
* 
*   Created by Steve Spormann on 20.10.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       1.03.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/

package utilitis;

public class AtrParser 
{
    private boolean hasTA1 = false;
    private byte TA1 = 0x11;
    private byte IFSC = 0x20;
    private byte TC1 = 0;
    private byte TC2 = 10;
    private byte TB3 = 0x45;
    private byte TD1 = 0;


   private byte protocol = 0x01;
    
    
   public boolean getHasTA1() 
   {
        return hasTA1;
   }

    public void setHasTA1(boolean hasTA1) {
        this.hasTA1 = hasTA1;
    }

    public byte getTA1() {
        return TA1;
    }

    public void setTA1(byte tA1) {
        TA1 = tA1;
    }

    public byte getIFSC() {
        return IFSC;
    }

    public void setIFSC(byte iFSC) {
        IFSC = iFSC;
    }

    public byte getTC1() {
        return TC1;
    }

    public void setTC1(byte tC1) {
        TC1 = tC1;
    }

    public byte getTC2() {
        return TC2;
    }

    public void setTC2(byte tC2) {
        TC2 = tC2;
    }

    public byte getTB3() {
        return TB3;
    }

    public void setTB3(byte tB3) {
        TB3 = tB3;
    }

    public byte getTD1() {
        return TD1;
    }

    public void setTD1(byte tD1) {
        TD1 = tD1;
    }

    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }

   public static AtrParser parseATR(String atr)
   {
       return parseATR(ByteOperations.hexStringToByteArray(atr));
   }

   public static AtrParser parseATR( byte[] atr)
   {
       
       AtrParser parser = new AtrParser();
       if(atr[0]==0x3b || atr[0]==0x3f)
           {
                 int ptr = 2;
                
                 if(atr[ptr] != (byte)0x10 )
                 {
                     parser.TA1=atr[ptr++];
                     parser.hasTA1=true;
                 }
                 if(atr[ptr] != (byte)0x20)
                 {
                        ptr++;
                 }
                 if(atr[ptr] != (byte)0x40)
                 {
                     parser.TC1=atr[2];
                 }
                 if(atr[ptr] != (byte)0x80)
                 {
                     parser.protocol=(byte) ((byte)1 << (byte)(atr[3] & (byte)0x0f));
                     parser.TD1=atr[ptr++];
                     
                        if(atr[ptr] != (byte)0x10)
                        {
                            ptr++;
                        }
                        if(atr[ptr] != (byte)0x20)
                        {
                               ptr++;
                        }
                        if(atr[ptr] != (byte)0x40)
                        {
                            parser.TC2=atr[ptr++];
                        }
                        if(atr[ptr] != (byte)0x80)
                        {
                               if((atr[ptr] & (byte)0x0f)!=1)
                               {
                                      if(atr[ptr] != (byte)0x10)
                                      {
                                	  parser.IFSC=atr[ptr++];
                                      }
                                      if(atr[ptr] != (byte)0x20)
                                      {
                                	  parser.TB3=atr[ptr];
                                            ptr++;
                                      }
                               }
                        }
                 }
              
           }
       
       return parser;
   
    }

    
    
}
