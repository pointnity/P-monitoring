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
