package org.tio.core.maintain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.ObjWithLock;
import org.tio.utils.lock.SetWithLock;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class Tokens {
	private static Logger log = LoggerFactory.getLogger(Tokens.class);

	/**
	 * key: token
	 * value: ChannelContext
	 */
	private MapWithLock<String, SetWithLock<ChannelContext>> mapWithLock = new MapWithLock<>(new HashMap<String, SetWithLock<ChannelContext>>());

	/**
	 * Bind tokens.
	 *
	 * @param token the token
	 * @param channelContext the channel context
	 * @author tanyaowu
	 */
	public void bind(String token, ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		if (StringUtils.isBlank(token)) {
			return;
		}
		String key = token;
		Lock lock = mapWithLock.getLock().writeLock();
		Map<String, SetWithLock<ChannelContext>> map = mapWithLock.getObj();

		try {
			lock.lock();

			SetWithLock<ChannelContext> setWithLock = map.get(key);
			if (setWithLock == null) {
				setWithLock = new SetWithLock<>(new HashSet<>());
				map.put(key, setWithLock);
			}
			setWithLock.add(channelContext);

			//			cacheMap.put(key, channelContext);

			channelContext.setToken(token);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Find.
	 *
	 * @param token the token
	 * @return the channel context
	 */
	public SetWithLock<ChannelContext> find(GroupContext groupContext, String token) {
		if (groupContext.isShortConnection()) {
			return null;
		}

		if (StringUtils.isBlank(token)) {
			return null;
		}
		String key = token;
		Lock lock = mapWithLock.getLock().readLock();
		Map<String, SetWithLock<ChannelContext>> m = mapWithLock.getObj();

		try {
			lock.lock();
			return m.get(key);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	/**
