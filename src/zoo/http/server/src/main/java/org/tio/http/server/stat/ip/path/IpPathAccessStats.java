package org.tio.http.server.stat.ip.path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.GroupContext;
import org.tio.utils.cache.guava.GuavaCache;

/**
 * 
 * @author tanyaowu
 *  
 */
public class IpPathAccessStats {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(IpPathAccessStats.class);

	private final static String CACHE_NAME = "TIO_IP_ACCESSPATH";
	//	private final static Long timeToLiveSeconds = null;
	//	private final static Long timeToIdleSeconds = Time.DAY_1;

	private GroupContext groupContext;
	
	private String groupContextId;

	//	private GuavaCache[] caches = null;
	/**
	 * key:  Time period, unit: seconds
	 * value: GuavaCache: key: ip, value: IpAccessStat
	 */
	public final Map<Long, GuavaCache> cacheMap = new HashMap<>();

	/**
	 * List of time and long segments
	 */
	public final List<Long> durationList = new ArrayList<>();
	
	private final Map<Long, IpPathAccessStatListener> listenerMap = new HashMap<>();

	/**
	 * 
	 * @param groupContext
	 * @param ipPathAccessStatListener
	 * @param durations
	 * @author tanyaowu
	 */
	public IpPathAccessStats(GroupContext groupContext, IpPathAccessStatListener ipPathAccessStatListener, Long[] durations) {
		this.groupContext = groupContext;
		this.groupContextId = groupContext.getId();
		if (durations != null) {
			for (Long duration : durations) {
				addDuration(duration, ipPathAccessStatListener);
			}
		}
	}

	/**
	 * Add Monitoring window
	 * @param duration Units: Seconds
	 * @param ipPathAccessStatListener can be null
	 * @author: tanyaowu
	 */
	public void addDuration(Long duration, IpPathAccessStatListener ipPathAccessStatListener) {
		@SuppressWarnings("unchecked")
		GuavaCache guavaCache = GuavaCache.register(getCacheName(duration), duration, null, new IpPathAccessStatRemovalListener(groupContext, ipPathAccessStatListener));
		cacheMap.put(duration, guavaCache);
		durationList.add(duration);
		
		if (ipPathAccessStatListener != null) {
			listenerMap.put(duration, ipPathAccessStatListener);
		}
	}
	
	/**
	 * 
	 * @param duration
	 * @return
	 * @author tanyaowu
	 */
	public IpPathAccessStatListener getListener(Long duration) {
		return listenerMap.get(duration);
	}

	/**
	 * Add Monitoring window
	 * @param durations Units: Seconds
	 * @param ipPathAccessStatListener can be null
	 * @author: tanyaowu
	 */
	public void addDurations(Long[] durations, IpPathAccessStatListener ipPathAccessStatListener) {
		if (durations != null) {
			for (Long duration : durations) {
				addDuration(duration, ipPathAccessStatListener);
			}
		}
	}

	/**
	 * Delete Monitoring time period
	 * @param duration
	 * @author: tanyaowu
	 */
	public void removeMonitor(Long duration) {
		clear(duration);
		cacheMap.remove(duration);
		durationList.remove(duration);
	}

	/**
	 * 
	 * @param duration
	 * @return
	 * @author: tanyaowu
	 */
	public String getCacheName(Long duration) {
		String cacheName = CACHE_NAME + "_" + this.groupContextId + "_";
		return cacheName + duration;
	}

	/**
	 * Emptying monitoring data
	 * @author: tanyaowu
	 */
	public void clear(Long duration) {
		GuavaCache guavaCache = cacheMap.get(duration);
		if (guavaCache == null) {
			return;
		}
		guavaCache.clear();
	}

	
	
	/**
	 * Get Ipaccessstat
	 * @param duration
	 * @param ip
	 * @param forceCreate
	 * @return
	 * @author tanyaowu
	 */
	public IpAccessStat get(Long duration, String ip, boolean forceCreate) {
		if (StringUtils.isBlank(ip)) {
			return null;
		}
		
		GuavaCache guavaCache = cacheMap.get(duration);
		if (guavaCache == null) {
