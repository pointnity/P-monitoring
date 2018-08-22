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
