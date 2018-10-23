package org.tio.core;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.WriteCompletionHandler.WriteCompletionVo;
import org.tio.core.intf.Packet;
import org.tio.core.intf.PacketWithMeta;
import org.tio.core.stat.ChannelStat;
import org.tio.core.stat.GroupStat;
import org.tio.core.stat.IpStat;
import org.tio.utils.SystemTimer;

/**
*
 * @author tanyaowu
 *
 */
public class WriteCompletionHandler implements CompletionHandler<Integer, WriteCompletionVo> {

	public static class WriteCompletionVo {
		private ByteBuffer byteBuffer = null;

		private Object obj = null;

		/**
		 * @param byteBuffer
		 * @param obj
		 * @author tanyaowu
		 */
		public WriteCompletionVo(ByteBuffer byteBuffer, Object obj) {
			super();
			this.byteBuffer = byteBuffer;
			this.obj = obj;
		}

		/**
		 * @return the byteBuffer
		 */
		public ByteBuffer getByteBuffer() {
			return byteBuffer;
		}

		/**
		 * @return the obj
		 */
		public Object getObj() {
			return obj;
		}

		/**
		 * @param byteBuffer the byteBuffer to set
		 */
		public void setByteBuffer(ByteBuffer byteBuffer) {
			this.byteBuffer = byteBuffer;
		}

		/**
		 * @param obj the obj to set
		 */
		public void setObj(Object obj) {
			this.obj = obj;
		}
	}

	private static Logger log = LoggerFactory.getLogger(WriteCompletionHandler.class);

	private ChannelContext channelContext = null;

	private java.util.concurrent.Semaphore writeSemaphore = new Semaphore(1);

	public WriteCompletionHandler(ChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	@Override
