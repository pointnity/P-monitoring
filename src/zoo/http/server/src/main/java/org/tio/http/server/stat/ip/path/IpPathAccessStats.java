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
