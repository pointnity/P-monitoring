package org.tio.client;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.CountDownLatch;

import org.tio.core.Node;

/**
 *
 * @author tanyaowu
 *  
 */
public class ConnectionCompletionVo {

	private ClientChannelContext channelContext = null;

	private AioClient aioClient = null;

	private boolean isReconnect = false;

	private AsynchronousSocketChannel asynchronousSocketChannel;

	private Node serverNode;

	private String bindIp;

	private Integer bindPort;

	private CountDownLatch countDownLatch = null;

	/**
	 * @author tanyaowu
	 *
	 */
	public ConnectionCompletionVo() {

	}

	/**
	 * @param channelContext
	 * @param aioClient
	 * @param isReconnect
	 * @param asynchronousSocketChannel
	 * @param serverNode
	 * @param bindIp
	 * @param bindPort
	 *
	 * @author tanyaowu
	 *
	 */
	public ConnectionCompletionVo(ClientChannelContext channelContext, AioClient aioClient, boolean isReconnect, AsynchronousSocketChannel asynchronousSocketChannel,
			Node serverNode, String bindIp, Integer bindPort) {
		super();
		this.channelContext = channelContext;
		this.aioClient = aioClient;
		this.isReconnect = isReconnect;
		this.asynchronousSocketChannel = asynchronousSocketChannel;
		this.serverNode = serverNode;
		this.bindIp = bindIp;
		this.bindPort = bindPort;
	}

