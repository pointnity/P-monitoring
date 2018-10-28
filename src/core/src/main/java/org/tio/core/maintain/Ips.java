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
