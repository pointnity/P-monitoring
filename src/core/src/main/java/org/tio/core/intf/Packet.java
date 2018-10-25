package org.tio.core.intf;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
*
* @author tanyaowu
* 
*/
public class Packet implements java.io.Serializable {

	private static final long serialVersionUID = 5275372187150637318L;

	private static final AtomicLong ID_ATOMICLONG = new AtomicLong();

	private Long id = ID_ATOMICLONG.incrementAndGet();

	private int byteCount = 0;

	private Long respId = null;

	private PacketListener packetListener;

	private boolean isBlockSend = false;
	
	/**
	 * Whether the message was another machine was turned over by topic, and if it is, do not go back to the loop again.
	 * This attribute is used internally by TIO, business layer users are requested to use
	 */
	private boolean isFromCluster = false;

	/**
	 * Synchronous serial number required when sending synchronously
