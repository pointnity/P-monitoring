package org.tio.core.intf;

import org.tio.core.ChannelContext;

/**
 * @author tanyaowu
 *  
 */
public interface GroupListener {
	/**
	 * Callback This method after binding a group
	 * @param channelContext
	 * @param group
	 * @throws Exception
	 * @author tanyaowu
	 */
	void onAfterBind(ChannelContext channelContext, String group) throws Exception;

	/**
