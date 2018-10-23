package org.tio.core;

/**
 *Message sending mode
 * @author tanyaowu
 *
 */
public enum PacketSendMode {
	/**
	 *Throw the packet in a queue and let the thread pool send
	 */
	QUEUE(1),
	/**
	 * A single message blocked send, the direct send succeeds, only then retrace
	 */
	SINGLE_BLOCK(2),
