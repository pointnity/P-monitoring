package org.tio.websocket.server;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpRequestDecoder;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseEncoder;
import org.tio.http.common.HttpResponseStatus;
import org.tio.server.intf.ServerAioHandler;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.common.WsServerDecoder;
import org.tio.websocket.common.WsServerEncoder;
import org.tio.websocket.common.WsSessionContext;
import org.tio.websocket.common.util.BASE64Util;
import org.tio.websocket.common.util.SHA1Util;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 *
 * @author tanyaowu
 *
 */
public class WsServerAioHandler implements ServerAioHandler {
	private static Logger log = LoggerFactory.getLogger(WsServerAioHandler.class);

	//	private static Map<Command, ImBsHandlerIntf> handlerMap = new HashMap<>();
	//	static {
	//		handlerMap.put(Command.COMMAND_HANDSHAKE_REQ, new HandshakeReqHandler());
	//		handlerMap.put(Command.COMMAND_AUTH_REQ, new AuthReqHandler());
	//		handlerMap.put(Command.COMMAND_CHAT_REQ, new ChatReqHandler());
	//		handlerMap.put(Command.COMMAND_JOIN_GROUP_REQ, new JoinReqHandler());
	//		handlerMap.put(Command.COMMAND_HEARTBEAT_REQ, new HeartbeatReqHandler());
	//		handlerMap.put(Command.COMMAND_CLOSE_REQ, new CloseReqHandler());
	//
	//		handlerMap.put(Command.COMMAND_LOGIN_REQ, new LoginReqHandler());
	//		handlerMap.put(Command.COMMAND_CLIENT_PAGE_REQ, new ClientPageReqHandler());
	//
	//	}

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public static void main(String[] args) {
	}

	private WsServerConfig wsServerConfig;

	private IWsMsgHandler wsMsgHandler;

	/**
	 *
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public WsServerAioHandler(WsServerConfig wsServerConfig, IWsMsgHandler wsMsgHandler) {
		this.wsServerConfig = wsServerConfig;
		this.wsMsgHandler = wsMsgHandler;
	}

	/**
	 * @see org.tio.core.intf.AioHandler#decode(java.nio.ByteBuffer)
	 *
	 * @param buffer
	 * @return
	 * @throws AioDecodeException
	 * @author tanyaowu
	 * 
	 *
	 */
	@Override
	public WsRequest decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
		WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
		//		int initPosition = buffer.position();

		if (!wsSessionContext.isHandshaked()) {
			HttpRequest request = HttpRequestDecoder.decode(buffer, channelContext);
			if (request == null) {
				return null;
			}

			HttpResponse httpResponse = updateWebSocketProtocol(request, channelContext);
			if (httpResponse == null) {
				throw new AioDecodeException("HTTP protocol upgrade to WebSocket protocol failed");
			}

			wsSessionContext.setHandshakeRequestPacket(request);
			wsSessionContext.setHandshakeResponsePacket(httpResponse);

			WsRequest wsRequestPacket = new WsRequest();
			//			wsRequestPacket.setHeaders(httpResponse.getHeaders());
			//			wsRequestPacket.setBody(httpResponse.getBody());
			wsRequestPacket.setHandShake(true);

			return wsRequestPacket;
		}

