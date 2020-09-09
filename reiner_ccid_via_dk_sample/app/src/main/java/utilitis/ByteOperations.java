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
package utilitis;

import java.nio.ByteBuffer;

// TODO: Auto-generated Javadoc
/**
 * The Class ByteOperations.
 */
public class ByteOperations {

	// / <summary>
	// / converts a hexString like "01 02 03 04 " into a byte array {0x01, 0x02,
	// 0x03, 0x04}
	// / </summary>
	// / <param name="hex"></param>
	// / <returns></returns>
	/**
	 * Hex string to byte array.
	 * 
	 * @param hexString
	 *            the hex string
	 * @return the byte[]
	 */
	public static byte[] hexStringToByteArray(String hexString) {

		hexString = hexString.replace("null", "");
		hexString = hexString.replace(" ", "");
		int len = hexString.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character
					.digit(hexString.charAt(i + 1), 16));
		}
		return data;

	}

	// / <summary>
	// / turns a char into its hex value, like 'C' or 'c' into 0x0c
	// / </summary>
	// / <param name="hex"></param>
	// / <returns></returns>
	/**
	 * Gets the hex val.
	 * 
	 * @param hex
	 *            the hex
	 * @return the hex val
	 */
	public static int getHexVal(char hex) {
		int val = (int) hex;
		return val - (val < 58 ? 48 : (val < 97 ? 55 : 87));
	}

	// / <summary>
	// / turns an integer into a string and add's 0 in front
	// / </summary>
	// / <param name="value"></param>
	// / <param name="padding"></param>
	// / <returns></returns>
	/**
	 * Int to hex string.
	 * 
	 * @param value
	 *            the value
	 * @param padding
	 *            the padding
	 * @return the string
	 */
	public static String IntToHEXString(int value, int padding,
			boolean littleEndian) {

		if (padding <= 2)
			littleEndian = false;

		String ret = null;

		if (littleEndian) {
			byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
			ret = byteArrayToHexString(reverseByteArray(bytes));

		} else {
			ret = Integer.toHexString(value);
		}

		while (ret.length() < padding) {
			if (littleEndian) {
				ret = ret + "0";
			} else {
				ret = "0" + ret;
			}
		}

		return ret;
	}

	public static byte[] reverseByteArray(byte[] array) {
		for (int start = 0, end = array.length - 1; start <= end; start++, end--) {
			byte aux = array[start];
			array[start] = array[end];
			array[end] = aux;
		}
		return array;
	}

	// / <summary>
	// / turns a byte[] like {0x01, 0x02, 0x03, 0x04} into a string like
	// "01 02 03 04 "
	// / </summary>
	// / <param name="a"></param>
	// / <returns></returns>
	/**
	 * Byte array to hex string.
	 * 
	 * @param a
	 *            the a
	 * @return the string
	 */
	public static String byteArrayToHexString(byte[] a) {
		StringBuilder sb = new StringBuilder();
		int x = 0;

		for (byte b : a) {

			if (x % 4 == 0 && x != 0) {
				sb.append(" ");
			}
			sb.append(String.format("%02x", b & 0xff));
			x++;
		}

		return sb.toString();
	}

	// / <summary>
	// / returns a koppy of a byte[] from the start index with a length
	// / </summary>
	// / <param name="start"></param>
	// / <param name="lenght"></param>
	// / <param name="array"></param>
	// / <returns></returns>
	/**
	 * Gets the bytes with range.
	 * 
	 * @param start
	 *            the start
	 * @param lenght
	 *            the lenght
	 * @param array
	 *            the array
	 * @return the bytes with range
	 */
	public static byte[] getBytesWithRange(int start, int lenght, byte[] array) {

		byte[] returnVal = new byte[lenght];
		if (array.length >= lenght - start) {
			System.arraycopy(array, start, returnVal, 0, lenght);
		} else {
			return null;
		}
		return returnVal;
	}

	// / <summary>
	// / combines bytearrays
	// / </summary>
	// / <param name="start"></param>
	// / <param name="toAppend"></param>
	// / <returns></returns>
	/**
	 * Append byte array.
	 * 
	 * @param start
	 *            the start
	 * @param toAppend
	 *            the to append
	 * @return the byte[]
	 */
	public static byte[] appendByteArray(byte[] start, byte[] toAppend) {
		int glen = start.length + toAppend.length;
		byte[] rValue = new byte[glen];
		int counter = 0;

		for (byte b : start) {
			rValue[counter] = b;
			counter++;
		}
		for (byte b : toAppend) {
			rValue[counter] = b;
			counter++;
		}

		return rValue;
	}

	/**
	 * Bytes to int.
	 * 
	 * @param bytes
	 *            the bytes
	 * @return the int
	 */
	public static int bytesToInt(byte[] bytes) {

		boolean isnull = true;
		for (byte b : bytes) {
			if (b != 0x00) {
				isnull = false;
				break;
			}
		}
		if (isnull) {
			return 0;
		}

		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
		}
		return result;
	}

	/**
	 * Gets the bits from byte.
	 * 
	 * @param info
	 *            the info
	 * @return the bits from byte
	 */
	public static boolean[] getBitsFromByte(byte info) {
		boolean[] bits = new boolean[8];
		for (int i = 0; i < 8; i++) {
			if ((info & (1 << (7 - (i % 8)))) > 0)
				bits[i] = true;
		}
		return bits;
	}

	/**
	 * Removes the spaces from string.
	 * 
	 * @param start
	 *            the start
	 * @return the string
	 */
	public static String removeSpacesFromString(String start) {
		String returnString = start.replace(" ", "").replace("null", "");
		return returnString;
	}

	/**
	 * Byte array to int.
	 * 
	 * @param b
	 *            the b
	 * @return the int
	 */
	public static int byteArrayToInt(byte[] b) {
		String hexstring = ByteOperations.byteArrayToHexString(b);
		return Integer.parseInt(hexstring, 16);
	}

	/**
	 * Int to byte array.
	 * 
	 * @param a
	 *            the a
	 * @return the byte[]
	 */
	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF),
				(byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

}
