package org.tio.websocket.common;

import java.util.List;

import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;

/**
 *
 * @author tanyaowu
 *
 */
public class WsSessionContext {
	/**
	 * @param args
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public static void main(String[] args) {

	}

	/**
	 * Have you ever held hands
	 */
	private boolean isHandshaked = false;

	/**
	 * websocket Handshake Request Package
	 */
	private HttpRequest handshakeRequestPacket = null;

	/**
	 * websocket Handshake Response Package
	 */
