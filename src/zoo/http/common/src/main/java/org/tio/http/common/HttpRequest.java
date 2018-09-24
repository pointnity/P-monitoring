package org.tio.http.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.core.ChannelContext;
import org.core.Node;
import org.http.common.HttpConst.RequestBodyFormat;
import org.http.common.session.HttpSession;
import org.http.common.utils.IpUtils;

import com.xiaoleilu.hutool.util.ArrayUtil;
import com.xiaoleilu.hutool.util.StrUtil;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpRequest extends HttpPacket {

	//	private static Logger log = LoggerFactory.getLogger(HttpRequest.class);

	private static final long serialVersionUID = -3849253977016967211L;

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public static void main(String[] args) {
	}

	private RequestLine requestLine = null;
	/**
	 *Request parameters
	 */
	private Map<String, Object[]> params = null;
	private List<Cookie> cookies = null;
	private Map<String, Cookie> cookieMap = null;
	private int contentLength;
	//	private byte[] bodyBytes;
	private String bodyString;
	//	private UserAgent userAgent;
	private RequestBodyFormat bodyFormat;
	private String charset = HttpConst.CHARSET_NAME;
	private Boolean isAjax = null;
	private Boolean isSupportGzip = null;
	private HttpSession httpSession;
	private Node remote = null;
	//	private HttpSession httpSession = null;
	private ChannelContext channelContext;

	private HttpConfig httpConfig;

	private String domain = null;
	private String host = null;
	private String clientIp = null;

	/**
	 *
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public HttpRequest(Node remote) {
		this.remote = remote;
	}

	public void addParam(String key, Object value) {
		if (params == null) {
			params = new HashMap<>();
		}

		Object[] existValue = params.get(key);
		if (existValue != null) {
			Object[] newExistValue = new Object[existValue.length + 1];
			System.arraycopy(existValue, 0, newExistValue, 0, existValue.length);
			newExistValue[newExistValue.length - 1] = value;
			params.put(key, newExistValue);
		} else {
			Object[] newExistValue = new Object[] { value };
			params.put(key, newExistValue);
		}
	}

	/**
	 * @return the bodyFormat
	 */
	public RequestBodyFormat getBodyFormat() {
		return bodyFormat;
	}

	/**
	 * Get the User-agent field in the request header
	 * @return
	 * @author: tanyaowu
	 */
	public String getUserAgent() {
		return this.headers.get(HttpConst.RequestHeaderKey.User_Agent);
	}

	/**
	 * Gets the host field in the request header, in the form: www.T-io.org:8080, www.T-io.ORG equivalent
	 * @return
	 * @author: tanyaowu
	 */
	public String getHost() {
		if (host != null) {
			return host;
		}
		
		host = this.headers.get(HttpConst.RequestHeaderKey.Host);
		return host;
	}
	
	/**
	 * Get the Real client IP
	 * @return
	 * @author tanyaowu
	 */
	public String getClientIp() {
		if (clientIp == null) {
			clientIp = IpUtils.getRealIp(this);
		}		
		return clientIp;
	}
	

	/**
	 * Based on the host field, gets the value of the pure domain name portion of the stripped port, as follows: ORG equivalent
	 * @return
	 * @author tanyaowu
	 */
	public String getDomain() {
		if (domain != null) {
			return domain;
		}
		if (StrUtil.isBlank(getHost())) {
			return null;
		}
		domain = StrUtil.subBefore(getHost(), ":", false);
		return domain;
	}

	/**
	 * @return the bodyString
	 */
	public String getBodyString() {
		return bodyString;
	}

	/**
	 * @return the channelContext
	 */
	public ChannelContext getChannelContext() {
		return channelContext;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @return the bodyLength
	 */
	public int getContentLength() {
		return contentLength;
	}

	public Cookie getCookie(String cooiename) {
		if (cookieMap == null) {
			return null;
		}
		return cookieMap.get(cooiename);
	}

	/**
	 * @return the cookieMap
	 */
	public Map<String, Cookie> getCookieMap() {
		return cookieMap;
	}

	//	/**
	//	 * @return the bodyBytes
	//	 */
	//	public byte[] getBodyBytes() {
	//		return bodyBytes;
	//	}
	//
	//	/**
	//	 * @param bodyBytes the bodyBytes to set
	//	 */
	//	public void setBodyBytes(byte[] bodyBytes) {
	//		this.bodyBytes = bodyBytes;
	//	}

	//	/**
	//	 * @return the userAgent
	//	 */
	//	public UserAgent getUserAgent() {
	//		return userAgent;
	//	}
	//
	//	/**
	//	 * @param userAgent the userAgent to set
	//	 */
	//	public void setUserAgent(UserAgent userAgent) {
	//		this.userAgent = userAgent;
	//	}

	/**
	 * @return the cookies
	 */
	public List<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * @return the httpConfig
	 */
	public HttpConfig getHttpConfig() {
		return httpConfig;
	}

	/**
	 * @return the httpSession
	 */
	public HttpSession getHttpSession() {
		return httpSession;
	}

	/**
	 * @return the isAjax
	 */
	public Boolean getIsAjax() {
		if (isAjax == null) {
			String X_Requested_With = this.getHeader(HttpConst.RequestHeaderKey.X_Requested_With);
			if (X_Requested_With != null && "XMLHttpRequest".equalsIgnoreCase(X_Requested_With)) {
				isAjax = true;
			} else {
				isAjax = false;
			}
		}

		return isAjax;
	}
