package org.tio.http.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.tio.core.intf.Packet;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpPacket extends Packet {

	//	private static Logger log = LoggerFactory.getLogger(HttpPacket.class);

	private static final long serialVersionUID = 3903186670675671956L;

	//	public static final int MAX_LENGTH_OF_BODY = (int) (1024 * 1024 * 5.1); //Only how much m data is supported

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	private Map<String, Serializable> props = new ConcurrentHashMap<>();
	
	/**
	 * Get Request Properties
	 * @param key
	 * @return
	 * @author tanyaowu
	 */
	public Object getAttribute(String key) {
		return props.get(key);
	}
	
	public Object getAttribute(String key, Serializable defaultValue) {
		Serializable ret = props.get(key);
		if (ret == null) {
			return defaultValue;
		}
		return ret;
	}
	
	/**
	 *
	 * @param key
	 * @param key
	 * @param httpConfig
	 * @author tanyaowu
	 */
	public void removeAttribute(String key) {
		props.remove(key);
	}

	/**
	 * Set request Properties
	 * @param key
	 * @param value
	 * @param httpConfig
	 * @author tanyaowu
	 */
	public void setAttribute(String key, Serializable value) {
		props.put(key, value);
	}
	
	protected byte[] body;

	private String headerString;

	protected Map<String, String> headers = new HashMap<>();

	public HttpPacket() {
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addHeaders(Map<String, String> headers) {
		if (headers != null) {
			this.headers.putAll(headers);
		}
	}

	/**
	 * @return the body
	 */
	public byte[] getBody() {
		return body;
	}

	public String getHeader(String key) {
		return headers.get(key);
	}

	/**
	 * @return the headers
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHeaderString() {
		return headerString;
	}

	public void removeHeader(String key, String value) {
		headers.remove(key);
	}

	/**
	 * @param headers the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
