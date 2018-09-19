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
