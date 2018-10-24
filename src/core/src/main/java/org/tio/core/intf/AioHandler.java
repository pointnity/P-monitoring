package org.tio.core.intf;

import java.nio.ByteBuffer;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;

/**
 * 
 * @author tanyaowu 
 *  
 */
public interface AioHandler {

	/**
	 * Packet objects that are decoded into business according to Bytebuffer.
	 * If the received data is incomplete, causing the decoding to fail, return NULL, and the framework layer will automatically renew the previous received data when the next message comes
	 * @param buffer
	 * @param channelContext
	 * @return
	 * @throws AioDecodeException
	 * @author: tanyaowu
	 */
	Packet decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException;

	/**
	 * Coding
	 * @param packet
	 * @param groupContext
	 * @param channelContext
	 * @return
	 * @author: tanyaowu
	 */
	ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext);

	/**
	 * Processing message Packets
	 * @param packet
	 * @param channelContext
	 * @throws Exception
	 * @author: tanyaowu
	 */
	void handler(Packet packet, ChannelContext channelContext) throws Exception;

}
