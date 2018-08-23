package org.tio.websocket.server;

import org.tio.http.common.HttpConst;

/**
 * @author tanyaowu
 * 
 */
public class WsServerConfig {

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	private String bindIp = null;//"127.0.0.1";

	private Integer bindPort = 9322;

	//	private File rootFile = null;

	private String charset = HttpConst.CHARSET_NAME;

	/**
	 *
	 * @author tanyaowu
	 */
	public WsServerConfig(Integer bindPort) {
