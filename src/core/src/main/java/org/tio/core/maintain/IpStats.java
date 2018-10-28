package org.tio.core.maintain;

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
import org.tio.core.cache.IpStatRemovalListener;
import org.tio.core.intf.IpStatListener;
import org.tio.core.stat.IpStat;
import org.tio.utils.cache.guava.GuavaCache;

/**
 *
 * @author tanyaowu
 * 
 */
public class IpStats {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(IpStats.class);

	private final static String CACHE_NAME = "TIO_IP_STAT";
	//	private final static Long timeToLiveSeconds = null;
	//	private final static Long timeToIdleSeconds = Time.DAY_1;

	private String groupContextId;
	private GroupContext groupContext;

	//	private GuavaCache[] caches = null;
	/**
	 * Key: Duration, Unit: seconds
	 */
	public final Map<Long, GuavaCache> cacheMap = new HashMap<>();

	public final List<Long> durationList = new ArrayList<>();

	public IpStats(GroupContext groupContext, IpStatListener ipStatListener, Long[] durations) {
		this.groupContext = groupContext;
		this.groupContextId = groupContext.getId();
		if (durations != null) {
			for (Long duration : durations) {
				addDuration(duration, ipStatListener);
			}
		}
	}

	/**
	 * Add Monitoring window
	 * @param duration Units: Seconds
	 * @param ipStatListener can be null
	 * @author: tanyaowu
	 */
	public void addDuration(Long duration, IpStatListener ipStatListener) {
		@SuppressWarnings("unchecked")
		GuavaCache guavaCache = GuavaCache.register(getCacheName(duration), duration, null, new IpStatRemovalListener(groupContext, ipStatListener));
		cacheMap.put(duration, guavaCache);
		durationList.add(duration);
	}

	/**
	 * Add Monitoring window
	 * @param durations Units: Seconds
