package org.http.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpResponse extends HttpPacket {
	private static Logger log = LoggerFactory.getLogger(HttpResponse.class);

	private static final long serialVersionUID = -3512681144230291786L;

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public static void main(String[] args) {
	}

	private HttpResponseStatus status = HttpResponseStatus.C200;

	/**
	 * Whether it is a static resource
         * True: Static resources
	 */
	private boolean isStaticRes = false;

	private HttpRequest request = null;
	private List<Cookie> cookies = null;

	//	private int contentLength;
	//	private byte[] bodyBytes;
	private String charset = HttpConst.CHARSET_NAME;

	/**
	 * Already coded byte[]
	 */
	private byte[] encodedBytes = null;

	/**
	 *
	 * @param request
	 * @param httpConfig 可以为null
	 * @author tanyaowu
	 */
	public HttpResponse(HttpRequest request, HttpConfig httpConfig) {
		this.request = request;

		String Connection = StringUtils.lowerCase(request.getHeader(HttpConst.RequestHeaderKey.Connection));
		RequestLine requestLine = request.getRequestLine();
		String version = requestLine.getVersion();
		if ("1.0".equals(version)) {
			if (StringUtils.equals(Connection, HttpConst.RequestHeaderValue.Connection.keep_alive)) {
				addHeader(HttpConst.ResponseHeaderKey.Connection, HttpConst.ResponseHeaderValue.Connection.keep_alive);
				addHeader(HttpConst.ResponseHeaderKey.Keep_Alive, "timeout=10, max=20");
			} else {
				addHeader(HttpConst.ResponseHeaderKey.Connection, HttpConst.ResponseHeaderValue.Connection.close);
			}
		} else {
			if (StringUtils.equals(Connection, HttpConst.RequestHeaderValue.Connection.close)) {
				addHeader(HttpConst.ResponseHeaderKey.Connection, HttpConst.ResponseHeaderValue.Connection.close);
			} else {
				addHeader(HttpConst.ResponseHeaderKey.Connection, HttpConst.ResponseHeaderValue.Connection.keep_alive);
				addHeader(HttpConst.ResponseHeaderKey.Keep_Alive, "timeout=10, max=20");
			}
		}
		

		if (httpConfig != null) {
			addHeader(HttpConst.ResponseHeaderKey.Server, httpConfig.getServerInfo());
		}
		//		String xx = DatePattern.HTTP_DATETIME_FORMAT.format(SystemTimer.currentTimeMillis());
		//		addHeader(HttpConst.ResponseHeaderKey.Date, DatePattern.HTTP_DATETIME_FORMAT.format(SystemTimer.currentTimeMillis()));
		//		addHeader(HttpConst.ResponseHeaderKey.Date, new Date().toGMTString());
	}
	
	/**
	 * Get "Content-type" header content
	 * @return
	 * @author tanyaowu
	 */
	public String getContentType() {
		return this.headers.get(HttpConst.RequestHeaderKey.Content_Type);
	}

	public boolean addCookie(Cookie cookie) {
		if (cookies == null) {
			synchronized (this) {
				if (cookies == null) {
					cookies = new ArrayList<>();
				}
			}
		}
		return cookies.add(cookie);
	}

	/**
	 * @return the charset
	 */
