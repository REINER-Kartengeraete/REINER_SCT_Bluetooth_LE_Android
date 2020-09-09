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
import android.util.Log;


// TODO: Auto-generated Javadoc
/**
 * The Class HHDGenerator.
 */
public class HHDGenerator {

	/**
	 * Gets the HHD command.
	 *
	 * @param start_code the start_code
	 * @param bde1 the bde1
	 * @param bde2 the bde2
	 * @param bde3 the bde3
	 * @param control the control
	 * @return the HHD command
	 */
	public static String getHHDCommand (String start_code, String bde1, String bde2, String bde3, String control) {

	  if (control == null || control.equals("")) {
	    control = "01"; // ASSUMING GERMAN CUNTRY CODE

	  }

	  int bde1_length = 12;
	  int bde2_length = 12;
	  int bde3_length = 12;
	  if (bde1.length() > 12) {
	    bde1_length = 36;
	  } else if (bde2.length() > 12) {
	    bde2_length = 36;
	  } else if (bde3.length() > 12) {
	    bde3_length = 36;
	  }
	  
	  
	  String hdd_start_code = toData(start_code, 12, true);
	  String hdd_bde1 = toData(bde1, bde1_length,false);
	  String hdd_bde2 = toData(bde2, bde2_length,false);
	  String hdd_bde3 = toData(bde3, bde3_length,false);
	

	  // strip last components if empty
	  if (bde3.equals("") || bde3_length == 0  ) {
	    hdd_bde3 = "";
	  }
	  if (bde2.equals("") || bde2_length == 0   && bde3.equals("") || bde3_length == 0  ) {
	    hdd_bde2 = "";
	  }
	  if (bde1.equals("") || bde1_length == 0  && bde2.equals("") || bde2_length == 0   && bde3.equals("") ||bde3_length == 0  ) {
	    hdd_bde1 = "";
	  }

	  String str = hdd_start_code +
	               hdd_bde1 + hdd_bde2 + hdd_bde3;
	  String lc = Integer.toHexString(str.length() / 2 + 1 );
	  if(lc.length()==1){
		  lc = "0"+lc;
	  }

	  String luhn_str = hdd_start_code.substring(2);

	  if (hdd_bde1.length() > 2)
	    luhn_str += hdd_bde1.substring(2);

	  if (hdd_bde2.length() > 2)
	    luhn_str += hdd_bde2.substring(2);

	  if (hdd_bde3.length() > 2)
	    luhn_str += hdd_bde3.substring(2);

	  String luhn = comp_luhn(luhn_str);
	  String xor_mask = comp_xor(lc + str);
 	  String returnVal =  lc  + str + luhn + xor_mask;
 	  String lc2 = Integer.toHexString((returnVal.length() / 2)   );
	  if(lc2.length()==1){
		  lc2 = "0"+lc2;
	  }
	  returnVal = "00000000010000" + lc2 + returnVal;
	 
	 
	  Log.d("HHDDuc:", returnVal);
	  //callback.didRecieveHHDDuc(returnVal);
	  return returnVal;
	}

/**
 * Ord.
 *
 * @param str the str
 * @return the int
 */
private static int ord(String str) {
	  return Character.valueOf(str.charAt(0));
	}

/**
 * Bcd ok.
 *
 * @param input the input
 * @return the boolean
 */
private static Boolean bcdOk(String input) {
  for (int i = 0; i < input.length(); i++) {
    if (ord(input.substring(i,i+1)) < 48 || ord(input.substring(i,i+1)) > 57) {
      return false;
    }
  }
  return true;
}

/**
 * To data.
 *
 * @param input the input
 * @param max_length the max_length
 * @param cb the cb
 * @return the string
 */
private static String toData(String input, int max_length, boolean cb) {
 
if (input == null || input.equals(""))
    input = "";
  if (input.length()> max_length) {
    return "";
  }else{
  return bcdOk(input) ? toBcd(input, cb) : toAscii(input, cb);
  }
}

/**
 * Rpad.
 *
 * @param data the data
 * @param c the c
 * @return the string
 */
private static String rpad(String data, char c) {
	
	  if (data.length() % 2 != 0) {
	    data = data + c;
	  }
	  return data;
	}

/**
 * To bcd.
 *
 * @param data the data
 * @param cb the cb
 * @return the string
 */
private static String toBcd(String data, boolean cb) {
  data = rpad(data, 'F');
  return comp_lde(data, false, cb) + comp_cb((boolean)cb) + data;
}

/**
 * To ascii.
 *
 * @param data the data
 * @param cb the cb
 * @return the string
 */
private static String toAscii(String data, boolean cb) {

  return comp_lde(data, true, cb) + comp_cb(cb) + toHex(data);
}

/**
 * To hex.
 *
 * @param input the input
 * @return the string
 */
private static String toHex(String input) {
	  String tmp = "";
	  for (int i = 0; i < input.length(); i++) {
	    if (ord(input.substring(i,i+1)) < 128){
	    	String t = Integer.toHexString(ord(input.substring(i,i+1)));
	    	  if(t.length() == 1){
	    		  t= "0" + t; 
	    	  }
	    	tmp += t;
	    }
	  }
	  return tmp;
	}

/**
 * Comp_cb.
 *
 * @param cb the cb
 * @return the string
 */
private static String comp_cb(boolean cb) {
	if  (cb == true){
		return "01";
	}else{ return "";} 
  
}

/**
 * Comp_lde.
 *
 * @param input the input
 * @param asc the asc
 * @param cb the cb
 * @return the string
 */
private static String comp_lde(String input, boolean asc, boolean cb) {
  int f = 0;
  f += asc ? 64 : 0; // ASC or BCD
  f += cb ? 128 : 0; // ControlByte?
  if (asc)
    f += input.length();
  else
    f += input.length() / 2;
  
  String returnString = Integer.toHexString(f);
  if(returnString.length() == 1){
	  return "0" + returnString; 
  }
  return returnString;
}

/**
 * Comp_luhn.
 *
 * @param str the str
 * @return the string
 */
private static String comp_luhn(String str) {
	if(str.length() != 0)
	{
	 int sum = 0;
		

	  for (int i = 0; i < str.length(); i++) {
	    if (i%2 != 0) {  
	      sum += sumOfDigits(2 * hexdec(str.substring(i, i+1)));
	    } else {        
	      sum += sumOfDigits(hexdec(str.substring(i, i+1)));
	    }
	  }
	  sum = sum % 10;
	  if (sum != 0)
	    sum = 10 - sum;
	  
	  String ret = ""+hexdec("" + sum);
	  return ret;
	
	}else{
		
		return "";
	}
	
	}

/**
 * Comp_xor.
 *
 * @param str the str
 * @return the string
 */
private static String comp_xor(String str) {
	  int tmp = 0;
	  int len = str.length();
	  for (int i = 0; i < len; i++) {
	    tmp ^= hexdec(str.substring(i, i+1));
	  }
	 
	  String ret = Integer.toHexString(tmp);
	  return ret;
	}

/**
 * Sum of digits.
 *
 * @param i the i
 * @return the int
 */
private static int sumOfDigits(int i) {
	  if (i < 10) {
	    return i;
	  }
	  return sumOfDigits((i/10)) + i % 10;
	}

/**
 * Hexdec.
 *
 * @param str the str
 * @return the int
 */
private static int hexdec(String str){
	
		if(str.equals("") || str.equals(".") ||str.equals(",")){
			return 0;
		}
	  return Integer.parseInt(str, 16);
	}
   
}