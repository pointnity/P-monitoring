package org.tio.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.ReadCompletionHandler;
import org.tio.utils.SystemTimer;

/**
 *
 * @author tanyaowu
 *  
 */
public class ConnectionCompletionHandler implements CompletionHandler<Void, ConnectionCompletionVo> {
	private static Logger log = LoggerFactory.getLogger(ConnectionCompletionHandler.class);

	/**
