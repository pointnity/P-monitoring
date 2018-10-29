package org.tio.core.stat;

import java.util.concurrent.atomic.AtomicLong;

import org.tio.utils.SystemTimer;

/**
 * @author tanyaowu
 *  
 */
public class ChannelStat implements java.io.Serializable {
	private static final long serialVersionUID = -6942731710053482089L;

	/**
	 * Number of times this decoding failed
	 */
	private int decodeFailCount = 0;

	/**
	 * Last time a business message packet was received (a complete business message package, part of the message is not counted)
	 */
	private long latestTimeOfReceivedPacket = SystemTimer.currentTimeMillis();

	/**
	 * Last time the business Message pack was sent (a complete business Message pack, part of the message is not counted)
	 */
	private long latestTimeOfSentPacket = SystemTimer.currentTimeMillis();
	
	/**
	 * Last time a business message packet was received: Bytes received even though
	 */
	private long latestTimeOfReceivedByte = SystemTimer.currentTimeMillis();

	/**
