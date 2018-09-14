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

	/**
	 * <policy-file-request/>
	 * @param buffer
	 * @param channelContext
	 * @return
	 * @throws AioDecodeException
	 * @author tanyaowu
	 */
	@Override
	public FlashPolicyPacket decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
		int readableLength = buffer.limit() - buffer.position();
		//The received data group is not a business package, then NULL is returned to tell the framework that the data is insufficient
		if (readableLength < FlashPolicyPacket.MIN_LENGHT) {
			return null;
		}

		String line = null;

		try {
			line = ByteBufferUtils.readLine(buffer, Const.CHARSET, '\0', FlashPolicyPacket.MAX_LING_LENGHT);
		} catch (LengthOverflowException e) {
			throw new AioDecodeException(e);
		}

		if (line == null) {
			return null;
		} else {
			log.info("Receive Message:{}", line);
			if (REQUEST_STR.equalsIgnoreCase(line)) {
				return FlashPolicyPacket.REQUEST;
			} else {
				throw new AioDecodeException("");
			}
		}
	}

	public static byte[] RESPONSE_BYTES;

	static {
		RESPONSE_BYTES = ("<cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>\0").getBytes();
	}

	/**
	 * 
	 * @param packet
	 * @param groupContext
	 * @param channelContext
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
		ByteBuffer ret = ByteBuffer.wrap(RESPONSE_BYTES);
		//		ret.position(ret.limit());
		return ret;
	}
