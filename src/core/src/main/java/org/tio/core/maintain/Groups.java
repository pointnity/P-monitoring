package org.tio.core.maintain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.GroupListener;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.SetWithLock;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class Groups {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(Groups.class);

	/** Which clients are in a group
	 * key: groupid
	 * value: Set<ChannelContext>
	 */
	private MapWithLock<String, SetWithLock<ChannelContext>> groupmap = new MapWithLock<>(
			new ConcurrentHashMap<String, SetWithLock<ChannelContext>>());

	/** What group of groups a client is in
	 *  key: ChannelContext
	 *  value: Set<groupid>
	 */
