package org.tio.http.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.common.session.id.ISessionIdGenerator;
import org.tio.utils.cache.ICache;

import com.xiaoleilu.hutool.io.FileUtil;

/**
 * @author tanyaowu
 * 
 */
public class HttpConfig {

	//	private static Logger log = LoggerFactory.getLogger(HttpConfig.class);

	/**
	 *That holds the HttpSession object.cacheName
	 */
	public static final String SESSION_CACHE_NAME = "tio-h-s";

	/**
	 * Storage of SessionID cookie name
	 */
	public static final String SESSION_COOKIE_NAME = "big-tio";

	/**
	 * session Default time-out, units: seconds
	 */
	public static final long DEFAULT_SESSION_TIMEOUT = 30 * 60;

	/**
	 * Default static resource cache time, units: seconds
	 */
	public static final int MAX_LIVETIME_OF_STATICRES = 60 * 10;
	
	/**
	 * The maximum length of the boundary value when the file is uploaded
	 */
	public static final int MAX_LENGTH_OF_BOUNDARY = 256;
	
	/**
	 * Maximum length of the head when uploading files
	 */
	public static final int MAX_LENGTH_OF_MULTI_HEADER = 128;
	
	/**
	 * Maximum length of the body when uploading files
	 */
	public static final int MAX_LENGTH_OF_MULTI_BODY = 1024 * 1024 * 20;

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	private String bindIp = null;//"127.0.0.1";

	/**
	 * Listening port
	 */
	private Integer bindPort = 80;

	private String serverInfo = HttpConst.SERVER_INFO;

	private String charset = HttpConst.CHARSET_NAME;

	private ICache sessionStore = null;
	
	private String contextPath = "";
	
	private String suffix = "";
	
	/**
	 * The domain name that is allowed to be accessed, or null if not limited
	 */
	private String[] allowDomains = null;

	/**
	 * That holds the HttpSession object.cacheName
	 */
