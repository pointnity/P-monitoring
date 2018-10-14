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
