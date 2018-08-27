package org.tio.websocket.common;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.utils.ByteBufferUtils;

/**
 * 
 * generallycloud.nio.codec.http11.WebSocketProtocolEncoder
 * @author tanyaowu
 *
 */
public class WsServerEncoder {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(WsServerEncoder.class);

	public static final int MAX_HEADER_LENGTH = 20480;

	private static void checkLength(byte[] bytes, int length, int offset) {
		if (bytes == null) {
			throw new IllegalArgumentException("null");
		}

		if (offset < 0) {
			throw new IllegalArgumentException("invalidate offset " + offset);
		}

		if (bytes.length - offset < length) {
			throw new IllegalArgumentException("invalidate length " + bytes.length);
		}
	}

	public static ByteBuffer encode(WsResponse wsResponse, GroupContext groupContext, ChannelContext channelContext) {
		byte[] imBody = wsResponse.getBody();//is the body of WS, excluding the head of WS
		int wsBodyLength = 0;

		if (imBody != null) {
			wsBodyLength += imBody.length;
		}
