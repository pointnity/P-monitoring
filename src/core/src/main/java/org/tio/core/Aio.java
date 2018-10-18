package org.tio.core;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.Packet;
import org.tio.core.intf.PacketWithMeta;
import org.tio.core.maintain.ChannelContextMapWithLock;
import org.tio.core.task.SendRunnable;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.ObjWithLock;
import org.tio.utils.lock.SetWithLock;
import org.tio.utils.page.Page;
import org.tio.utils.page.PageUtils;
import org.tio.utils.thread.ThreadUtils;

/**
 * The Class Aio. T-io users care about the API almost all in this
 *
 * @author tanyaowu
 */
public class Aio {
	private Aio() {}


	/** The log. */
	private static Logger log = LoggerFactory.getLogger(Aio.class);

	/**
	 * Bind groups
	 * @param channelContext
	 * @param group
	 * @author tanyaowu
	 */
	public static void bindGroup(ChannelContext channelContext, String group) {
		channelContext.getGroupContext().groups.bind(group, channelContext);
	}
	
	/**
	 * Whether a channel is in a group
	 * @param channelContext
	 * @param group
	 * @return true：In this group
	 * @author: tanyaowu
	 */
	public static boolean isInGroup(ChannelContext channelContext, String group) {
		MapWithLock<ChannelContext, SetWithLock<String>> mapWithLock = channelContext.getGroupContext().groups.getChannelmap();
		
		ReadLock lock = mapWithLock.getLock().readLock();
		try {
			lock.lock();
			Map<ChannelContext, SetWithLock<String>> m = mapWithLock.getObj();
			if (m == null || m.size() == 0) {
				return false;
			}
			SetWithLock<String> set = m.get(channelContext);
			if (set == null) {
				return false;
			}
			return set.getObj().contains(group);
		} catch (Throwable e) {
			log.error(e.toString(), e);
			return false;
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * How many connections are there in a group
	 * @param group
	 * @return
	 * @author tanyaowu
	 */
	public static int groupCount(GroupContext groupContext, String group) {
		SetWithLock<ChannelContext> setWithLock = groupContext.groups.clients(groupContext, group);
		if (setWithLock == null) {
			return 0;
		}
		
		return setWithLock.getObj().size();
	}

	/**

