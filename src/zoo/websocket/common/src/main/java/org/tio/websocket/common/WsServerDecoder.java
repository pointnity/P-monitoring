package org.tio.websocket.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.utils.ByteBufferUtils;

/**
*
 * @author tanyaowu
 * 
 */
public class WsServerDecoder {
	public static enum Step {
		header, remain_header, data,
	}

	private static Logger log = LoggerFactory.getLogger(WsServerDecoder.class);

	public static WsRequest decode(ByteBuffer buf, ChannelContext channelContext) throws AioDecodeException {
