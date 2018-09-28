package org.http.common.session;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpConfig;

/**
 *
 * @author tanyaowu
 * 
 */
public class HttpSession implements java.io.Serializable {
	private static Logger log = LoggerFactory.getLogger(HttpSession.class);

	private static final long serialVersionUID = 6077020620501316538L;

	private Map<String, Serializable> data = new ConcurrentHashMap<>();

	private String id = null;

	/**
	 *The empty constructor here must have
	 * 
	 * @author: tanyaowu
	 */
	public HttpSession() {
	}

	/**
	 * @author tanyaowu
	 */
	public HttpSession(String id) {
		this.id = id;
	}

	/**
	 * Clear All Properties
	 * @param httpConfig
	 * @author tanyaowu
	 */
	public void clear(HttpConfig httpConfig) {
		data.clear();
		update(httpConfig);
	}

	/**
	 * Get Session Properties
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	public Object getAttribute(String key) {
		return data.get(key);
	}

	/**
	 * 
	 * @param key
	 * @param clazz
	 * @return
	 * @author: tanyaowu
	 */
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> clazz) {
		return (T) data.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String key, Class<T> clazz, T defaultObj) {
		T t = (T) data.get(key);
		if (t == null) {
			log.warn("key【{}】'value in session is null", key);
			return defaultObj;
		}
		return t;
	}

	//	public Map<String, Serializable> getData() {
	//		return data;
	//	}

	public String getId() {
		return id;
	}

	/**
	 *
	 * @param key
	 * @param httpConfig
	 * @author tanyaowu
	 */
	public void removeAttribute(String key, HttpConfig httpConfig) {
		data.remove(key);
		update(httpConfig);
	}

	/**
	 * Set session Properties
	 * @param key
