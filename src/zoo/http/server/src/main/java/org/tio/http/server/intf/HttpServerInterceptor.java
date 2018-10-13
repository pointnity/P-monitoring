package org.tio.http.server.intf;

import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.common.RequestLine;

/**
 * @author tanyaowu
 *  
 */
public interface HttpServerInterceptor {

	/**
	 * In the execution org.tio.http.server.handler.IHttpRequestHandler.handler(HttpRequestPacket, RequestLine, ChannelContext<HttpSessionContext, HttpPacket, Object>)This method will be called after the business layer can be unified here to give httpresponsepacket some decoration
	 * @param request
	 * @param requestLine
	 * @param channelContext
	 * @param response
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public void doAfterHandler(HttpRequest request, RequestLine requestLine, HttpResponse response) throws Exception;

	/**
	 * In the execution org.tio.http.server.handler.IHttpRequestHandler.handler(HttpRequestPacket, RequestLine, ChannelContext<HttpSessionContext, HttpPacket, Object>)This method is called before 
	 *If the Httpresponsepacket object is returned, subsequent executions are no longer performed, indicating that the call stack ends 
	 * @param request
	 * @param requestLine
	 * @param channelContext
	 * @param responseFromCache HttpResponse objects obtained from the cache
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public HttpResponse doBeforeHandler(HttpRequest request, RequestLine requestLine, HttpResponse responseFromCache) throws Exception;

}
