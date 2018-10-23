package org.tio.core;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.stat.IpStat;
import org.tio.core.task.DecodeRunnable;
import org.tio.core.utils.AioUtils;
import org.tio.utils.SystemTimer;

/**
 *
 * @author tanyaowu
 *  
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
	private static Logger log = LoggerFactory.getLogger(ReadCompletionHandler.class);
	private ChannelContext channelContext = null;
	private ByteBuffer readByteBuffer;

	//	private ByteBuffer byteBuffer = ByteBuffer.allocate(ChannelContext.READ_BUFFER_SIZE);

	/**
	 *
	 * @param channelContext
	 * @author tanyaowu
	 */
	public ReadCompletionHandler(ChannelContext channelContext) {
		this.channelContext = channelContext;
		this.readByteBuffer = ByteBuffer.allocate(channelContext.getGroupContext().getReadBufferSize());
	}

	@Override
	public void completed(Integer result, ByteBuffer byteBuffer) {
		//		GroupContext groupContext = channelContext.getGroupContext();
		if (result > 0) {
			channelContext.getStat().setLatestTimeOfReceivedByte(SystemTimer.currentTimeMillis());
			GroupContext groupContext = channelContext.getGroupContext();
			
			groupContext.getGroupStat().getReceivedBytes().addAndGet(result);
			channelContext.getStat().getReceivedBytes().addAndGet(result);
//			channelContext.getIpStat().getReceivedBytes().addAndGet(result);
			
			
			groupContext.getGroupStat().getReceivedTcps().incrementAndGet();
			channelContext.getStat().getReceivedTcps().incrementAndGet();
//			channelContext.getIpStat().getReceivedTcps().incrementAndGet();
			
			
//			GuavaCache[] caches = groupContext.ips.getCaches();
