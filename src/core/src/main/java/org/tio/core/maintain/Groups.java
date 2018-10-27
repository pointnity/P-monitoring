package org.tio.core.maintain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.GroupListener;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.SetWithLock;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class Groups {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(Groups.class);

	/** Which clients are in a group
	 * key: groupid
	 * value: Set<ChannelContext>
	 */
	private MapWithLock<String, SetWithLock<ChannelContext>> groupmap = new MapWithLock<>(
			new ConcurrentHashMap<String, SetWithLock<ChannelContext>>());

	/** What group of groups a client is in
	 *  key: ChannelContext
	 *  value: Set<groupid>
	 */
	private MapWithLock<ChannelContext, SetWithLock<String>> channelmap = new MapWithLock<>(
			new ConcurrentHashMap<ChannelContext, SetWithLock<String>>());

	/**
	 * and group Bindings
	 * @param groupid
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void bind(String groupid, ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		if (StringUtils.isBlank(groupid)) {
			return;
		}

		Lock lock1 = groupmap.getLock().writeLock();
		SetWithLock<ChannelContext> channelContexts = null;
		try {
			lock1.lock();
			Map<String, SetWithLock<ChannelContext>> map = groupmap.getObj();
			channelContexts = map.get(groupid);
			if (channelContexts == null) {
				channelContexts = new SetWithLock<>(new HashSet<ChannelContext>());
				map.put(groupid, channelContexts);
			}
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {
			lock1.unlock();
		}

		if (channelContexts != null) {
			Lock lock11 = channelContexts.getLock().writeLock();
			try {
				lock11.lock();
				channelContexts.getObj().add(channelContext);
			} catch (Throwable e) {
				log.error(e.toString(), e);
			} finally {
				lock11.unlock();
			}
		}

		Lock lock2 = channelmap.getLock().writeLock();
		SetWithLock<String> groups = null;// = channelmap.getObj().get(channelContext);
		try {
			lock2.lock();
			groups = channelmap.getObj().get(channelContext);
			if (groups == null) {
				groups = new SetWithLock<>(new HashSet<String>());
			}
			channelmap.getObj().put(channelContext, groups);
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {
			lock2.unlock();
		}

		if (groups != null) {
			Lock lock22 = groups.getLock().writeLock();
			try {
				lock22.lock();
				groups.getObj().add(groupid);
			} catch (Throwable e) {
				log.error(e.toString(), e);
			} finally {
				lock22.unlock();
			}
		}
	}

	/**
	 * Which clients are in a group
	 * @param groupid
	 * @return
	 * @author tanyaowu
	 */
	public SetWithLock<ChannelContext> clients(GroupContext groupContext, String groupid) {
		if (groupContext.isShortConnection()) {
			return null;
		}

		if (StringUtils.isBlank(groupid)) {
			return null;
		}

		Map<String, SetWithLock<ChannelContext>> map = groupmap.getObj();
		SetWithLock<ChannelContext> set = map.get(groupid);
		return set;
	}

	/**
	 * @return the channelmap
	 */
	public MapWithLock<ChannelContext, SetWithLock<String>> getChannelmap() {
		return channelmap;
	}

	/**
	 * @return the groupmap
	 */
	public MapWithLock<String, SetWithLock<ChannelContext>> getGroupmap() {
		return groupmap;
	}

	/**
	 * Which groups a client is in
	 * @param channelContext
	 * @return
	 * @author tanyaowu
	 */
	public SetWithLock<String> groups(ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return null;
		}

		Map<ChannelContext, SetWithLock<String>> map = channelmap.getObj();
		SetWithLock<String> set = map.get(channelContext);
		return set;
	}

	/**
	 * Unbind from all Groups
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void unbind(ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		Lock lock = channelmap.getLock().writeLock();
		try {
			SetWithLock<String> set = null;
			try {
				lock.lock();
				Map<ChannelContext, SetWithLock<String>> m = channelmap.getObj();
				set = m.get(channelContext);
				m.remove(channelContext);
			} catch (Throwable e) {
				log.error(e.toString(), e);
			} finally {
				lock.unlock();
			}

			if (set != null) {

				GroupListener groupListener = groupContext.getGroupListener();
				Set<String> groups = set.getObj();
				if (groups != null && groups.size() > 0) {
					for (String groupid : groups) {
						unbind(groupid, channelContext);
