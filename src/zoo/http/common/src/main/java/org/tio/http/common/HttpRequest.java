package org.tio.http.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.http.common.HttpConst.RequestBodyFormat;
import org.tio.http.common.session.HttpSession;
import org.tio.http.common.utils.IpUtils;

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
