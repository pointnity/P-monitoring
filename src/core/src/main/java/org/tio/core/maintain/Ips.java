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
		Lock lock11 = channelContexts.getLock().writeLock();
		try {
			lock11.lock();
			channelContexts.getObj().add(channelContext);
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {
			lock11.unlock();
		}
		//		}

	}

	/**
	 * What clients are available for an IP, it is possible to return null
	 * @param ip
	 * @return
	 * @author tanyaowu
	 */
	public SetWithLock<ChannelContext> clients(GroupContext groupContext, String ip) {
//		if (groupContext.isShortConnection()) {
//			return null;
//		}

		if (StringUtils.isBlank(ip)) {
			return null;
		}

		Map<String, SetWithLock<ChannelContext>> map = ipmap.getObj();
		SetWithLock<ChannelContext> set = map.get(ip);
		return set;
	}

	/**
	 * @return the ipmap
	 */
	public MapWithLock<String, SetWithLock<ChannelContext>> getIpmap() {
		return ipmap;
	}

	/**
	 * Unbind from the specified IP
	 * @param ip
	 * @param channelContext
	 * @author tanyaowu
	 */
	public void unbind(ChannelContext channelContext) {
		if (channelContext == null) {
			return;
		}
		
		String ip = channelContext.getClientNode().getIp();
		if (ChannelContext.UNKNOWN_ADDRESS_IP.equals(ip)) {
			log.error("{} ip is not right", channelContext);
			return;
		}
		
		GroupContext groupContext = channelContext.getGroupContext();
//		if (groupContext.isShortConnection()) {
//			return;
//		}

		if (StringUtils.isBlank(ip)) {
			return;
		}

		SetWithLock<ChannelContext> channelContexts = ipmap.getObj().get(ip);
		if (channelContexts != null) {
			Lock lock1 = channelContexts.getLock().writeLock();
			try {
				lock1.lock();
				channelContexts.getObj().remove(channelContext);
				
				if (channelContexts.getObj().size() == 0) {
					Lock lock2 = ipmap.getLock().writeLock();
					try {
						lock2.lock();
						ipmap.getObj().remove(ip);
					} catch (Throwable e) {
						log.error(e.toString(), e);
					} finally {
						lock2.unlock();
					}
				}
				
			} catch (Throwable e) {
				log.error(e.toString(), e);
			} finally {
				lock1.unlock();
			}
		} else {
			log.error("{}, ip【{}】 Could not find the corresponding SetWithLock", groupContext.getName(), ip);
		}
	}
}
