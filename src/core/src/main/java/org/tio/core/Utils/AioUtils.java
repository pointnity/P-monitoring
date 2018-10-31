package org.tio.core.utils;

import java.nio.channels.AsynchronousSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.thread.ThreadUtils;

/**
 * 
 * @author tanyaowu 
 * 
 */
public class AioUtils {
	private static Logger log = LoggerFactory.getLogger(AioUtils.class);

	public static boolean checkBeforeIO(ChannelContext channelContext) {
		boolean isClosed = channelContext.isClosed();
		boolean isRemoved = channelContext.isRemoved();
		boolean isWaitingClose = channelContext.isWaitingClose();

		if (isWaitingClose) {
