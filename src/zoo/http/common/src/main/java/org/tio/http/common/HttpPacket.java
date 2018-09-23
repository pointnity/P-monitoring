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
