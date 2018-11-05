package org.tio.server.intf;

import org.tio.core.intf.AioListener;

/**
 *
 * @author tanyaowu
 *
 */
public interface ServerAioListener extends AioListener {

	/**
	 * Method of triggering after establishing connection
	 * @param asynchronousSocketChannel
	 * @param aioServer
	 * @return false: means rejecting the connection, true: means accepting the connection
	 *
