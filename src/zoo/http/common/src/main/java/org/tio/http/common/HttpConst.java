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
		String Accept_Encoding = "Accept-Encoding".toLowerCase(); //gzip, deflate, br
		String Accept_Language = "Accept-Language".toLowerCase(); //zh-CN,zh;q=0.8,en;q=0.6
		String Upgrade = "Upgrade".toLowerCase(); //websocket
		String Sec_WebSocket_Extensions = "Sec-WebSocket-Extensions".toLowerCase(); //permessage-deflate; client_max_window_bits
		String Content_Length = "Content-Length".toLowerCase(); //65
		String Content_Type = "Content-Type".toLowerCase();// : 【application/x-www-form-urlencoded】【application/x-www-form-urlencoded; charset=UTF-8】【multipart/form-data; boundary=----WebKitFormBoundaryuwYcfA2AIgxqIxA0 】
		String If_Modified_Since = "If-Modified-Since".toLowerCase(); //与Last-Modifiedc With

		/**
		 * Value is XMLHttpRequest is the Ajax
		 */
		String X_Requested_With = "X-Requested-With".toLowerCase();//XMLHttpRequest
	}

	//	Content-Type: text/html;charset:utf-8;

	/**
	 *
	 * @author tanyaowu
	 * 
	 */
	public interface RequestHeaderValue {
		public interface Connection {
			String keep_alive = "keep-alive".toLowerCase();
			String Upgrade = "Upgrade".toLowerCase();
			String close = "close".toLowerCase();
		}

		//application/x-www-form-urlencoded、multipart/form-data、text/plain
		public interface Content_Type {
			/**
			 * Normal text, usually JSON or xml
			 */
			String text_plain = "text/plain".toLowerCase();
			/**
			 * File Upload
			 */
			String multipart_form_data = "multipart/form-data".toLowerCase();
			/**
			 * Normal.key-value
			 */
			String application_x_www_form_urlencoded = "application/x-www-form-urlencoded".toLowerCase();
		}
	}

	public interface ResponseHeaderKey {
		//Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
		String Set_Cookie = "Set-Cookie".toLowerCase(); //Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1
		String Content_Length = "Content-Length".toLowerCase(); //65

		String Connection = "Connection".toLowerCase(); //Upgrade,  keep-alive
		String Keep_Alive = "Keep-Alive".toLowerCase(); //Keep-Alive:timeout=20
		String Sec_WebSocket_Accept = "Sec-WebSocket-Accept".toLowerCase();
		String Upgrade = "Upgrade".toLowerCase();

		/**
		 * Content-Disposition: attachment;filename=FileName.txt
		 * File download
		 */
		String Content_disposition = "Content-disposition".toLowerCase();
		/**
		 * The encoding (Encode) method of the document.Can only be obtained after decoding Content-Type the content type specified by the header.
		 * Using gzip to compress documents can significantly reduce the download time of HTML documents.
		 * Java's gzipoutputstream can be easily gzip compressed, but only on Unix Netscape and IE 4, ie 5 on Windows.
