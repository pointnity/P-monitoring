package org.tio.core.maintain;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.SetWithLock;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class Ips {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(Ips.class);

	/** Which clients are in one IP
	 * key: ip
	 * value: Set<ChannelContext>
	 */
	private MapWithLock<String, SetWithLock<ChannelContext>> ipmap = new MapWithLock<>(new ConcurrentHashMap<String, SetWithLock<ChannelContext>>());

	/**
	 * and IP bindings
	 * @param ip
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void bind(ChannelContext channelContext) {
		if (channelContext == null) {
			return;
		}
		
		String ip = channelContext.getClientNode().getIp();
		if (ChannelContext.UNKNOWN_ADDRESS_IP.equals(ip)) {
			return;
		}
		
//		GroupContext groupContext = channelContext.getGroupContext();
//		if (groupContext.isShortConnection()) {
//			return;
//		}

		if (StringUtils.isBlank(ip)) {
			return;
		}

		Lock lock1 = ipmap.getLock().writeLock();
		SetWithLock<ChannelContext> channelContexts = null;//ipmap.getObj().get(ip);
		try {
			lock1.lock();
			Map<String, SetWithLock<ChannelContext>> map = ipmap.getObj();
			channelContexts = map.get(ip);
			if (channelContexts == null) {
				channelContexts = new SetWithLock<>(new HashSet<ChannelContext>());
				map.put(ip, channelContexts);
			}
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {
			lock1.unlock();
		}

		//		if (channelContexts != null) {
