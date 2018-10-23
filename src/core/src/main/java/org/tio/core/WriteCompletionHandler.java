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
	public void completed(Integer result, WriteCompletionVo writeCompletionVo) {
		//		Object attachment = writeCompletionVo.getObj();
		ByteBuffer byteBuffer = writeCompletionVo.getByteBuffer();
		if (byteBuffer.hasRemaining()) {
			//			int iv = byteBuffer.capacity() - byteBuffer.position();
			log.info("{} {}/{} has sent", channelContext, byteBuffer.position(), byteBuffer.capacity());
			AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
			asynchronousSocketChannel.write(byteBuffer, writeCompletionVo, this);
			channelContext.getStat().setLatestTimeOfSentByte(SystemTimer.currentTimeMillis());
		} else {
			channelContext.getStat().setLatestTimeOfSentPacket(SystemTimer.currentTimeMillis());
			handle(result, null, writeCompletionVo);
		}

	}

	@Override
	public void failed(Throwable throwable, WriteCompletionVo writeCompletionVo) {
		//		Object attachment = writeCompletionVo.getObj();
		handle(0, throwable, writeCompletionVo);
	}

	/**
	 * @return the writeSemaphore
	 */
	public java.util.concurrent.Semaphore getWriteSemaphore() {
		return writeSemaphore;
	}

	/**
	 *
	 * @param result
	 * @param throwable
	 * @param attachment Packet or PacketWithMeta or List<PacketWithMeta> or List<Packet>
	 * @author tanyaowu
	 */
	public void handle(Integer result, Throwable throwable, WriteCompletionVo writeCompletionVo) {
		this.writeSemaphore.release();
		Object attachment = writeCompletionVo.getObj();

		GroupContext groupContext = channelContext.getGroupContext();
		GroupStat groupStat = groupContext.getGroupStat();
		ChannelStat channelStat = channelContext.getStat();
		//		AioListener aioListener = groupContext.getAioListener();
		boolean isSentSuccess = result > 0;

		
//		GuavaCache[] caches = channelContext.getGroupContext().ips.getCaches();
		
		List<Long> list = groupContext.ipStats.durationList;
		
		
		
		if (isSentSuccess) {
			groupStat.getSentBytes().addAndGet(result);
			channelStat.getSentBytes().addAndGet(result);
//			channelContext.getIpStat().getSentBytes().addAndGet(result);
			
//			for (GuavaCache guavaCache : caches) {
//				IpStat ipStat = (IpStat) guavaCache.get(channelContext.getClientNode().getIp());
//				ipStat.getSentBytes().addAndGet(result);
//			}
			for (Long v : list) {
				IpStat ipStat = (IpStat) channelContext.getGroupContext().ipStats.get(v, channelContext.getClientNode().getIp());
				ipStat.getSentBytes().addAndGet(result);
			}
			
		}

		int packetCount = 0;
		try {
			boolean isPacket = attachment instanceof Packet;
			boolean isPacketWithMeta = !isPacket && attachment instanceof PacketWithMeta;

			if (isPacket || isPacketWithMeta) {
				if (isSentSuccess) {
					groupStat.getSentPacket().incrementAndGet();
					channelStat.getSentPackets().incrementAndGet();
//					channelContext.getIpStat().getSentPackets().incrementAndGet();

//					for (GuavaCache guavaCache : caches) {
//						IpStat ipStat = (IpStat) guavaCache.get(channelContext.getClientNode().getIp());
//						ipStat.getSentPackets().incrementAndGet();
//					}
					
					for (Long v : list) {
						IpStat ipStat = (IpStat) channelContext.getGroupContext().ipStats.get(v, channelContext.getClientNode().getIp());
						ipStat.getSentPackets().incrementAndGet();
					}
				}
				handleOne(result, throwable, attachment, isSentSuccess);
			} else {
				List<?> ps = (List<?>) attachment;
				if (isSentSuccess) {
					packetCount = ps.size();
					groupStat.getSentPacket().addAndGet(packetCount);
					channelStat.getSentPackets().addAndGet(packetCount);
//					channelContext.getIpStat().getSentPackets().addAndGet(packetCount);
					
//					for (GuavaCache guavaCache : caches) {
//						IpStat ipStat = (IpStat) guavaCache.get(channelContext.getClientNode().getIp());
//						ipStat.getSentPackets().addAndGet(packetCount);
//					}
					
					for (Long v : list) {
						IpStat ipStat = (IpStat) channelContext.getGroupContext().ipStats.get(v, channelContext.getClientNode().getIp());
						ipStat.getSentPackets().addAndGet(packetCount);
					}
				}

				for (Object obj : ps) {
					handleOne(result, throwable, obj, isSentSuccess);
				}
			}

			if (!isSentSuccess) {
				Aio.close(channelContext, throwable, "Write Data back:" + result);
			}
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {

		}
	}

	/**
	 *
	 * @param result
	 * @param throwable
	 * @param obj PacketWithMeta or Packet
	 * @param isSentSuccess
	 * @author tanyaowu
	 */
	public void handleOne(Integer result, Throwable throwable, Object obj, Boolean isSentSuccess) {
		Packet packet = null;
