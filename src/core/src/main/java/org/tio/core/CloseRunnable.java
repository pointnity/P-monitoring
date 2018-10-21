package org.tio.core;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.ClientChannelContext;
import org.tio.client.ReconnConf;
import org.tio.core.intf.AioListener;
import org.tio.core.maintain.MaintainUtils;
import org.tio.utils.SystemTimer;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class CloseRunnable implements Runnable {

	private static Logger log = LoggerFactory.getLogger(CloseRunnable.class);

	private ChannelContext channelContext;
	private Throwable throwable;
	private String remark;
	private boolean isNeedRemove;

	/**
	 *
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public CloseRunnable(ChannelContext channelContext, Throwable throwable, String remark, boolean isNeedRemove) {
		this.channelContext = channelContext;
		this.throwable = throwable;
		this.remark = remark;
		this.isNeedRemove = isNeedRemove;
	}

	/**
	 * @see java.lang.Runnable#run()
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	public void run() {
		try {
			GroupContext groupContext = channelContext.getGroupContext();
			AioListener aioListener = groupContext.getAioListener();

			try {
				AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
				if (asynchronousSocketChannel != null && asynchronousSocketChannel.isOpen()) {
					try {
						asynchronousSocketChannel.close();
					} catch (Throwable e) {
						log.error(e.toString(), e);
					}
				}
			} catch (Throwable e) {
				log.error(e.toString(), e);
			}

			boolean isClientChannelContext = channelContext instanceof ClientChannelContext;
			//			ReconnConf reconnConf = channelContext.getGroupContext().getReconnConf();
			boolean isRemove = this.isNeedRemove;
			if (!isRemove) {
				if (isClientChannelContext) {
					ClientChannelContext clientChannelContext = (ClientChannelContext) channelContext;

					if (!ReconnConf.isNeedReconn(clientChannelContext, false)) {
						isRemove = true;
					}
				} else {
					isRemove = true;
				}
			}

			try {
				channelContext.getStat().setTimeClosed(SystemTimer.currentTimeMillis());
				aioListener.onBeforeClose(channelContext, throwable, remark, isRemove);
			} catch (Throwable e) {
				log.error(e.toString(), e);
			}

			ReentrantReadWriteLock reentrantReadWriteLock = channelContext.getCloseLock();//.getLock();
			WriteLock writeLock = reentrantReadWriteLock.writeLock();
			boolean isLock = writeLock.tryLock();

			try {
				if (!isLock) {
					if (isRemove) {
						if (channelContext.isRemoved()) {
							return;
						} else {
							writeLock.lock();
						}
					} else {
						return;
					}
				}

				channelContext.traceClient(ChannelAction.UNCONNECT, null, null);

				if (channelContext.isClosed() && !isRemove) {
					log.info("{}, {}Closed, note: {}, exception:{}", channelContext.getGroupContext(), channelContext, remark, throwable == null ? "no" : throwable.toString());
					return;
				}

				if (channelContext.isRemoved()) {
					log.info("{}, {}deleted, note: {}, exception:{}", channelContext.getGroupContext(), channelContext, remark, throwable == null ? "no" : throwable.toString());
					return;
				}

				//You must cancel the task before emptying the queue
				//				channelContext.getDecodeRunnable().setCanceled(true);
				channelContext.getHandlerRunnable().setCanceled(true);
				//		channelContext.getHandlerRunnableHighPrior().setCanceled(true);
				channelContext.getSendRunnable().setCanceled(true);
				//		channelContext.getSendRunnableHighPrior().setCanceled(true);

				channelContext.getDecodeRunnable().clearMsgQueue();
				channelContext.getHandlerRunnable().clearMsgQueue();
				//		channelContext.getHandlerRunnableHighPrior().clearMsgQueue();
				channelContext.getSendRunnable().clearMsgQueue();
				//		channelContext.getSendRunnableHighPrior().clearMsgQueue();

				log.info("{}, {} Preparing to close the connection, isNeedRemove:{}, {}", channelContext.getGroupContext(), channelContext, isRemove, remark);

				try {
//					channelContext.getIpStat().getActivatedCount().decrementAndGet();
					
//					GuavaCache[] caches = channelContext.getGroupContext().ips.getCaches();
//					for (GuavaCache guavaCache : caches) {
//						IpStat ipStat = (IpStat) guavaCache.get(channelContext.getClientNode().getIp());
//						ipStat.getActivatedCount().decrementAndGet();
//					}
					
//					List<Long> durationList = groupContext.ips.list;
//					for (Long v : durationList) {
//						IpStat ipStat = (IpStat) channelContext.getGroupContext().ips.get(v, channelContext.getClientNode().getIp());
//						ipStat.getActivatedCount().decrementAndGet();
//					}
//					String clientIp = channelContext.getClientNode().getIp();
//					AtomicInteger activatedCount = IpStat.getActivatedCount(clientIp, false);
//					if (activatedCount != null) {
//						activatedCount.decrementAndGet();
//						if (activatedCount.get() <= 0) {
//							IpStat.removeActivatedCount(clientIp);
//						}
//					}
					
					if (isRemove) {
						MaintainUtils.remove(channelContext);
					} else {
//						if (!groupContext.isShortConnection()) {
							groupContext.closeds.add(channelContext);
							groupContext.connecteds.remove(channelContext);

							MaintainUtils.close(channelContext);
//						}
					}

					try {
						
						channelContext.setRemoved(isRemove);
						channelContext.getGroupContext().getGroupStat().getClosed().incrementAndGet();
						channelContext.getStat().setTimeClosed(SystemTimer.currentTimeMillis());
					} catch (Throwable e) {
						log.error(e.toString(), e);
					}

					try {
						aioListener.onAfterClose(channelContext, throwable, remark, isRemove);
						channelContext.setClosed(true);
					} catch (Throwable e) {
						log.error(e.toString(), e);
					}					
				} catch (Throwable e) {
					log.error(e.toString(), e);
				} finally {
					if (!isRemove && channelContext.isClosed() && isClientChannelContext) //Does not delete and is not connected, it is added to the reconnection queue
					{
						ClientChannelContext clientChannelContext = (ClientChannelContext) channelContext;
						ReconnConf.put(clientChannelContext);
					}
				}
