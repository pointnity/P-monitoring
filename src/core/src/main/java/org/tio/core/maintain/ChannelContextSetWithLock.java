package org.tio.core.maintain;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.lock.SetWithLock;

/**
 *
 * @author tanyaowu
 *  
 */
public class ChannelContextSetWithLock {

	/** remoteAndChannelContext key: "ip:port" value: ChannelContext. */
	private SetWithLock<ChannelContext> setWithLock = new SetWithLock<>(new HashSet<ChannelContext>());

	public void add(ChannelContext channelContext) {
		@SuppressWarnings("unused")
		GroupContext groupContext = channelContext.getGroupContext();
//		if (groupContext.isShortConnection()) {
//			return;
//		}

		Lock lock = setWithLock.getLock().writeLock();

		try {
			lock.lock();
			Set<ChannelContext> m = setWithLock.getObj();
			m.add(channelContext);
		} catch (Throwable e) {
			throw e;
