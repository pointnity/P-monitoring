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
