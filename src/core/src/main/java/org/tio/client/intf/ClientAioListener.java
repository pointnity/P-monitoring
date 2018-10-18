package org.tio.client.intf;

import org.tio.core.intf.AioListener;

/**
 *
 * @author tanyaowu
 *  
 */
public interface ClientAioListener extends AioListener {

	/**
	 * This method is triggered after a re-connection
	 * @param channelContext
	 * @param isConnected True: Indicates that the connection succeeded, false: failed to connect again
	 * @return
	 *
	 * @author tanyaowu
	 *
	 */
	//	void onAfterReconnected(ChannelContext channelContext, boolean isConnected) throws Exception;

	//	/**
	//	 * Methods triggered after a connection failure
	//	 * @param channelContext
	//	 * @param isReconnect Whether it is a re-connect
	//	 * @param throwable There could be a null
