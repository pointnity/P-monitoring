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
	public PacketWithMeta(Packet packet, CountDownLatch countDownLatch) {
		super();
		this.packet = packet;
		this.countDownLatch = countDownLatch;
		if (countDownLatch != null) {
			this.packet.setBlockSend(true);
		}
	}

	/**
	 * @return the countDownLatch
	 */
	public java.util.concurrent.CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	/**
	 * @return the isSentSuccess
	 */
	public Boolean getIsSentSuccess() {
		return isSentSuccess;
	}

	/**
	 * @return the packet
	 */
	public Packet getPacket() {
		return packet;
	}

	/**
