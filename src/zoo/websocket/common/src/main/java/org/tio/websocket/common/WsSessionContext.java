package org.tio.websocket.common;

import java.util.List;

import org.tio.common.HttpRequest;
import org.tio.common.HttpResponse;

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
	private HttpResponse handshakeResponsePacket = null;

	private String token = null;

	//websocket protocol used, and sometimes the packet is divided into several, pay attention to that fin field, this IM temporarily does not support
	private List<byte[]> lastParts = null;

	/**
	 *
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public WsSessionContext() {

	}

	/**
	 * @return the httpHandshakePacket
	 */
	public HttpRequest getHandshakeRequestPacket() {
		return handshakeRequestPacket;
	}

	/**
	 * @return the handshakeResponsePacket
	 */
	public HttpResponse getHandshakeResponsePacket() {
		return handshakeResponsePacket;
	}
