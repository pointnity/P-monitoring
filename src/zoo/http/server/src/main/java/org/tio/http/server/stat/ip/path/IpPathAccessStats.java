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
