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
package ccid;

import utilitis.AtrParser;
import utilitis.ByteOperations;

// TODO: Auto-generated Javadoc
/**
 * The Class CCIDProtocoll.
 */
public class CCIDProtocoll {
    
    //select main file if a card
    /** The Constant getRootCommand. */
    public final static String getRootCommand = "00 A4 00 00 02 3f 00 00"; 
    
    /** The Constant Device_to_RDR_IccSetParameter. */
    public final static String Device_to_RDR_IccSetParameter = "61";
   
    /** The Constant Device_to_RDR_IccPowerOn. */
    public final static String Device_to_RDR_IccPowerOn = "62";
    
    /** The Constant Device_to_RDR_IccPowerOff. */
    public final static String Device_to_RDR_IccPowerOff = "63";
    
    /** The Constant Device_to_RDR_GetSlotStatus. */
    public final static String Device_to_RDR_GetSlotStatus = "65";
    
    /** The Constant Device_to_RDR_XfrBlock. */
    public final static String Device_to_RDR_XfrBlock = "6F";

	/** The Constant Device_to_RDR_ESCBlock. */
	public final static String Device_to_RDR_ESCBlock = "6B";
    
    
    /** The Constant Device_to_RDR_Secure. */
    public final static String Device_to_RDR_Secure  = "69";
    
    /** The Constant RDR_to_DEVICE_DataBlock. */
    public final static String RDR_to_DEVICE_DataBlock = "80";   // PC_to_RDR_IccPowerOn, PC_to_RDR_XfrBlock PC_to_RDR_Secure
    
    /** The Constant RDR_to_DEVICE_SlotStatus. */
    public final static String RDR_to_DEVICE_SlotStatus ="81";  // PC_to_RDR_IccPowerOff, PC_to_RDR_GetSlotStatus, PC_to_RDR_Mechanical, PC_to_RDR_T0APDUPC_to_RDR_Abort and Class specific ABORT request
    
    /** The Constant RDR_to_DEVICE_SlotStatus. */
    public final static String RDR_to_DEVICE_Parameter ="82";  // PC_to_RDR_IccPowerOff, PC_to_RDR_GetSlotStatus, PC_to_RDR_Mechanical, PC_to_RDR_T0APDUPC_to_RDR_Abort and Class specific ABORT request

	/** The Constant RDR_to_DEVICE_SlotStatus. */
	public final static String RDR_to_DEVICE_ESCAPE ="83";

    /**
     * The Enum CCIDSecureReturnType.
     */
    public enum CCIDSecureReturnType {
	
   	/** The tpdu. */
	   TPDU ("00 00 "),
   	
	   /** The apdu. */
	   APDU  ("00 00 "),
   	
	   /** The apdu beginns or ends. */
	   APDU_BEGINNS_OR_ENDS ("00 00 "),
   	
	   /** The apdu beginns and continues. */
	   APDU_BEGINNS_AND_CONTINUES  ("00 01 "),
        
           /** The apdu continues and ends. */
           APDU_CONTINUES_AND_ENDS  ("00 01 ");
           
           
           /** The value. */
           private final String value;   
           
           /**
            * Instantiates a new CCID secure return type.
            *
            * @param value the value
            */
           private CCIDSecureReturnType(String value)
   	{
   	    this.value = value;
   	}
           
   	/**
	    * Gets the value.
	    *
	    * @return the value
	    */
	   public String getValue() {
   	    return value;
   	}    
   }
 
 /**
  * The Enum PINOperation.
  */
 public enum PINOperation {
	
   	/** The pin verification. */
	   PIN_VERIFICATION ("00"),
   	
	   /** The pin modification. */
	   PIN_MODIFICATION  ("01"),
   	
	   /** The transphere pin. */
	   TRANSPHERE_PIN ("02"),
   	
	   /** The wait icc response. */
	   WAIT_ICC_RESPONSE  ("03"),
        
            /** The cancel pin funktion. */
            CANCEL_PIN_FUNKTION  ("04"),
            
            /** The resend last block. */
            RESEND_LAST_BLOCK  ("05"),
            
            /** The send nextpart. */
            SEND_NEXTPART  ("06");
           
           
           /** The value. */
           private final String value;   
           
           /**
            * Instantiates a new PIN operation.
            *
            * @param value the value
            */
           private PINOperation(String value)
   	{
   	    this.value = value;
   	}
           
   	/**
	    * Gets the value.
	    *
	    * @return the value
	    */
	   public String getValue() {
   	    return value;
   	}    
   }
    
