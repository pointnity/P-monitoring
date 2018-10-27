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
