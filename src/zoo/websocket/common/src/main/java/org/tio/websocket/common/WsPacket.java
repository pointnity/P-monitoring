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

	/**
	 * @return the wsBodyText
	 */
	public String getWsBodyText() {
		return wsBodyText;
	}

	/**
	 * @return the wsMask
	 */
	public byte[] getWsMask() {
		return wsMask;
	}

	/**
	 * @return the wsOpcode
	 */
	public Opcode getWsOpcode() {
		return wsOpcode;
	}

	/**
	 * @return the isHandShake
	 */
	public boolean isHandShake() {
		return isHandShake;
	}

	/**
	 * @return the wsEof
	 */
	public boolean isWsEof() {
		return wsEof;
	}

	/**
	 * @return the wsHasMask
	 */
	public boolean isWsHasMask() {
		return wsHasMask;
	}

	/**
	 * @see org.tio.core.intf.Packet#logstr()
	 *
	 * @return
	 * @author tanyaowu
	 * 
	 *
	 */
	@Override
	public String logstr() {
		return "websocket";

	}

	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}

	/**
	 * @param isHandShake the isHandShake to set
	 */
	public void setHandShake(boolean isHandShake) {
		this.isHandShake = isHandShake;
	}

	/**
	 * @param wsBodyLength the wsBodyLength to set
	 */
	public void setWsBodyLength(long wsBodyLength) {
		this.wsBodyLength = wsBodyLength;
	}

	/**
	 * @param wsBodyText the wsBodyText to set
	 */
	public void setWsBodyText(String wsBodyText) {
		this.wsBodyText = wsBodyText;
	}

	/**
	 * @param wsEof the wsEof to set
	 */
	public void setWsEof(boolean wsEof) {
		this.wsEof = wsEof;
	}

	/**
	 * @param wsHasMask the wsHasMask to set
	 */
	public void setWsHasMask(boolean wsHasMask) {
		this.wsHasMask = wsHasMask;
	}

	/**
	 * @param wsMask the wsMask to set
	 */
	public void setWsMask(byte[] wsMask) {
		this.wsMask = wsMask;
	}

	/**
	 * @param wsOpcode the wsOpcode to set
	 */
	public void setWsOpcode(Opcode wsOpcode) {
		this.wsOpcode = wsOpcode;
	}

	//	/**
	//	 * @return the headers
	//	 */
	//	public Map<String, String> getHeaders() {
	//		return headers;
	//	}
	//
	//	/**
	//	 * @param headers the headers to set
	//	 */
	//	public void setHeaders(Map<String, String> headers) {
	//		this.headers = headers;
	//	}
}
