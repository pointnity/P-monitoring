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
	                      Host : t-io.org:9321
	           Accept-Encoding : gzip, deflate, sdch
	                User-Agent : Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36
	                    Origin 
	         Sec-WebSocket-Key : kmCL2C7q9vtNSMyHpft7lw==
	                Connection : Upgrade
	             Cache-Control : no-cache
