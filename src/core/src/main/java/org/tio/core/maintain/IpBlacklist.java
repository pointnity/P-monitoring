package org.tio.core.maintain;

import java.util.Collection;

import org.tio.core.Aio;
import org.tio.core.GroupContext;
import org.tio.utils.SystemTimer;
import org.tio.utils.cache.guava.GuavaCache;
import org.tio.utils.time.Time;

/**
 *
 * @author tanyaowu
 *  
 */
public class IpBlacklist {

	/** remoteAndChannelContext key: "ip:port" value: ChannelContext. */
//	private SetWithLock<String> setWithLock = new SetWithLock<>(new HashSet<String>());

	
	private String id;

	private final static String CACHE_NAME = "TIO_IP_BLACK_LIST";
	private final static Long TIME_TO_LIVE_SECONDS = Time.DAY_1;
	private final static Long TIME_TO_IDLE_SECONDS = null;

	private String cacheName = null;
	private GuavaCache cache = null;
	
	private GroupContext groupContext;

	public IpBlacklist(String id, GroupContext groupContext) {
		this.id = id;
		this.groupContext = groupContext;
		this.cacheName = CACHE_NAME + this.id;
		this.cache = GuavaCache.register(this.cacheName, TIME_TO_LIVE_SECONDS, TIME_TO_IDLE_SECONDS, null);
	}

	
	public boolean add(String ip) {
		//Add to Blacklist list first
//		Lock lock = setWithLock.getLock().writeLock();
//		try {
//			lock.lock();
//			Set<String> m = setWithLock.getObj();
//			m.add(ip);
//		} catch (Throwable e) {
//			throw e;
//		} finally {
//			lock.unlock();
//		}
		
		if (isInBlacklist(ip)) {
			return false;
		}
		cache.put(ip, SystemTimer.currentTimeMillis());

		//Then delete the associated connection
		Aio.remove(groupContext, ip, "ip[" + ip + "]Was added to the blacklist");
		return true;
	}

	public void clear() {
//		Lock lock = setWithLock.getLock().writeLock();
//		try {
//			lock.lock();
//			Set<String> m = setWithLock.getObj();
//			m.clear();
//		} catch (Throwable e) {
//			throw e;
