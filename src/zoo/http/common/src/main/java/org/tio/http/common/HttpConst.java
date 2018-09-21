package org.tio.http.common;

/**
 *
 * @author tanyaowu
 *
 */
public interface HttpConst {

	/**
	 * Format of the request body
	 * @author tanyaowu
	 *  
	 */
	public enum RequestBodyFormat {
		URLENCODED, MULTIPART, TEXT
	}

	/**
	 *         Accept-Language : zh-CN,zh;q=0.8
	     Sec-WebSocket-Version : 13
	  Sec-WebSocket-Extensions : permessage-deflate; client_max_window_bits
	                   Upgrade : websocket
