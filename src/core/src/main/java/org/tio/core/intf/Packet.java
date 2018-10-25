package org.tio.core.intf;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicLong;

/**
*
* @author tanyaowu
* 
*/
public class Packet implements java.io.Serializable {

	private static final long serialVersionUID = 5275372187150637318L;

	private static final AtomicLong ID_ATOMICLONG = new AtomicLong();

	private Long id = ID_ATOMICLONG.incrementAndGet();

	private int byteCount = 0;

	private Long respId = null;

	private PacketListener packetListener;

	private boolean isBlockSend = false;
	
	/**
	 * Whether the message was another machine was turned over by topic, and if it is, do not go back to the loop again.
	 * This attribute is used internally by TIO, business layer users are requested to use
	 */
	private boolean isFromCluster = false;

	/**
	 * Synchronous serial number required when sending synchronously
	 */
	private Integer synSeq = 0;

	/**
	 * Pre-encoded Bytebuffer, if this value is not NULL, the framework ignores the original encode () and uses this value directly
	 */
	private ByteBuffer preEncodedByteBuffer = null;

	/**
	 * @return the byteCount
	 */
	public int getByteCount() {
		return byteCount;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the packetListener
	 */
	public PacketListener getPacketListener() {
		return packetListener;
	}

	/**
	 * @return the preEncodedByteBuffer
	 */
	public ByteBuffer getPreEncodedByteBuffer() {
		return preEncodedByteBuffer;
	}

	/**
	 * @return the respId
	 */
	public Long getRespId() {
		return respId;
	}

	/**
	 * @return the synSeq
	 */
	public Integer getSynSeq() {
		return synSeq;
	}

	/**
	 * @return the isBlockSend
	 */
	public boolean isBlockSend() {
		return isBlockSend;
	}

	public String logstr() {
		return "";
	}

	/**
	 * @param isBlockSend the isBlockSend to set
	 */
	public void setBlockSend(boolean isBlockSend) {
		this.isBlockSend = isBlockSend;
	}

	/**
	 * @param byteCount the byteCount to set
	 */
	public void setByteCount(int byteCount) {
		this.byteCount = byteCount;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param packetListener the packetListener to set
	 */
	public void setPacketListener(PacketListener packetListener) {
		this.packetListener = packetListener;
	}

	/**
	 * @param preEncodedByteBuffer the preEncodedByteBuffer to set
	 */
	public void setPreEncodedByteBuffer(ByteBuffer preEncodedByteBuffer) {
		this.preEncodedByteBuffer = preEncodedByteBuffer;
	}

	/**
	 * @param respId the respId to set
	 */
	public void setRespId(Long respId) {
		this.respId = respId;
	}

	/**
	 * @param synSeq the synSeq to set
	 */
	public void setSynSeq(Integer synSeq) {
		this.synSeq = synSeq;
	}

	public boolean isFromCluster() {
		return isFromCluster;
	}

	public void setFromCluster(boolean isFromCluster) {
		this.isFromCluster = isFromCluster;
	}

}
