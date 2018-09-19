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
		return cookie;
	}

	public static Map<String, String> getEqualMap(String cookieline) {
		Map<String, String> equalMap = new HashMap<>();
		String[] searchedStrings = searchByRegex(cookieline, "([^ ;,]+=[^ ;,]+)");
		for (String groupString : searchedStrings) {
			//The reason for not split here is that there is a possibility that an equal sign appears in the value string after the equals sign
			String[] equalStrings = new String[2];
			int equalCharIndex = groupString.indexOf("=");
			equalStrings[0] = groupString.substring(0, equalCharIndex);
			equalStrings[1] = groupString.substring(equalCharIndex + 1, groupString.length());
			if (equalStrings.length == 2) {
				String key = equalStrings[0];
				String value = equalStrings[1];
				if (value.startsWith("\"") && value.endsWith("\"")) {
					value = value.substring(1, value.length() - 1);
				}
				equalMap.put(key, value);
			}
		}
		return equalMap;
	}

	public static String[] searchByRegex(String source, String regex) {
		if (source == null) {
			return null;
		}

		Map<Integer, Pattern> regexPattern = new HashMap<>();

		Pattern pattern = null;
		if (regexPattern.containsKey(regex.hashCode())) {
			pattern = regexPattern.get(regex.hashCode());
		} else {
			pattern = Pattern.compile(regex);
			regexPattern.put(regex.hashCode(), pattern);
		}
		Matcher matcher = pattern.matcher(source);
		ArrayList<String> result = new ArrayList<>();
		while (matcher.find()) {
			result.add(matcher.group());
		}
		return result.toArray(new String[0]);
	}

	private String domain = null;
	private String path = null;
	private Long maxAge = null;

	private String expires = null;
	private boolean secure = false;

	private boolean httpOnly = false;

	private String name;

	private String value;

	/**
	 *
	 * @author tanyaowu
	 */
	public Cookie() {
	}

	/**
	 * Create a Cookie
	 * @param domain	cookie the Controlled domain
	 * @param name		Name
	 * @param value		Value
	 * @param maxAge	Failure time, Unit seconds
	 * @return Cookie Object
	 */
