package org.tio.websocket.server.demo1;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.common.HttpRequest;
import org.tio.common.HttpResponse;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 * @author tanyaowu
 * 
 */
public class WsDemoMsgHandler implements IWsMsgHandler {
	private static Logger log = LoggerFactory.getLogger(WsDemoMsgHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public WsDemoMsgHandler() {
	}

	@Override
	public HttpResponse handshake(HttpRequest request, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
		return httpResponse;
	}

	@Override
	public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
		String ss = new String(bytes, "utf-8");
		log.info("Receive a byte message:{},{}", bytes, ss);
