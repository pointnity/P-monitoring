package org.http.common.handler;

import org.http.common.HttpRequest;
import org.http.common.HttpResponse;
import org.http.common.RequestLine;

/**
 * http request processor
 * @author tanyaowu 
 * 
 */
public interface HttpRequestHandler {
	/**
	 * Processing requests
	 * @param packet
	 * @return can be null
	 * @throws Exception
	 * @author tanyaowu
	 */
	public HttpResponse handler(HttpRequest packet) throws Exception;

	/**
	 *Response404
	 * @param request
	 * @param requestLine
	 * @param channelContext
	 * @return
	 * @author tanyaowu
	 */
	public HttpResponse resp404(HttpRequest request, RequestLine requestLine);

	/**
	 * Response500
	 * @param request
	 * @param requestLine
	 * @param throwable
	 * @return
	 * @author tanyaowu
	 */
