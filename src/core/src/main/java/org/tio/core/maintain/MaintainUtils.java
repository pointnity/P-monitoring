package org.tio.core.maintain;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class MaintainUtils {
	private static Logger log = LoggerFactory.getLogger(MaintainUtils.class);

	/**
	 * Completely removed, no longer maintained
	 * @param channelContext
	 *
	 * @author tanyaowu
	 *
	 */
	public static void remove(ChannelContext channelContext) {
		GroupContext groupContext = channelContext.getGroupContext();

		groupContext.connections.remove(channelContext);
		groupContext.connecteds.remove(channelContext);
		groupContext.closeds.remove(channelContext);
		groupContext.ips.unbind(channelContext);

		//		if (groupContext.isShortConnection()) {
		//			return;
		//		}

		try {
			//ID Unbind
			groupContext.ids.unbind(channelContext);

			close(channelContext);
		} catch (Throwable e1) {
			log.error(e1.toString(), e1);
		}
	}

	public static void close(ChannelContext channelContext) {
		//		GroupContext groupContext = channelContext.getGroupContext();

		//User ID Unbind
		if (StringUtils.isNotBlank(channelContext.getUserid())) {
			try {
				Aio.unbindUser(channelContext);
			} catch (Throwable e) {
				log.error(e.toString(), e);
			}
		}
