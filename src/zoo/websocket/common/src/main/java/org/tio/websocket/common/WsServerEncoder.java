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
