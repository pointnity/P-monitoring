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

	/**
	 * @return the aioClient
	 */
	public AioClient getAioClient() {
		return aioClient;
	}

	/**
	 * @return the asynchronousSocketChannel
	 */
	public AsynchronousSocketChannel getAsynchronousSocketChannel() {
		return asynchronousSocketChannel;
	}

	/**
	 * @return the bindIp
	 */
	public String getBindIp() {
		return bindIp;
	}

	/**
	 * @return the bindPort
	 */
	public Integer getBindPort() {
		return bindPort;
	}

	/**
	 * @return the channelContext
	 */
	public ClientChannelContext getChannelContext() {
		return channelContext;
	}

	/**
	 * @return the countDownLatch
	 */
	public java.util.concurrent.CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}

	/**
	 * @return the serverNode
	 */
	public Node getServerNode() {
		return serverNode;
	}

	/**
	 * @return the isReconnect
	 */
	public boolean isReconnect() {
		return isReconnect;
	}

	/**
	 * @param aioClient the aioClient to set
	 */
	public void setAioClient(AioClient aioClient) {
		this.aioClient = aioClient;
	}

	/**
	 * @param asynchronousSocketChannel the asynchronousSocketChannel to set
	 */
	public void setAsynchronousSocketChannel(AsynchronousSocketChannel asynchronousSocketChannel) {
		this.asynchronousSocketChannel = asynchronousSocketChannel;
	}

	/**
	 * @param bindIp the bindIp to set
	 */
	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	/**
	 * @param bindPort the bindPort to set
	 */
	public void setBindPort(Integer bindPort) {
		this.bindPort = bindPort;
	}

	/**
