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
	private String sessionCacheName = SESSION_CACHE_NAME;

	/**
	 * session Time-out, units: seconds
	 */
	private long sessionTimeout = DEFAULT_SESSION_TIMEOUT;

	private String sessionCookieName = SESSION_COOKIE_NAME;

	/**
	 * The static resource cache time, if less than or equal to 0, is not cached, units: seconds
	 */
	private int maxLiveTimeOfStaticRes = MAX_LIVETIME_OF_STATICRES;

	private String page404 = "/404.html";

	//	private HttpSessionManager httpSessionManager;

	private String page500 = "/500.html";

	private ISessionIdGenerator sessionIdGenerator;
	
	private HttpRequestHandler httpRequestHandler;
	
	/**
	 * Whether the agent is
	 */
	private boolean isProxied = false;

	/**
	 * Example:
	 * 1、classpath in：page
	 * 2、Absolute path：/page
	 */
	private File pageRoot = null;//FileUtil.getAbsolutePath("page");//"/page";

	//	/**
	//	 * @return the httpSessionManager
	//	 */
	//	public HttpSessionManager getHttpSessionManager() {
	//		return httpSessionManager;
	//	}
	//
	//	/**
	//	 * @param httpSessionManager the httpSessionManager to set
	//	 */
	//	public void setHttpSessionManager(HttpSessionManager httpSessionManager) {
	//		this.httpSessionManager = httpSessionManager;
	//	}

	/**
	 *
	 * @author tanyaowu
	 */
	public HttpConfig(Integer bindPort, Long sessionTimeout, String contextPath, String suffix) {
		this.bindPort = bindPort;
		if (sessionTimeout != null) {
			this.sessionTimeout = sessionTimeout;
		}
		
		if (contextPath == null) {
			contextPath = "";
		}
		this.contextPath = contextPath;
		
		if (suffix == null) {
			suffix = "";
		}
		this.suffix = suffix;
	}

	//	private File rootFile = null;

	/**
	 * @return the bindIp
	 */
	public String getBindIp() {
		return bindIp;
	}

	/**
	 * @return the bindPort
	 */
	public Integer getBindPort() {
		return bindPort;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @return the maxLiveTimeOfStaticRes
	 */
	public int getMaxLiveTimeOfStaticRes() {
		return maxLiveTimeOfStaticRes;
	}

	public String getPage404() {
		return page404;
	}

	public String getPage500() {
		return page500;
	}

	/**
	 * @return the pageRoot
	 */
	public File getPageRoot() {
		return pageRoot;
	}

	/**
	 * @return the serverInfo
	 */
	public String getServerInfo() {
		return serverInfo;
	}

	/**
	 * @return the sessionCacheName
	 */
	public String getSessionCacheName() {
		return sessionCacheName;
	}

	public String getSessionCookieName() {
		return sessionCookieName;
	}

	//	public void setSessionTimeout(long sessionTimeout) {
	//		this.sessionTimeout = sessionTimeout;
	//	}

	public ISessionIdGenerator getSessionIdGenerator() {
		return sessionIdGenerator;
	}

	public ICache getSessionStore() {
		return sessionStore;
	}

	public long getSessionTimeout() {
		return sessionTimeout;
	}

	/**
	 * @param bindIp the bindIp to set
	 */
	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	/**
	 * @param charset the charset to set
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @param maxLiveTimeOfStaticRes the maxLiveTimeOfStaticRes to set
	 */
	public void setMaxLiveTimeOfStaticRes(int maxLiveTimeOfStaticRes) {
		this.maxLiveTimeOfStaticRes = maxLiveTimeOfStaticRes;
	}

	public void setPage404(String page404) {
		this.page404 = page404;
	}

	public void setPage500(String page500) {
		this.page500 = page500;
	}

	/**
	 * 
	 * @param pageRoot
	 * @author tanyaowu
	 * @throws IOException 
	 */
	public void setPageRoot(String pageRoot) throws IOException {
		if (pageRoot == null) {
			return;
		}
		
		if (StringUtils.startsWithIgnoreCase(pageRoot, "classpath:")) {
			this.pageRoot = new File(FileUtil.getAbsolutePath(pageRoot));
		} else {
			this.pageRoot = new File(pageRoot);
		}
	}

	/**
	 * @param serverInfo the serverInfo to set
	 */
	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * @param sessionCacheName the sessionCacheName to set
	 */
	public void setSessionCacheName(String sessionCacheName) {
		this.sessionCacheName = sessionCacheName;
	}

	public void setSessionCookieName(String sessionCookieName) {
		this.sessionCookieName = sessionCookieName;
	}

	public void setSessionIdGenerator(ISessionIdGenerator sessionIdGenerator) {
		this.sessionIdGenerator = sessionIdGenerator;
	}

	public void setSessionStore(ICache sessionStore) {
		this.sessionStore = sessionStore;
		//		this.httpSessionManager = HttpSessionManager.getInstance(sessionStore);
	}

	/**
	 * @return the httpRequestHandler
	 */
	public HttpRequestHandler getHttpRequestHandler() {
		return httpRequestHandler;
	}

	/**
	 * @param httpRequestHandler the httpRequestHandler to set
	 */
	public void setHttpRequestHandler(HttpRequestHandler httpRequestHandler) {
		this.httpRequestHandler = httpRequestHandler;
	}

	public String getContextPath() {
		return contextPath;
	}

	public String getSuffix() {
		return suffix;
	}

	public String[] getAllowDomains() {
		return allowDomains;
	}

	public void setAllowDomains(String[] allowDomains) {
		this.allowDomains = allowDomains;
	}

	/**
	 * @return the isProxied
	 */
	public boolean isProxied() {
		return isProxied;
	}

	/**
	 * @param isProxied the isProxied to set
	 */
	public void setProxied(boolean isProxied) {
		this.isProxied = isProxied;
	}
}
