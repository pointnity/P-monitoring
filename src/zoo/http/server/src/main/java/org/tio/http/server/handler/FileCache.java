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
