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
