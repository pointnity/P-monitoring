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
		int rsv = (first & 0x70) >>> 4;//Get5、6、7 For 01110000 Then move right four digits to00000111
		byte opCodeByte = (byte) (first & 0x0F);//The latter four digits areopCode 00001111
		Opcode opcode = Opcode.valueOf(opCodeByte);
		if (opcode == Opcode.CLOSE) {
			//			Aio.remove(channelContext, "Roger that opcode:" + opcode);
			//			return null;
		}
		if (!fin) {
			log.error("{} Request for Fin false is not supported temporarily", channelContext);
			Aio.remove(channelContext, "Request for Fin false is not supported temporarily");
			return null;
			//The following code should not be deleted, and if you support fin in the future, you will need
			//			if (lastParts == null) {
			//				lastParts = new ArrayList<>();
			//				imSessionContext.setLastParts(lastParts);
			//			}
		} else {
			imSessionContext.setLastParts(null);
		}

		byte second = buf.get(); //Read backward one byte
		boolean hasMask = (second & 0xFF) >> 7 == 1; //Used to identify whether Payloaddata has been masked.If the data in the 1,masking-key field is a mask key, it is used to decode the payloaddata.The data frames emitted by the client need to be masked, so this bit is 1.

              //Client data must be masked
              //if (!Hasmask) {//9th for mask, must be 1
			//throw new AioDecodeException("websocket client data must be masked");
		} else {
			headLength += 4;
		}
		int payloadLength = second & 0x7F; //After reading the 7-bit Payload legth, if <126 is Payloadlength

		byte[] mask = null;
		if (payloadLength == 126) { //126 reads 2 bytes, and the latter two bytes is payloadlength
			headLength += 2;
			if (readableLength < headLength) {
				return null;
			}
			payloadLength = ByteBufferUtils.readUB2WithBigEdian(buf);
			log.info("{} payloadLengthFlag: 126，payloadLength {}", channelContext, payloadLength);

		} else if (payloadLength == 127) { //127 reads 8 bytes, and the latter 8 bytes is payloadlength
			headLength += 8;
			if (readableLength < headLength) {
				return null;
			}

			payloadLength = (int) buf.getLong();
			log.info("{} payloadLengthFlag: 127，payloadLength {}", channelContext, payloadLength);
		}

		if (payloadLength < 0 || payloadLength > WsPacket.MAX_BODY_LENGTH) {
			throw new AioDecodeException("body length(" + payloadLength + ") is not right");
		}

		if (readableLength < headLength + payloadLength) {
			return null;
		}

		if (hasMask) {
			mask = ByteBufferUtils.readBytes(buf, 4);
		}

		//Second phase analysis
		WsRequest websocketPacket = new WsRequest();
		websocketPacket.setWsEof(fin);
		websocketPacket.setWsHasMask(hasMask);
		websocketPacket.setWsMask(mask);
		websocketPacket.setWsOpcode(opcode);
		websocketPacket.setWsBodyLength(payloadLength);

		if (payloadLength == 0) {
			return websocketPacket;
		}

		byte[] array = ByteBufferUtils.readBytes(buf, payloadLength);
		if (hasMask) {
			for (int i = 0; i < array.length; i++) {
				array[i] = (byte) (array[i] ^ mask[i % 4]);
			}
		}

		if (!fin) {
			//lastParts.add(array);
			log.error("payloadLength {}, lastParts size {}, array length {}", payloadLength, lastParts.size(), array.length);
			return websocketPacket;
		} else {
			int allLength = array.length;
			if (lastParts != null) {
				for (byte[] part : lastParts) {
					allLength += part.length;
				}
				byte[] allByte = new byte[allLength];

				int offset = 0;
				for (byte[] part : lastParts) {
					System.arraycopy(part, 0, allByte, offset, part.length);
					offset += part.length;
				}
				System.arraycopy(array, 0, allByte, offset, array.length);
				array = allByte;
			}
