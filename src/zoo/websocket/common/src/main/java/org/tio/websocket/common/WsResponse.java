package org.tio.websocket.common;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author tanyaowu
 * 
 */
public class WsResponse extends WsPacket {
	private static Logger log = LoggerFactory.getLogger(WsResponse.class);

	private static final long serialVersionUID = 963847148301021559L;
	
	public static WsResponse fromText(String text, String charset) {
