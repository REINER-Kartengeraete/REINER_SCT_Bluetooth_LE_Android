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

import java.util.LinkedList;
import java.util.List;

import utilitis.ByteOperations;

// TODO: Auto-generated Javadoc
/**
 * The Class TLV.
 */
public class TLV {
    
    /**
     * TLVException
     * 
     * called in case of an error in the tlv block.
     *
     * @author Admin
     */
    public class TLVException extends Exception {
	
	/**
	 * Instantiates a new TLV exception.
	 *
	 * @param message the message
	 */
	public TLVException(String  message)
	{
	    super(message);
	}
	
	/**
	 * Instantiates a new TLV exception.
	 */
	public TLVException()
	{
	    super();
	}
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2427261641980591073L;
    }

    /** The Constant TAG_TOPLEVEL. */
    private static final int TAG_TOPLEVEL = 0xFFFF;
    
    /** The m value. */
    private byte[] mValue;
    
    /** The m index. */
    private int mIndex;
    
    /** The m length. */
    private int mLength;
    
    /** The m tag. */
    private int mTag;
    
    /** The m children. */
    private List<TLV> mChildren;

    /**
     * Constructor TLV.
     *
     * @param value the value
     * @throws TLVException the TLV exception
     */
    public TLV(byte[] value) throws TLVException {
	this(value, 0, value.length, TAG_TOPLEVEL);
	String test  = ByteOperations.byteArrayToHexString(value);
	System.out.print(test);
    }
    
    /**
     * Constructor TLV.
     *
     * @param value the value
     * @param index the index
     * @param length the length
     * @param tag the tag
     * @throws TLVException the TLV exception
     */
    private TLV(byte[] value, int index, int length, int tag)
	    throws TLVException {
	if (value == null)
	    throw new IllegalArgumentException("value must not be null");
	mValue = value;
	mIndex = index;
	mLength = length;
	mTag = tag;
	mChildren = new LinkedList<TLV>();
	if (isConstructed()) {
	    parse();
	}
    }
    
    /**
     * gets the tag.
     *
     * @return the tag
     */
    public int getTag() {
	return mTag;
    }

    /**
     * gets the value.
     *
     * @return the value
     */
    public byte[] getValue() {
	byte[] newArray = new byte[mLength];
	System.arraycopy(mValue, mIndex, newArray, 0, mLength);
	return newArray;
    }

    /**
     * gets all children.
     *
     * @return the children
     */
    public List<TLV> getChildren() {
	return mChildren;
    }

/**
 * if is constructed.
 *
 * @return true, if is constructed
 */
    public boolean isConstructed() {
	final int CONSTRUCTED_BIT = 0x20;
	return (getFirstTagByte(mTag) & CONSTRUCTED_BIT) != 0;
    }

/**
 * parses the tlv structure.
 *
 * @throws TLVException the TLV exception
 */
    private void parse() throws TLVException {
	int index = mIndex;
	int endIndex = mIndex + mLength;
	while (index < endIndex) {
	    int tag = getNext(index++);
	    if (tag == 0x00 || tag == 0xFF)
		continue;
	    if (tagHasMultipleBytes(tag)) {
		tag <<= 8;
		tag |= getNext(index++);
		if (tagHasAnotherByte(tag)) {
		    tag <<= 8;
		    tag |= getNext(index++);
		}
		if (tagHasAnotherByte(tag))
		    throw new TLVException();
	    }
	    int length = getNext(index++);
	    if (length >= 0x80) {
		int numLengthBytes = (length & 0x7F);

		if (numLengthBytes > 3)
		    throw new TLVException();
		length = 0;
		for (int i = 0; i < numLengthBytes; i++) {
		    length <<= 8;
		    length |= getNext(index++);
		}
	    }
	    TLV tlv = new TLV(mValue, index, length, tag);
	    mChildren.add(tlv);
	    index += tlv.getLength();
	}
    }

    /**
     * gets the length.
     *
     * @return the length
     */
    private int getLength() {
	return mLength;
    }

/**
 * gets the next child.
 *
 * @param index the index
 * @return the next
 * @throws TLVException the TLV exception
 */
    private int getNext(int index) throws TLVException {
	if (index < mIndex || (index >= (mIndex + mLength )&& (mLength != 1)))
	    throw new TLVException();
	return (mValue[index] & 0xFF);
    }
    
    /**
     * gets the first tag.
     *
     * @param tag the tag
     * @return the first tag byte
     */
    private static int getFirstTagByte(int tag) {
	while (tag > 0xFF)
	    tag >>= 8;
	return tag;
    }

/**
 * check if has multiple bytes.
 *
 * @param tag the tag
 * @return true, if successful
 */
    private static boolean tagHasMultipleBytes(int tag) {
	final int MULTIBYTE_TAG_MASK = 0x1F;
	return (tag & MULTIBYTE_TAG_MASK) == MULTIBYTE_TAG_MASK;
    }

/**
 * check if tag has an other byte.
 *
 * @param tag the tag
 * @return true, if successful
 */
    private static boolean tagHasAnotherByte(int tag) {
	final int NEXT_BYTE = 0x80;
	return (tag & NEXT_BYTE) != 0;
    }
}
