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
