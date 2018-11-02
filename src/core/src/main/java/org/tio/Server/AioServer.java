package org.tio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Node;

/**
 * @author tanyaowu
*
 */
public class AioServer {
	private static Logger log = LoggerFactory.getLogger(AioServer.class);

	private ServerGroupContext serverGroupContext;

	private AsynchronousServerSocketChannel serverSocketChannel;

	private Node serverNode;

	private boolean isWaitingStop = false;

	/**
	 *
	 * @param serverGroupContext
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public AioServer(ServerGroupContext serverGroupContext) {
		super();
		this.serverGroupContext = serverGroupContext;
	}

	/**
	 * @return the serverGroupContext
	 */
	public ServerGroupContext getServerGroupContext() {
		return serverGroupContext;
	}

	/**
	 * @return the serverNode
	 */
	public Node getServerNode() {
		return serverNode;
	}

	/**
	 * @return the serverSocketChannel
	 */
	public AsynchronousServerSocketChannel getServerSocketChannel() {
		return serverSocketChannel;
	}

	/**
	 * @return the isWaitingStop
	 */
	public boolean isWaitingStop() {
		return isWaitingStop;
	}

	/**
