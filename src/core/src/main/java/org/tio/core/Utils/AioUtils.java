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
			return false;
		}

		AsynchronousSocketChannel asynchronousSocketChannel = channelContext.getAsynchronousSocketChannel();
		Boolean isopen = null;
		if (asynchronousSocketChannel != null) {
			isopen = asynchronousSocketChannel.isOpen();

			if (isClosed || isRemoved) {
				if (isopen) {
					try {
						Aio.close(channelContext, "asynchronousSocketChannel is open, but channelContext isClosed: " + isClosed + ", isRemoved: " + isRemoved);
					} catch (Throwable e) {
						log.error(e.toString(), e);
					}
				}
				log.info("{}, isopen:{}, isClosed:{}, isRemoved:{}", channelContext, isopen, channelContext.isClosed(), channelContext.isRemoved());
				return false;
			}
		}  else  {
			Log . error ( "{}, check this exception, asynchronousSocketChannel is null, isClosed:{}, isRemoved:{}, {} " ,  channelContext ,  channelContext . isClosed (),  channelContext . isRemoved (),
					ThreadUtils . stackTrace ());
			Return  false ;
