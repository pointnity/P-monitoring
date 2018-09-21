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
	                    Pragma : no-cache
	 *
	 * @author tanyaowu
	 * 
	 */
	public interface RequestHeaderKey {
		String Cookie = "Cookie".toLowerCase();//Cookie: $Version=1; Skin=new;
		String Origin = "Origin".toLowerCase(); /127.0.0.1
		String Sec_WebSocket_Key = "Sec-WebSocket-Key".toLowerCase(); //2GFwqJ1Z37glm62YKKLUeA==
		String Cache_Control = "Cache-Control".toLowerCase(); //no-cache
		String Connection = "Connection".toLowerCase(); //Upgrade,  keep-alive
		String User_Agent = "User-Agent".toLowerCase(); //Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3088.3 Safari/537.36
		String Sec_WebSocket_Version = "Sec-WebSocket-Version".toLowerCase(); //13
		String Host = "Host".toLowerCase(); //127.0.0.1:9321
		String Pragma = "Pragma".toLowerCase(); //no-cache
