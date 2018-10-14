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
	 * 
	 * @param domain Shaped like:".baidu.com"
	 * @author: tanyaowu
	 */
	public DomainSessionCookieDecorator(String domain) {
		this.domain = domain;
		
		Map<String, String> domainMap = new HashMap<>();
		domainMap.put("(\\w)*(" + domain + "){1}", domain);

		domainMappingSessionCookieDecorator = new DomainMappingSessionCookieDecorator(domainMap);
	}



	/** 
	 * @param sessionCookie