 public enum CardVoltage {
	
	
     AUTOMATIC_CARD_VOLTAGE ("00"),// not supported in cyberJack wave readers
     VOLTS_5_CARD_VOLTAGE ("01"),
     VOLTS_3_CARD_VOLTAGE ("02"),
     VOLTS_1_8_CARD_VOLTAGE ("03");
     
     
        /** The value. */
        private final String value;   
        
        /**
         * Instantiates a new CardVoltage.
         *
         * @param value the value
         */
        private CardVoltage(String value)
	{
	    this.value = value;
	}
        
	/**
	    * Gets the value.
	    *
	    * @return the value
	    */
	   public String getValue() {
	    return value;
	}    
}
 
 
 public enum TransportProtocol {
	
	
     T_Equals_0 ("00"),
     T_Equals_1 ("01"),
     Automatic ( "FF") ;
 
     
        /** The value. */
        private final String value;   
        
        /**
         * Instantiates a new TransportProtocol.
         *
         * @param value the value
         */
        private TransportProtocol(String value)
	{
	    this.value = value;
	}
        
	/**
	    * Gets the value.
	    *
	    * @return the value
	    */
	   public String getValue() {
	    return value;
	}    
}
    
    /**
     * The Enum CCIDReturnBlockType.
     */
    public enum CCIDReturnBlockType {
	
   	/** The data block. */
	   DATA_BLOCK (RDR_to_DEVICE_DataBlock),
   	
	   /** The slot status. */
	   SLOT_STATUS  (RDR_to_DEVICE_SlotStatus),
	   
	   /** The parameter status. */
	   PARAMETER_BLOCK (RDR_to_DEVICE_Parameter),

		/** The esc block. */
		ESC_BLOCK (RDR_to_DEVICE_ESCAPE);
   
        /** The value. */
        private final String value;   
        
        /**
         * Instantiates a new CCID return block type.
         *
         * @param value the value
         */
        private CCIDReturnBlockType(String value)
   	{
   	    this.value = value;
   	}
        
           
   	/**
	    * Gets the value.
	    *
	    * @return the value
	    */
	   public String getValue() {
   	    return value;
   	}    
   	
   	/**
	    * Gets the byte value.
	    *
	    * @return the byte value
	    */
	   public byte getByteValue() {
   	    return ByteOperations.hexStringToByteArray(value)[0];
   	}    
   }
    
    /**
     * The Class CCID_AnswerBlock.
     */
    public static class CCID_AnswerBlock
    {
	public CCID_DataBlock dataBlock;
	
	public CCID_SlotStausBlock slotStatus;
	
	public CCID_ParameterBlock parameterBlock;

		public CCID_ESCBlock escBlock;
	
	public CCID_ParameterBlock getParameterBlock() {
	    return parameterBlock;
	}

	protected CCID_AnswerBlock (){};
	
	/** The length. */
	protected int length;
	
	/** The slot. */
	protected int slot;
	
	/** The cmd sequence. */
	protected int cmdSequence;
	
	/** The slotstatus. */
	protected int slotstatus;
	
	/** The slot error. */
	protected int slotError;
	
	/** The chain parameter. */
	protected int chainParameter;
	
	/** The commando data. */
	protected String commandoData;
	
	/** The clock status. */
	protected int clockStatus;
	
	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
	    return length;
	}

	/**
	 * Gets the slot.
	 *
	 * @return the slot
	 */
	public int getSlot() {
	    return slot;
	}

	/**
	 * Gets the cmd sequence.
	 *
	 * @return the cmd sequence
	 */
	public int getCmdSequence() {
	    return cmdSequence;
	}

	/**
	 * Gets the slotstatus.
	 *
	 * @return the slotstatus
	 */
	public int getSlotstatus() {
	    return slotstatus;
	}

