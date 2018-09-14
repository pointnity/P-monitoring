package org.tio.flash.policy.server;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.exception.LengthOverflowException;
import org.tio.core.intf.Packet;
import org.tio.core.utils.ByteBufferUtils;
import org.tio.server.intf.ServerAioHandler;

/**
 * 
 * @author tanyaowu 
 * 
 */
public class FlashPolicyServerAioHandler implements ServerAioHandler {
	private static Logger log = LoggerFactory.getLogger(FlashPolicyServerAioHandler.class);

	/**
	 * Processing messages
	 */
	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		Aio.send(channelContext, FlashPolicyPacket.RESPONSE);
	}

	public static final String REQUEST_STR = "<policy-file-request/>";
