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
package secode3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utilitis.*;

// TODO: Auto-generated Javadoc
/**
 * The Class SecoderProtocoll.
 */
public class SecoderProtocoll {

	/** The Constant R_HEADER_LEN. */
	private static final int R_HEADER_LEN = 14;

	/** The Constant R_HEADER_C1. */
	private static final int R_HEADER_C1 = -63;

	/** The Constant R_TRANSPARRENT_1. */
	private static final int R_TRANSPARRENT_1 = -128;

	/** The Constant R_TRANSPARRENT_2. */
	private static final int R_TRANSPARRENT_2 = -127;

	/** The Constant R_TRANSPARRENT_2. */
	private static final int R_TRANSPARRENT_3 = -126;

	/** The Constant R_ERROR. */
	private static final int R_ERROR = -126;

	/** The Constant R_SW1_C. */
	private static final int R_SW1_C = -112;

	/** The Constant R_SW1_E. */
	private static final int R_SW1_E = -31;

	// / <summary>
	// / disects the dkprotocoll header
	// / </summary>
	// / <param name="header"></param>
	// / <returns></returns>
	/**
	 * Check header.
	 * 
	 * @param header
	 *            the header
	 * @return the int
	 * @throws Exception
	 *             the exception
	 */
	public static int CheckHeader(byte[] header) throws Exception {
		int blocklen = 0;
		int blocksEstimated = 0;

		if (header.length == R_HEADER_LEN) {
			// HHD DK
			if ((header[1] == R_HEADER_C1 && header[0] == 0)
					|| (header[1] == R_TRANSPARRENT_1 && header[0] == 0)
					|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0)
					|| (header[1] == R_TRANSPARRENT_3 && header[0] == 0)) {
				if (!(header[1] == R_TRANSPARRENT_1 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_3 && header[0] == 0)) {
					// Fehlerblock
					if (header[9] == R_ERROR) {
						byte[] array = new byte[] { header[10] };
						String hex = ByteOperations.byteArrayToHexString(array);
						int packet = Integer.parseInt(hex, 16);
						throw new Exception("Packet Missing" + packet);
					}
				}
				if ((header[1] == R_TRANSPARRENT_1 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_3 && header[0] == 0)) {

						return getEstimatedBlockCount(header);
				}

				// SW1SW2 90 00
				if ((header[12] == R_SW1_C || header[12] == R_SW1_E)
						&& header[13] == 0) {
					byte[] a = { header[5], header[4], header[3], header[2] };
					String hexstring = ByteOperations.byteArrayToHexString(a);
					blocklen = Integer.parseInt(hexstring, 16);
					blocksEstimated = (int) (blocklen / 20);
					if (blocksEstimated % 20 != 0) {
						blocksEstimated++;
					}
					// if (header[12] == R_SW1_E)
					// return blocklen - 1;

					return blocksEstimated;
				}
				if (!(header[1] == R_TRANSPARRENT_1 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_3 && header[0] == 0)) {
					// ! SW1SW
					if (header[12] != R_SW1_C && header[12] != R_SW1_E) {
						String hexstring = ByteOperations
								.byteArrayToHexString(header);
						throw new Exception(
								"Card Error"
										+ hexstring.substring(
												hexstring.length() - 4, 4));
					}
				}
			}
		}
		return 0;
	}

	// / <summary>
	// / hanles the input blocks
	// / </summary>
	// / <param name="values"></param>
	// / <returns></returns>
	/**
	 * Handle input.
	 * 
	 * @param values
	 *            the values
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String HandleInput(List<byte[]> values) throws Exception {

		if (values.size() > 0) {
			int blocksEstimated = 0;
			byte[] header = values.get(0);

			// Header
			if (header.length == R_HEADER_LEN) {

				if ((header[1] == R_HEADER_C1 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_1 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0)
						|| (header[1] == R_TRANSPARRENT_3 && header[0] == 0)) {

					if ((header[1] == R_TRANSPARRENT_1 && header[0] == 0)
							|| (header[1] == R_TRANSPARRENT_2 && header[0] == 0 || (header[1] == R_TRANSPARRENT_3 && header[0] == 0))) {

						blocksEstimated = getEstimatedBlockCount(header);
						return ExtractMessageFrom(values, blocksEstimated, true);
					}

					// Fehlerblock
					if (header[10] == R_ERROR) {
						byte[] array = new byte[] { header[11] };
						String hex = ByteOperations.byteArrayToHexString(array);
						int packet = Integer.parseInt(hex, 16);
						throw new Exception("Packet Missing" + packet);
					}
					// SW1SW2 90 00
					if ((header[12] == R_SW1_C || header[12] == R_SW1_E)
							&& header[13] == 0) {
						blocksEstimated = getEstimatedBlockCount(header);
						return ExtractMessageFrom(values, blocksEstimated,
								false);
					}

					// ! SW1SW
					if (header[12] != R_SW1_C && header[12] != R_SW1_E
							&& header[1] != R_HEADER_C1) {
						String hexstring = ByteOperations
								.byteArrayToHexString(header);
						throw new Exception(
								"Card Error"
										+ hexstring.substring(
												hexstring.length() - 4, 4));
					}
				}
			}
		}
		return null;
	}

	// / <summary>
	// / extracts the message block from the protocoll block
	// / </summary>
	// / <param name="values"></param>
	// / <param name="blocksEstimated"></param>
	// / <returns></returns>
	/**
	 * Extract message from.
	 * 
	 * @param values
	 *            the values
	 * @param blocksEstimated
	 *            the blocks estimated
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	private static String ExtractMessageFrom(List<byte[]> values,
			int blocksEstimated, boolean transparrent) throws Exception {

		int blockcounter = 1;

		StringBuilder response = new StringBuilder();
		if (!transparrent) {
			values.remove(0);
		} else {
			blocksEstimated++;
		}

		for (byte[] r : values) {
			if (r != null) {
				blockcounter++;
				response.append(ByteOperations
						.byteArrayToHexString(ByteOperations.getBytesWithRange(
								1, r.length - 1, r)));
			}
		}

		if (blockcounter - 1 != blocksEstimated) {
			throw new Exception("no vallid sequence, maybe blocks are missing");
		}

		return response.toString();
	}

	private static int getEstimatedBlockCount(byte[] header) {
		int blocklen = 0;
		int blocksEstimated = 0;

		byte[] a = { header[5], header[4], header[3], header[2] };
		String hexstring = ByteOperations.byteArrayToHexString(a);
		blocklen = Integer.parseInt(hexstring, 16);
		
		if(blocklen == 0)
			return 0;
		
		if(blocklen < 20)
			return 1;
		
		blocksEstimated = (int) ((blocklen / 20) + 0.5d) ;
		if ((blocksEstimated + 1) % 19 == 0 && blocksEstimated != 0) {
				blocksEstimated++;
		}
		blocksEstimated += (int) ((blocksEstimated / 20) + 0.5d);
	    blocksEstimated++;
		
		return blocksEstimated ;
	}

	// / <summary>
	// / builds the blocks wich will be send
	// / </summary>
	// / <param name="apdu"></param>
	// / <param name="cmdSequenz"></param>
	// / <returns></returns>
	/**
	 * Builds the send blocks.
	 * 
	 * @param apdu
	 *            the apdu
	 * @param cmdSequenz
	 *            the cmd sequenz
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public static List<byte[]> BuildSendBlocks(String apdu, int cmdSequenz)
			throws Exception {
		if (cmdSequenz < 1) {
			throw new Exception("the sequenz has to be larger than 0");
		}

		if (apdu != null) {
			List<byte[]> blocks = new ArrayList<byte[]>();
			apdu = ByteOperations.removeSpacesFromString(apdu);
			blocks.add(GenerateHeaderBlock(apdu.length() / 2, cmdSequenz));
			blocks.addAll(CommandToSendableChunks(apdu, false));
			return blocks;
		}

		throw new Exception("null is not a vallid command");
	}

	/**
	 * Builds the trans parent send blocks.
	 * 
	 * @param apdu
	 *            the apdu
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public static List<byte[]> BuildTransParentSendBlocks(String apdu)
			throws Exception {
		if (apdu != null) {
			List<byte[]> blocks = new ArrayList<byte[]>();
			apdu = ByteOperations.removeSpacesFromString(apdu);
			blocks.add(ByteOperations.hexStringToByteArray("00"
					+ apdu.substring(0, 20)));
			if (apdu.length() > 20) {
				apdu = apdu.substring(20, apdu.length());
				blocks.addAll(CommandToSendableChunks(apdu, false));
			}
			return blocks;
		}

		/*
		 * if (apdu != null) { List<byte[]> blocks = new ArrayList<byte[]>();
		 * apdu = ByteOperations.removeSpacesFromString(apdu);
		 * blocks.addAll(CommandToSendableChunks(apdu, true)); return blocks; }
		 */

		throw new Exception("null is not a vallid command");
	}

	// / <summary>
	// / build the blocks
	// / </summary>
	// / <param name="command"></param>
	// / <returns></returns>
	/**
	 * Command to sendable chunks.
	 * 
	 * @param command
	 *            the command
	 * @param transparrent
	 *            the transparrent
	 * @return the list
	 */
	private static List<byte[]> CommandToSendableChunks(String command,
			boolean transparrent) {
		// splitting the message into chunks to send to the reader via dk
		// protocol

		List<byte[]> result = new ArrayList<byte[]>();
		byte[] apdu = ByteOperations.hexStringToByteArray(command);

		int start = 0;
		int blocknummer = 1;

		if (transparrent)
			blocknummer = 0;

		while (start < apdu.length) {
			int end = Math.min(apdu.length, start + 19);
			result.add(buildBlock(Arrays.copyOfRange(apdu, start, end),
					blocknummer));
			blocknummer++;
			start += 19;
		}

		return result;
	}

	// / <summary>
	// / add blocknumbers
	// / </summary>
	// / <param name="data"></param>
	// / <param name="blockNumber"></param>
	// / <returns></returns>
	/**
	 * Builds the block.
	 * 
	 * @param bs
	 *            the bs
	 * @param blockNumber
	 *            the block number
	 * @return the byte[]
	 */
	private static byte[] buildBlock(byte[] bs, int blockNumber) {
		// Build one chunk for sending
		byte[] block = new byte[bs.length + 1];

		if (blockNumber != 0)
			block[0] = (byte) blockNumber;

		for (int i = 1; i < block.length; i++) {
			block[i] = bs[i - 1];
		}
		return block;

	}

	// / <summary>
	// / bild the header block
	// / </summary>
	// / <param name="lenght"></param>
	// / <param name="cmdSequenz"></param>
	// / <returns></returns>
	/**
	 * Generate header block.
	 * 
	 * @param lenght
	 *            the lenght
	 * @param cmdSequenz
	 *            the cmd sequenz
	 * @return the byte[]
	 */
	private static byte[] GenerateHeaderBlock(int lenght, int cmdSequenz) {

		// Building the Header for the HHD protocol
		byte[] header = new byte[11];
		header[0] = 0x00;
		header[1] = 0x01;
		header[2] = (byte) (lenght & 0xFF);
		header[3] = (byte) ((lenght >> 8) & 0xFF);
		header[4] = (byte) ((lenght >> 16) & 0xFF);
		header[5] = (byte) ((lenght >> 24) & 0xFF);
		header[6] = 0x00;
		header[7] = (byte) cmdSequenz;
		header[8] = 0x10;
		header[9] = 0x00;
		header[10] = 0x00;
		cmdSequenz++;
		return header;

	}

}