	/**
	 * Gets the slot error.
	 *
	 * @return the slot error
	 */
	public int getSlotError() {
	    return slotError;
	}
	
	
	/**
	 * Instantiates a new CCI d_ answer block.
	 *
	 * @param block the block
	 */
	public CCID_AnswerBlock(byte[] block)
	{
	    if(block[0] == CCIDReturnBlockType.DATA_BLOCK.getByteValue())
	    {
		dataBlock = new CCID_DataBlock(block);
	    }
	    else if(block[0] == CCIDReturnBlockType.SLOT_STATUS.getByteValue())
	    {
		slotStatus = new CCID_SlotStausBlock(block);
	    }
	    else if(block[0] == CCIDReturnBlockType.PARAMETER_BLOCK.getByteValue())
		{
			parameterBlock = new CCID_ParameterBlock(block);
		}
		else if(block[0] == CCIDReturnBlockType.ESC_BLOCK.getByteValue())
		{
			escBlock = new CCID_ESCBlock(block);
		}
	};
    }

    
    /**
     * The Class CCID_DataBlock.
     */
    public static class CCID_DataBlock extends CCID_AnswerBlock
    {

	/**
	 * Gets the chain parameter.
	 *
	 * @return the chain parameter
	 */
	public int getChainParameter() {
	    return chainParameter;
	}

	/**
	 * Gets the commando data.
	 *
	 * @return the commando data
	 */
	public String getCommandoData() {
	    return commandoData;
	}

	
	/**
	 * Instantiates a new CCI d_ data block.
	 *
	 * @param block the block
	 */
	public CCID_DataBlock(byte[] block)
	{
	    super();
	    length = ByteOperations.byteArrayToInt(new byte[]{block[4],block[3],block[2],block[1]});
	    slot = (int)block[5];
	    cmdSequence = (int)block[6];
	    slotstatus = (int)block[7];
	    slotError = (int)block[8];
	    chainParameter = (int)block[9];
	    
	    byte[] data = ByteOperations.getBytesWithRange(13, block.length -13 , block);
	    commandoData = ByteOperations.byteArrayToHexString(data);
	}
    }

	/**
	 * The Class CCID_DataBlock.
	 */
	public static class CCID_ESCBlock extends CCID_AnswerBlock
	{

		/**
		 * Gets the chain parameter.
		 *
		 * @return the chain parameter
		 */
		public int getChainParameter() {
			return chainParameter;
		}

		/**
		 * Gets the commando data.
		 *
		 * @return the commando data
		 */
		public String getCommandoData() {
			return commandoData;
		}


		/**
		 * Instantiates a new CCI d_ data block.
		 *
		 * @param block the block
		 */
		public CCID_ESCBlock(byte[] block)
		{
			super();
			length = ByteOperations.byteArrayToInt(new byte[]{block[4],block[3],block[2],block[1]});
			slot = (int)block[5];
			cmdSequence = (int)block[6];
			slotstatus = (int)block[7];
			slotError = (int)block[8];
			chainParameter = (int)block[9];

			byte[] data = ByteOperations.getBytesWithRange(13, block.length -13 , block);
			commandoData = ByteOperations.byteArrayToHexString(data);
		}
	}


    /**
     * The Class CCID_DataBlock.
     */
    public static class CCID_ParameterBlock extends CCID_AnswerBlock
    {

	/**
	 * Instantiates a new CCI d_ data block.
	 *
	 * @param block the block
	 */
	public CCID_ParameterBlock(byte[] block)
	{
	    super();
	    length = ByteOperations.byteArrayToInt(new byte[]{block[4],block[3],block[2],block[1]});
	    slot = (int)block[5];
	    cmdSequence = (int)block[6];
	    slotstatus = (int)block[7];
	    slotError = (int)block[8];
	    chainParameter = (int)block[9];
	    
	    byte[] data = ByteOperations.getBytesWithRange(13, block.length -13 , block);
	    commandoData = ByteOperations.byteArrayToHexString(data);
	}
    }
    
    /**
     * The Class CCID_SlotStausBlock.
     */
    public static class CCID_SlotStausBlock extends CCID_AnswerBlock
    {
	
	/** The clock status. */
	private int clockStatus;
	

	/**
	 * Gets the clock status.
	 *
	 * @return the clock status
	 */
	public int getClockStatus() {
	    return clockStatus;
	}

	
	/**
	 * Instantiates a new CCId_ slot staus block.
	 *
	 * @param block the block
	 */
	public CCID_SlotStausBlock(byte[] block)
	{
	    super();
	    length = ByteOperations.byteArrayToInt(new byte[]{block[4],block[3],block[2],block[1]});
	    slot = (int)block[5];
	    cmdSequence = (int)block[6];
	    slotstatus = (int)block[7];
	    slotError = (int)block[8];
	    clockStatus = (int)block[9];
	}

}
	private static String byteToString(byte b) {
		return String.format("%02X ", b);
	}

    /**
     * Generate power on.
     *
     * @param seq the seq
     * @return the string
     */
    public static String generatePowerOn(int seq, CardVoltage voltage, byte slot)
    {
	return (Device_to_RDR_IccPowerOn + " 00 00 00 00 " + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true)    + voltage.getValue() +      "00 00 ");
	// 	command 			length	     slot     sequence number				 voltage selection           RFu	     
    }
    
    /**
     * Generate power off.
     *
     * @param seq the seq
     * @return the string
     */
    public static String generatePowerOff(int seq, byte slot )
    {
	return (Device_to_RDR_IccPowerOff + " 00 00 00 00 " + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2,true) +    "00 00 00 ");
	// 	command 			length	     slot       sequence number			          RFu	     
    }
    
    /**
     * Generate get slot status.
     *
     * @param seq the seq
     * @return the string
     */
    public static String generateGetSlotStatus(int seq, byte slot)
    {
	return (Device_to_RDR_GetSlotStatus + " 00 00 00 00 " + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    "00 00 00 ");
	// 	command 			length	     slot       sequence number			          RFu	     
    }
    
 	/**
	 * Generate xfer block.
	 *
	 * @param data the data
	 * @param seq the seq
	 * @return the string
	 */
	public static String generateXferBlock(String data, int seq, byte slot)
	{
		data = data.replace(" ", "");
		return (Device_to_RDR_XfrBlock + ByteOperations.IntToHEXString(data.length() / 2, 8,true) + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    "00 "    +          "00 00 "                   +    data);
		// 	command 			length	     					slot        sequence number			  timeout  	expected return length           send block
	}

	/**
	 * Generate esc block.
	 *
	 * @param data the data
	 * @param seq the seq
	 * @return the string
	 */
	public static String generateESCBlock(String data, int seq, byte slot)
	{
		data = data.replace(" ", "");
		return (Device_to_RDR_ESCBlock + ByteOperations.IntToHEXString(data.length() / 2, 8,true) + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    "00 "    +          "00 00 "                   +    data);
		// 	command 			length	     					slot        sequence number			  timeout  	expected return length           send block
	}



	/**
     * Generate get secure.
     *
     * @param data the data
     * @param seq the seq
     * @param returntype the returntype
     * @param operation the operation
     * @return the string
     */
    public static String generateGetSecure(String data, int seq, CCIDSecureReturnType returntype, PINOperation operation, byte slot)
    {
	data = data.replace(" ", "");
	return (Device_to_RDR_Secure + ByteOperations.IntToHEXString(data.length() / 2, 8, true) + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    "FF "    +          returntype.getValue() + operation.getValue()  +    data);
	// 	command 			length	     				     slot        sequence number			timeout              returntype	  		 PINOperation	   send block
    }
    
    
    
    public static String generateSetParameters(TransportProtocol protocol, int seq, AtrParser atr, byte slot)
    {
	
	if(protocol == TransportProtocol.Automatic ){
	    
		if(atr.getProtocol() == 0x01)
		{
		    protocol = TransportProtocol.T_Equals_1;
		}
		else
		{
		    protocol = TransportProtocol.T_Equals_0;
		}
	}
	
	
	if(protocol == TransportProtocol.T_Equals_1 ){
		
	    byte[] dataByte = new byte[7];
	   
	    dataByte[0] = atr.getTA1();
	    dataByte[1] = (byte) 0x00;//0x11;
	    dataByte[2] = (byte) 0xff;
	    dataByte[3] = atr.getTB3();
	    dataByte[4] = (byte) 0x00;
	    dataByte[5] = atr.getIFSC();
	    dataByte[6] = (byte) 0x00;//atr.getTC2();//(byte)0xff;

	    return (Device_to_RDR_IccSetParameter + ByteOperations.IntToHEXString(7, 6, true) + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    protocol.getValue()    + " 00 00 " + ByteOperations.byteArrayToHexString(dataByte) );
	    		// Protocoll id 			length				slot				serquence		  t=1 		RFU 		data
	}
	
	if(protocol == TransportProtocol.T_Equals_0 ){
		
	    byte[] dataByte = new byte[5];
	    
	    dataByte[0] = atr.getTA1();
	    dataByte[1] = (byte) 0x00;//0x11;
	    dataByte[2] = (byte) 0xff;
	    dataByte[3] = atr.getTB3();
	    dataByte[4] = (byte) 0x00;

	    return (Device_to_RDR_IccSetParameter + ByteOperations.IntToHEXString(5, 6, true) + byteToString(slot) + ByteOperations.IntToHEXString(seq, 2, true) +    protocol.getValue()    + " 00 00 " + ByteOperations.byteArrayToHexString(dataByte) );
	    		// Protocoll id 			length				slot				serquence		  t=1 		RFU 		data
	}
	return "";

    }
    
   
    
    
    
    /**
     * Parses the ccid response.
     *
     * @param block the block
     * @return the CCI d_ answer block
     */
    public static CCID_AnswerBlock parseCCIDResponse(byte[] block)
    {
	return new CCID_AnswerBlock(block);
    }
    
    
    

}
