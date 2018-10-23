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
