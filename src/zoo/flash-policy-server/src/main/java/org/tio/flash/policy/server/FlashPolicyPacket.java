package org.tio.flash.policy.server;

import org.tio.core.intf.Packet;

 /**
 *
 * @author tanyaowu
 *
 */
public class FlashPolicyPacket extends Packet {
	private static final long serialVersionUID = -172060606924066412L;
	public static final int MIN_LENGHT = 22;//The minimum length of the message
	public static final int MAX_LING_LENGHT = 256;//Maximum length of a row
	
	public static final FlashPolicyPacket REQUEST = new FlashPolicyPacket();
