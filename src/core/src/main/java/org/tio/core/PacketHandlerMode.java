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
	 * Throw the packet in a queue and let the thread pool handle it.
	 */
	QUEUE(2);
