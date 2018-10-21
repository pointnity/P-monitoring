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
