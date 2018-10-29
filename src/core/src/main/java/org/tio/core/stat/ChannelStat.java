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

	/**
	 * The number of bytes processed by this connection
	 */
	Private  AtomicLong  handledBytes  =  new  AtomicLong ();

	/**
	 * The number of packets processed by this connection
	 */
	Private  AtomicLong  handledPackets  =  new  AtomicLong ();

	/**
	 * The number of bytes that have been received by this connection
	 */
	Private  AtomicLong  receivedBytes  =  new  AtomicLong ();
	
	/**
	 * How many times TCP packets have been received by this connection
	 */
	Private  AtomicLong  receivedTcps  =  new  AtomicLong ();

	/**
	 * The number of packets received by this connection
	 */
	Private  AtomicLong  receivedPackets  =  new  AtomicLong ();
	
	/**
	 * Average number of bytes received per TCP, this can be used to monitor slow attacks, configure PacketsPerTcpReceive to locate slow attacks
	 */
	Public  double  getBytesPerTcpReceive ()  {
		If  ( receivedTcps . get ()  ==  0 )  {
			Return  0 ;
		}
		Double  ret  =  ( double ) receivedBytes . get ()  /  ( double ) receivedTcps . get ();
		Return  ret ;
	}
	
	/**
	 * Average number of service packets received per TCP, this can be used to monitor slow attacks. The smaller the value, the more suspected the attack is.
	 */
	Public  double  getPacketsPerTcpReceive ()  {
		If  ( receivedTcps . get ()  ==  0 )  {
			Return  0 ;
		}
		Double  ret  =  ( double ) receivedPackets . get ()  /  ( double ) receivedTcps . get ();
		Return  ret ;
	}

	/**
	 * @return the decodeFailCount
	 */
	Public  int  getDecodeFailCount ()  {
		Return  decodeFailCount ;
	}

	/**
	 * @return the countHandledByte
	 */
	Public  AtomicLong  getHandledBytes ()  {
		Return  handledBytes ;
	}

	/**
	 * @return the countHandledPacket
	 */
	Public  AtomicLong  getHandledPackets ()  {
		Return  handledPackets ;
	}

	/**
	 * @return the timeLatestReceivedMsg
	 */
	Public  long  getLatestTimeOfReceivedPacket ()  {
		Return  latestTimeOfReceivedPacket ;
	}

	/**
	 * @return the timeLatestSentMsg
	 */
	Public  long  getLatestTimeOfSentPacket ()  {
		Return  latestTimeOfSentPacket ;
	}

	/**
	 * @return the countReceivedByte
	 */
	Public  AtomicLong  getReceivedBytes ()  {
		Return  receivedBytes ;
	}

	/**
	 * @return the countReceivedPacket
	 */
	Public  AtomicLong  getReceivedPackets ()  {
		Return  receivedPackets ;
	}

	/**
	 * @return the countSentByte
	 */
	Public  AtomicLong  getSentBytes ()  {
		Return  sentBytes ;
	}

	/**
	 * @return the countSentPacket
	 */
	Public  AtomicLong  getSentPackets ()  {
		Return  sentPackets ;
	}

	/**
	 * @return the timeClosed
	 */
	Public  long  getTimeClosed ()  {
		Return  timeClosed ;
	}

	/**
	 * @return the timeCreated
	 */
	Public  long  getTimeCreated ()  {
		Return  timeCreated ;
