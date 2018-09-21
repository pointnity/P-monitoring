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
		 * Therefore, the servlet should look through the accept-encoding header (that is, request.GetHeader ("accept-encoding")) check if the browser supports gzip,
		 * Returns the gzip-compressed HTML page for a browser that supports gzip, returning a normal page for another browser.
		 */
		String Content_Encoding = "Content-Encoding".toLowerCase();
		/**
		 * Indicates what MIME type the following document belongs to.The servlet defaults to Text/plain, but it usually needs to be explicitly specified as text/html.
		 * Because Content-type is often set up, HttpServletResponse provides a dedicated method setContentType.
		 */
		String Content_Type = "Content-Type".toLowerCase();
		/**
		 * The current GMT time.You can use Setdateheader to set this header to avoid the hassle of converting the time format.
		 */
		String Date = "Date".toLowerCase();
		/**
		 * When should I think that the document has expired so that it is no longer cached?
		 */
		String Expires = "Expires".toLowerCase();
		/**
		 * The last modification time of the document.The customer can provide a date through the If-modified-since request header, which will be treated as a conditional get,
		 * Only documents that have been modified later than the specified time are returned, otherwise a 304 (not Modified) state is returned.Last-modified can also be set using the Setdateheader method.
		 */
		String Last_Modified = "Last-Modified".toLowerCase();
		/**
		 *Indicates where the customer should go to extract the document.Location is usually not set directly, but by HttpServletResponse's Sendredirect method, which sets the status code to 302.
		 */
		String Location = "Location".toLowerCase();
		/**
		 *Indicates how much time the browser should refresh the document, in seconds.In addition to refreshing the current document, you can also pass SetHeader ("Refresh", "5;Url//host/path ") lets the browser read the specified page.
		Note This functionality is usually done by setting the HTML page in the head area of the For Servlets, however, it is more convenient to set the refresh header directly.
		
		Note that the meaning of refresh is "refresh this page after n seconds or visit the specified page" instead of "refresh this page every n seconds or visit the specified page".Therefore, continuous refresh requires a refresh header to be sent each time, and sending a 204 status code prevents the browser from continuing to refresh, whether it is using the refresh header or the >
		
		Note that the refresh header is not part of the HTTP 1.1 formal specification, but rather an extension, but both Netscape and IE support it.
		 */
		String Refresh = "Refresh".toLowerCase();
		/**
		 * Server name.The servlet generally does not set this value, but is set by the Web server itself.
		 */
		String Server = "Server".toLowerCase();

		/**
		 *
		 */
		String Access_Control_Allow_Origin = "Access-Control-Allow-Origin".toLowerCase(); //value: *

		/**
		 *
		 */
		String Access_Control_Allow_Headers = "Access-Control-Allow-Headers".toLowerCase(); //value: x-requested-with,content-type

		/**
		 * Is the data obtained from the cache, Tio-httpserver unique header information
		 */
		String tio_from_cache = "tio-from-cache";
	}

	/**
	 *
	 * @author tanyaowu
	 * 
	 */
	public interface ResponseHeaderValue {
		public interface Connection {
			String keep_alive = "keep-alive".toLowerCase();
			String Upgrade = "Upgrade".toLowerCase();
			String close = "close".toLowerCase();
		}
	}

	/**
	 *
	 */
	String SERVER_INFO = "tio-httpserver/0.0.1";

	/**
