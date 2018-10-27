package org.tio.core.maintain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.lock.MapWithLock;

/**
 *
 * @author tanyaowu
 * 
 */
public class Ids {

	/**
	 * key: id
	 * value: ChannelContext
	 */
	private MapWithLock<String, ChannelContext> map = new MapWithLock<>(new HashMap<String, ChannelContext>());

	/**
	 *
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void bind(ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		String key = channelContext.getId();
		if (StringUtils.isBlank(key)) {
			return;
		}
		Lock lock = map.getLock().writeLock();
		Map<String, ChannelContext> m = map.getObj();

		try {
			lock.lock();
			m.put(key, channelContext);
			//			channelContext.setId(id);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the channel context
	 */
	public ChannelContext find(GroupContext groupContext, String id) {
		if (groupContext.isShortConnection()) {
			return null;
		}

		if (StringUtils.isBlank(id)) {
			return null;
		}
		String key = id;
		Lock lock = map.getLock().readLock();
		Map<String, ChannelContext> m = map.getObj();

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
	 * @return the cacheMap
	 */
	public MapWithLock<String, ChannelContext> getMap() {
		return map;
	}

	/**
	 *
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void unbind(ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		String key = channelContext.getId();
		if (StringUtils.isBlank(key)) {
			return;
		}
		Lock lock = map.getLock().writeLock();
		Map<String, ChannelContext> m = map.getObj();
		try {
			lock.lock();
			m.remove(key);
		} catch (Throwable e) {
			throw e;
		} finally {
			lock.unlock();
		}
	}
}