		WsRequest websocketPacket = WsServerDecoder.decode(buffer, channelContext);
		return websocketPacket;
		//		if (websocketPacket == null) {
		//			return null;
		//		}
		//
		//		if (!websocketPacket.isWsEof()) {
		//			log.error("{} WebSocket Bag's not finished yet.", channelContext);
		//			return null;
		//		}
		//
		//		Opcode opcode = websocketPacket.getWsOpcode();
		//		if (opcode == Opcode.BINARY) {
		//			byte[] wsBody = websocketPacket.getWsBody();
		//			if (wsBody == null || wsBody.length == 0) {
		//				throw new AioDecodeException("Wrong WebSocket package, body is empty");
		//			}
		//
		//			WsRequest imPacket = new WsRequest();
		//
		//			if (wsBody.length > 1) {
		//				byte[] dst = new byte[wsBody.length - 1];
		//				System.arraycopy(wsBody, 1, dst, 0, dst.length);
		//				imPacket.setBody(dst);
		//			}
		//			return imPacket;
		//		} else if (opcode == Opcode.PING || opcode == Opcode.PONG) {
		//			return heartbeatPacket;
		//		} else if (opcode == Opcode.CLOSE) {
		//			WsRequest imPacket = new WsRequest();
		//			return imPacket;
		//		} else if (opcode == Opcode.TEXT) {
		//			throw new AioDecodeException("Bad WebSocket package, text-type data not supported");
		//		} else {
		//			throw new AioDecodeException("The wrong WebSocket package, the wrong opcode");
		//		}
	}

	/**
	 * @see org.tio.core.intf.AioHandler#encode(org.tio.core.intf.Packet)
	 *
	 * @param packet
	 * @return
	 * @author tanyaowu
	 * 
	 *
	 */
	@Override
	public ByteBuffer encode(Packet packet, GroupContext groupContext, ChannelContext channelContext) {
		WsResponse wsResponse = (WsResponse) packet;

		//Handshake Package
		if (wsResponse.isHandShake()) {
			WsSessionContext imSessionContext = (WsSessionContext) channelContext.getAttribute();
			HttpResponse handshakeResponsePacket = imSessionContext.getHandshakeResponsePacket();
			return HttpResponseEncoder.encode(handshakeResponsePacket, groupContext, channelContext, false);
		}

		ByteBuffer byteBuffer = WsServerEncoder.encode(wsResponse, groupContext, channelContext);
		return byteBuffer;
	}

	/**
	 * @return the httpConfig
	 */
	public WsServerConfig getHttpConfig() {
		return wsServerConfig;
	}

	private WsResponse h(WsRequest websocketPacket, byte[] bytes, Opcode opcode, ChannelContext channelContext) throws Exception {
		WsResponse wsResponse = null;
		if (opcode == Opcode.TEXT) {
			if (bytes == null || bytes.length == 0) {
				Aio.remove(channelContext, "Wrong WebSocket package, body is empty");
				return null;
			}
			String text = new String(bytes, wsServerConfig.getCharset());
			Object retObj = wsMsgHandler.onText(websocketPacket, text, channelContext);
			String methodName = "onText";
			wsResponse = processRetObj(retObj, methodName, channelContext);
			return wsResponse;
		} else if (opcode == Opcode.BINARY) {
			if (bytes == null || bytes.length == 0) {
				Aio.remove(channelContext, "Wrong WebSocket package, body is empty");
				return null;
			}
			Object retObj = wsMsgHandler.onBytes(websocketPacket, bytes, channelContext);
			String methodName = "onBytes";
			wsResponse = processRetObj(retObj, methodName, channelContext);
			return wsResponse;
		} else if (opcode == Opcode.PING || opcode == Opcode.PONG) {
			log.error("Roger that" + opcode);
		return null;
		} else if (opcode == Opcode.CLOSE) {
			Object retObj = wsMsgHandler.onClose(websocketPacket, bytes, channelContext);
			String methodName = "onClose";
			wsResponse = processRetObj(retObj, methodName, channelContext);
			return wsResponse;
		} else {
			Aio.remove(channelContext, "The wrong WebSocket package, the wrong opcode");
			return null;
		}
	}

	/**
	 * @see org.tio.core.intf.AioHandler#handler(org.tio.core.intf.Packet)
	 *
	 * @param packet
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 * 
	 *
	 */
	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		WsRequest wsRequestPacket = (WsRequest) packet;

		if (wsRequestPacket.isHandShake()) {
			WsSessionContext wsSessionContext = (WsSessionContext) channelContext.getAttribute();
			HttpRequest request = wsSessionContext.getHandshakeRequestPacket();
			HttpResponse httpResponse = wsSessionContext.getHandshakeResponsePacket();
			HttpResponse r = wsMsgHandler.handshake(request, httpResponse, channelContext);
			if (r == null) {
				Aio.remove(channelContext, "Business layer does not agree to shake hands");
