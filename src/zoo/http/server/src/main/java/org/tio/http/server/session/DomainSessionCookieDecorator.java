package org.tio.http.server.session;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.Cookie;

import com.xiaoleilu.hutool.util.ReUtil;

/**
 * @author tanyaowu 
 *  
 */
public class DomainSessionCookieDecorator implements SessionCookieDecorator {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(DomainSessionCookieDecorator.class);

	/**
	 *Shaped like:".baidu.com"
	 */
	private String domain;
	
	private DomainMappingSessionCookieDecorator domainMappingSessionCookieDecorator;

	/**
