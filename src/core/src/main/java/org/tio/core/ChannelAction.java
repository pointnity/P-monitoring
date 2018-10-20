package org.tio.core;

/**
 * client action
 * @author tanyaowu
 *
 */
public enum ChannelAction {
	/**
	 *
	 */
	CONNECT(1),

	/**
	 *
	 */
	RECEIVED(2),
	/**
	 *
	 */
	BEFORE_SEND(3),

	/**
	 *
	 */
	AFTER_SEND(4),

	/**
	 *
	 */
	UNCONNECT(5),

	/**
	 * reconnects
	 */
	RE_CONNECT(6),

	/**
	 *
	 */
	BEFORE_HANDLER(7),
	/**
	 *
	 */
	AFTER_HANDLER(8),

	/**
	 *
	 */
	RECEIVED_BUF(9);

	public static ChannelAction forNumber(int value) {
		switch (value) {
		case 1:
