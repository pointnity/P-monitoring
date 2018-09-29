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
		if (regexPattern.containsKey(regex.hashCode())) {
			pattern = regexPattern.get(regex.hashCode());
		} else {
			pattern = Pattern.compile(regex);
			regexPattern.put(regex.hashCode(), pattern);
		}
		return pattern;
	}

	/**
	 * Parse all the Equals expressions in a string into a Map
	 * @param str
	 *             An equation expression
	 * @return An equals expression Map
	 */
	public static Map<String, String> getEqualMap(String str) {
		Map<String, String> equalMap = new HashMap<>();
		String[] searchedStrings = searchByRegex(str, "([^ ;,]+=[^ ;,]+)");
		for (String groupString : searchedStrings) {
			//The reason for not split here is that there is a possibility that an equal sign appears in the value string after the equals sign
			String[] equalStrings = new String[2];
			int equalCharIndex = groupString.indexOf("=");
