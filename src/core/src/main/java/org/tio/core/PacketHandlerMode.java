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

	public static PacketHandlerMode forNumber(int value) {
		switch (value) {
		case 1:
			return SINGLE_THREAD;
		case 2:
			return QUEUE;
		default:
			return null;
		}
	}
