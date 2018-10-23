package org.tio.core;

/**
 *  Message processing mode
 * @author tanyaowu
 *
 */
public enum PacketHandlerMode {
	/**
	 * Processing messages and decoding in the same thread
	 */
	SINGLE_THREAD(1),

	/**
