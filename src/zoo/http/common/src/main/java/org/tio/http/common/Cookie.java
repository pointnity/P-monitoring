package org.tio.http.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * helyho/Voovan
 * @author tanyaowu
 * 
 */
public class Cookie {
	private static Logger log = LoggerFactory.getLogger(Cookie.class);

	/**
	 *Build a MAP with maps Cookie Object
	 * @param cookieMap Cookie Property Map
	 * @return Cookie Object
	 */
	public static Cookie buildCookie(Map<String, String> cookieMap) {
		Cookie cookie = new Cookie();
		for (Entry<String, String> cookieMapItem : cookieMap.entrySet()) {
			switch (cookieMapItem.getKey().toLowerCase()) {
			case "domain":
				cookie.setDomain(cookieMapItem.getValue());
				break;
			case "path":
				cookie.setPath(cookieMapItem.getValue());
				break;
			case "max-age":
				cookie.setMaxAge(Long.parseLong(cookieMapItem.getValue()));
				break;
			case "secure":
				cookie.setSecure(true);
				break;
			case "httponly":
				cookie.setHttpOnly(true);
				break;
			case "expires":
				cookie.setExpires(cookieMapItem.getValue());
				break;
			default:
				cookie.setName(cookieMapItem.getKey());
				try {
					cookie.setValue(URLDecoder.decode(cookieMapItem.getValue(), HttpConst.CHARSET_NAME));
				} catch (UnsupportedEncodingException e) {
					log.error(e.toString(), e);
				}
				break;
			}
		}
