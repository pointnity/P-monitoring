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
		WsSessionContext imSessionContext = (WsSessionContext) channelContext.getAttribute();
		List<byte[]> lastParts = imSessionContext.getLastParts();

		//First phase analysis
		int initPosition = buf.position();
		int readableLength = buf.limit() - initPosition;

		int headLength = WsPacket.MINIMUM_HEADER_LENGTH;

		if (readableLength < headLength) {
			return null;
		}

		byte first = buf.get();
		//		int b = first & 0xFF; //Convert to 32-bit
		boolean fin = (first & 0x80) > 0; //Get 8th place 10000000>0
		@SuppressWarnings("unused")
		int rsv = (first & 0x70) >>> 4;//Get5、6、7 为01110000 Then move right four digits to00000111
		byte opCodeByte = (byte) (first & 0x0F);//The latter four digits areopCode 00001111
		Opcode opcode = Opcode.valueOf(opCodeByte);
		if (opcode == Opcode.CLOSE) {
			//			Aio.remove(channelContext, "Roger that opcode:" + opcode);
			//			return null;
		}
