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

        public static final int MAX_BODY_LENGTH = 1024 * 512; //Up to 1024 * 512 (half m) data received

        public static final String CHARSET_NAME = "utf-8";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	/**
	 * Whether it is a handshake package
	 */
	private boolean isHandShake = false;

	private byte[] body;

	private boolean wsEof;

	private Opcode wsOpcode = Opcode.BINARY;

	private boolean wsHasMask;

	private long wsBodyLength;

	private byte[] wsMask;

	private String wsBodyText; //This field is only available when text is

	public WsPacket() {

	}

	public WsPacket(byte[] body) {
		this();
		this.body = body;
	}

	/**
	 * @return the body
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * @return the wsBodyLength
	 */
	public long getWsBodyLength() {
		return wsBodyLength;
	}
