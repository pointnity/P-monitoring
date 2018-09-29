package org.tio.http.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author tanyaowu
 * 
 */
public class HttpParseUtils {
	//	private static Logger log = LoggerFactory.getLogger(HttpParseUtils.class);

	/**
	 * 
	 * 
	 *
	 * ------  start  ------
	 */
	private static ConcurrentHashMap<Integer, Pattern> regexPattern = new ConcurrentHashMap<>();

	private static Pattern getCachedPattern(String regex) {
		Pattern pattern = null;
