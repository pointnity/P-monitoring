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
