package org.tio.http.server.session;

import org.tio.http.common.Cookie;

/**
 * @author tanyaowu 
 *  
 */
public interface SessionCookieDecorator {
	/**
	 * DefaultHttpRequestHandlerA cookie is created for the session based on the host field, and the user can customize the cookie by using this method.
	 * For example, the domain name of the cookie is www.Baidu.COM changed to.Baidu.Com
