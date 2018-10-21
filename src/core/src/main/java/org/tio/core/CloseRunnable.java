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
					log.info("{}, {}Closed, note: {}, exception:{}", channelContext.getGroupContext(), channelContext, remark, throwable == null ? "无" : throwable.toString());
					return;
				}
