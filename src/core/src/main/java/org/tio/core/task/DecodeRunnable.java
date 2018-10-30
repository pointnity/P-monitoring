package org.tio.core.task;

import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelAction;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.PacketHandlerMode;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioListener;
import org.tio.core.intf.Packet;
import org.tio.core.stat.ChannelStat;
import org.tio.core.stat.IpStat;
import org.tio.core.utils.ByteBufferUtils;
import org.tio.utils.SystemTimer;

/**
 * Decoding
*
 * @author  
 * 2012-08-09
 *
 */
public class DecodeRunnable implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(DecodeRunnable.class);

	/**
	 *
	 * @param channelContext
	 * @param packet
	 * @param byteCount
	 * @author tanyaowu
	 */
	public static void handler(ChannelContext channelContext, Packet packet, int byteCount) {

		GroupContext groupContext = channelContext.getGroupContext();
		PacketHandlerMode packetHandlerMode = groupContext.getPacketHandlerMode();

		HandlerRunnable handlerRunnable = channelContext.getHandlerRunnable();
		if (packetHandlerMode == PacketHandlerMode.QUEUE) {

			handlerRunnable.addMsg(packet);
			groupContext.getTioExecutor().execute(handlerRunnable);
		} else {
			handlerRunnable.handler(packet);
		}
	}

	private ChannelContext channelContext = null;

	/**
	 * Last decoding the rest of the data
	 */
	private ByteBuffer lastByteBuffer = null;

	/**
	 *New data received
	 */
	private ByteBuffer newByteBuffer = null;

	/**
	 *
	 */
	public DecodeRunnable(ChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	/**
	 * Empty the processed queue messages
	 */
	public void clearMsgQueue() {
		lastByteBuffer = null;
		newByteBuffer = null;
	}

	/**
	 * @see java.lang.Runnable#run()
	 *
	 * @author tanyaowu
	 *  
	 *
