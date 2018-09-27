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
