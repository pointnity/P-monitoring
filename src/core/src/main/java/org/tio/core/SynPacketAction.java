package org.tio.core;

/**
 * Synchronize message Action
 * @author tanyaowu
 *
 */
public enum SynPacketAction {
	/**
	 *
	 */
	BEFORE_WAIT(1),

	/**
	 *
	 */
	AFTER__WAIT(2),

	/**
	 *
	 */
	BEFORE_DOWN(3);

	public static SynPacketAction forNumber(int value) {
		switch (value) {
