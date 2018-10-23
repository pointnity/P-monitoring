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

	/**
	 *A group of messages blocked send, sent directly to the successful, only to retrace
	 */
	GROUP_BLOCK(3);

	public static PacketSendMode forNumber(int value) {
		switch (value) {
		case 1:
			return QUEUE;
		case 2:
			return SINGLE_BLOCK;
		case 3:
			return GROUP_BLOCK;
		default:
			return null;
		}
	}

	private final int value;

	private PacketSendMode(int value) {
		this.value = value;
	}
