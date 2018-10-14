package org.tio.http.server.session;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.Cookie;

import com.xiaoleilu.hutool.util.ReUtil;

/**
 * @author tanyaowu 
 *  
 */
public class DomainMappingSessionCookieDecorator implements SessionCookieDecorator {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(DomainMappingSessionCookieDecorator.class);

	/**
	 * key:   Original domain, for example: , or it can be a regular expression, such as *.Baidu.Com
	 * value : Replace the domain of the original domain, for example.Baidu.Com
	 */
	private Map<String, String> domainMap = null;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public DomainMappingSessionCookieDecorator(Map<String, String> domainMap) {
		this.domainMap = domainMap;
	}
	
	protected DomainMappingSessionCookieDecorator() {
		
	}

	public void addMapping(String key, String value) {
		domainMap.put(key, value);
	}

	public void removeMapping(String key) {
		domainMap.remove(key);
	}

	/** 
	 * @param sessionCookie
	 * @author: tanyaowu
	 */
	@Override
	public void decorate(Cookie sessionCookie) {
		Set<Entry<String, String>> set = domainMap.entrySet();
		String initDomain = sessionCookie.getDomain();
		for (Entry<String, String> entry : set) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.equalsIgnoreCase(key, initDomain) || ReUtil.isMatch(key, initDomain)) {
				sessionCookie.setDomain(value);
			}
		}
	}

	/**
	 * @param args
	 * @author: tanyaowu
