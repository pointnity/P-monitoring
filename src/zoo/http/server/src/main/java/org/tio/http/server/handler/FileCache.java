package org.tio.http.server.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanyaowu
 *  
 */
public class FileCache implements java.io.Serializable {

	private static final long serialVersionUID = 6517890350387789902L;

	private static Logger log = LoggerFactory.getLogger(FileCache.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	//this.addHeader(HttpConst.ResponseHeaderKey.Content_Encoding, "gzip");
	private Map<String, String> headers = null;
	private long lastModified;

	private byte[] data;

	/**
	 *
	 * @author tanyaowu
	 */
	public FileCache() {
	}

	public FileCache(Map<String, String> headers, long lastModified, byte[] data) {
		super();
		this.setHeaders(headers);
		this.lastModified = lastModified;
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

}
