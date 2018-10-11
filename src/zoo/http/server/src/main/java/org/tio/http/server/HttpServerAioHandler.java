package org.tio.http.server;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpRequestDecoder;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.HttpResponseEncoder;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.server.intf.ServerAioHandler;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpServerAioHandler implements ServerAioHandler {
	private static Logger log = LoggerFactory.getLogger(HttpServerAioHandler.class);

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public static void main(String[] args) {
	}

	protected HttpConfig httpConfig;

	//	protected Routes routes = null;

	//	public HttpServerAioHandler(HttpRequestHandler requestHandler) {
	//		this.requestHandler = requestHandler;
	//	}

	private HttpRequestHandler requestHandler;

	//	public HttpServerAioHandler(HttpConfig httpConfig, HttpRequestHandler requestHandler) {
	//		this(httpConfig, requestHandler);
	////		this.routes = routes;
	//	}

	/**
	 *
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
