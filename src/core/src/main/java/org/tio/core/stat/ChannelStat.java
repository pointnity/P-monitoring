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
	 * Last time the business Message pack was sent: send bytes even if
	 */
	private long latestTimeOfSentByte = SystemTimer.currentTimeMillis();

	/**
	 * Channelcontext the time the object was created
	 */
	private long timeCreated = SystemTimer.currentTimeMillis();

	/**
	 * The time when the first connection was successful
	 */
	Private  Long  timeFirstConnected  =  null ;

	/**
	 * When the connection is closed
	 */
	Private  long  timeClosed  =  SystemTimer . currentTimeMillis ();

	/**
	 * Enter re-queue time
	 */
	Private  long  timeInReconnQueue  =  SystemTimer . currentTimeMillis ();

	/**
	 * The number of bytes sent by this connection
	 */
	Private  AtomicLong  sentBytes  =  new  AtomicLong ();

	/**
	 * The number of packets sent by this connection
	 */
	Private  AtomicLong  sentPackets  =  new  AtomicLong ();
