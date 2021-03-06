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
	public HttpServerAioHandler(HttpConfig httpConfig, HttpRequestHandler requestHandler) {
		this.httpConfig = httpConfig;
		this.requestHandler = requestHandler;
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
	public HttpRequest decode(ByteBuffer buffer, ChannelContext channelContext) throws AioDecodeException {
		HttpRequest request = HttpRequestDecoder.decode(buffer, channelContext);
		return request;
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
		HttpResponse httpResponse = (HttpResponse) packet;
		ByteBuffer byteBuffer = HttpResponseEncoder.encode(httpResponse, groupContext, channelContext, false);
		return byteBuffer;
	}

	/**
	 * @return the httpConfig
	 */
	public HttpConfig getHttpConfig() {
		return httpConfig;
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
		HttpRequest request = (HttpRequest) packet;
		String ip = request.getClientIp();
		
		if (channelContext.getGroupContext().ipBlacklist.isInBlacklist(ip)) {
			Aio.remove(channelContext, ip + "In the Blacklist");
			return;
		}
		
		HttpResponse httpResponse = requestHandler.handler(request);
		if (httpResponse != null) {
			Aio.send(channelContext, httpResponse);
		} else {
			log.info("{}, {}, handler return null, request line: {}", channelContext.getGroupContext().getName(), channelContext.toString(), request.getRequestLine().getLine());
			Aio.remove(channelContext, "handler return null");
		}
	}

	/**
	 * @param httpConfig the httpConfig to set
	 */
	public void setHttpConfig(HttpConfig httpConfig) {
		this.httpConfig = httpConfig;
	}

}
