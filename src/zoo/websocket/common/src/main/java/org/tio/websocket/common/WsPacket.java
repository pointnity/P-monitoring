package org.tio.websocket.common;

import org.tio.core.intf.Packet;

/**
 *
 * @author tanyaowu
 * 
 */
public class WsPacket extends Packet {

	//	private static Logger log = LoggerFactory.getLogger(WsPacket.class);
	//
	//	//Head with no cookies included
	//	protected Map<String, String> headers = null;

	private static final long serialVersionUID = 4506947563506841436L;
	/**
	 * The maximum number of message bodies
	 */
	public static final int MAX_LENGTH_OF_BODY = (int) (1024 * 1024 * 2.1); //How many m data is supported only
	public static final int MINIMUM_HEADER_LENGTH = 2;

