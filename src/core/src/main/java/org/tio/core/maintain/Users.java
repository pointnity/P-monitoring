package org.tio.core.maintain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.ObjWithLock;
import org.tio.utils.lock.SetWithLock;

/**
 * 
 * @author tanyaowu 
 * 
 */
public class Users {
	private static Logger log = LoggerFactory.getLogger(Users.class);

	/**
	 * key: userid
	 * value: ChannelContext
	 */
	private MapWithLock<String, SetWithLock<ChannelContext>> mapWithLock = new MapWithLock<>(new HashMap<String, SetWithLock<ChannelContext>>());

	/**
	 * Binds the UserID.
	 *
	 * @param userid the userid
	 * @param channelContext the channel context
	 * @author tanyaowu
	 */
	public void bind(String userid, ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();
		if (groupContext.isShortConnection()) {
			return;
		}

		if (StringUtils.isBlank(userid)) {
			return;
		}
		String key = userid;
