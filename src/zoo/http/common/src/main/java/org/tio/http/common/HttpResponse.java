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
	public String getCharset() {
		return charset;
	}

	/**
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @return the encodedBytes
	 */
	public byte[] getEncodedBytes() {
		return encodedBytes;
	}

	/**
	 * @return the request
	 */
	public HttpRequest getHttpRequest() {
		return request;
	}

	/**
	 * @return the status
	 */
	public HttpResponseStatus getStatus() {
		return status;
	}



	/**
	 * @return the isStaticRes
	 */
	public boolean isStaticRes() {
		return isStaticRes;
	}

	@Override
	public String logstr() {
		String str = null;
		if (request != null) {
			str = "\r\n Response: Request ID_" + request.getId() + "  " + request.getRequestLine().getPathAndQuery();
			str += "\r\n" + this.getHeaderString();
		} else {
			str = "\r\nResponse\r\n" + status.getHeaderText();
		}
		return str;
	}

	public void setBody(byte[] body, HttpRequest request) {
		this.body = body;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @param cookies the cookies to set
	 */
	public void setCookies(List<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @param encodedBytes the encodedBytes to set
	 */
	public void setEncodedBytes(byte[] encodedBytes) {
		this.encodedBytes = encodedBytes;
	}

	/**
	 * @param request the request to set
	 */
	public void setHttpRequestPacket(HttpRequest request) {
		this.request = request;
