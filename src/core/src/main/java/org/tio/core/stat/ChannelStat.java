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
