package org.tio.core.intf;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author tanyaowu
 *  
 */
public class PacketWithMeta {
	private Packet packet = null;
	private Boolean isSentSuccess = null;
	private CountDownLatch countDownLatch = null;

	/**
	 *
	 * @param packet
	 * @param countDownLatch
	 * @author tanyaowu
	 */
