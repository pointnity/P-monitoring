package org.tio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.Node;
import org.tio.core.intf.Packet;
import org.tio.core.stat.ChannelStat;
import org.tio.utils.SystemTimer;
import org.tio.utils.lock.SetWithLock;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

/**
 *
 * @author tanyaowu
 *  
 */
public class AioClient {
	private static class ReconnRunnable implements Runnable {
		ClientChannelContext channelContext = null;
		AioClient aioClient = null;

		//		private static Map<Node, Long> cacheMap = new HashMap<>();

		public ReconnRunnable(ClientChannelContext channelContext, AioClient aioClient) {
			this.channelContext = channelContext;
			this.aioClient = aioClient;
		}

		/**
		 * @see java.lang.Runnable#run()
		 *
		 * @author tanyaowu
		 *  
		 *
		 */
		@Override
		public void run() {
			ReentrantReadWriteLock closeLock = channelContext.getCloseLock();
			WriteLock writeLock = closeLock.writeLock();

			try {
				writeLock.lock();
				if (!channelContext.isClosed()) //It's already connected, no need to re-connect.
				{
					return;
				}
				long start = SystemTimer.currentTimeMillis();
				aioClient.reconnect(channelContext, 2);
				long end = SystemTimer.currentTimeMillis();
				long iv = end - start;
				if (iv >= 100) {
					log.error("{},Time to re-connect:{} ms", channelContext, iv);
				} else {
					log.info("{},Time to re-connect:{} ms", channelContext, iv);
				}

				if (channelContext.isClosed()) {
					channelContext.setReconnCount(channelContext.getReconnCount() + 1);
					//					cacheMap.put(channelContext.getServerNode(), SystemTimer.currentTimeMillis());
					return;
				}
			} catch (java.lang.Throwable e) {
				log.error(e.toString(), e);
			} finally {
				writeLock.unlock();
			}

		}
	}

	private static Logger log = LoggerFactory.getLogger(AioClient.class);

	private AsynchronousChannelGroup channelGroup;

	private ClientGroupContext clientGroupContext;

	/**
	 * @param serverIp Can be empty
	 * @param serverPort
	 * @param aioDecoder
	 * @param aioEncoder
	 * @param aioHandler
	 *
	 * @author tanyaowu
	 * @throws IOException
	 *
	 */
	public AioClient(final ClientGroupContext clientGroupContext) throws IOException {
		super();
		this.clientGroupContext = clientGroupContext;
		//		ExecutorService groupExecutor = clientGroupContext.getGroupExecutor();
		this.channelGroup = AsynchronousChannelGroup.withThreadPool(clientGroupContext.getGroupExecutor());

		startHeartbeatTask();
		startReconnTask();
	}

	/**
	 *
	 * @param serverNode
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public void asynConnect(Node serverNode) throws Exception {
		asynConnect(serverNode, null);
	}

	/**
	 *
	 * @param serverNode
	 * @param timeout
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public void asynConnect(Node serverNode, Integer timeout) throws Exception {
		asynConnect(serverNode, null, null, timeout);
	}

	/**
	 *
	 * @param serverNode
	 * @param bindIp
	 * @param bindPort
	 * @param timeout
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public void asynConnect(Node serverNode, String bindIp, Integer bindPort, Integer timeout) throws Exception {
		connect(serverNode, bindIp, bindPort, null, timeout, false);
	}

	/**
	 *
	 * @param serverNode
	 * @return
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public ClientChannelContext connect(Node serverNode) throws Exception {
		return connect(serverNode, null);
	}

	/**
	 *
	 * @param serverNode
	 * @param timeout
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public ClientChannelContext connect(Node serverNode, Integer timeout) throws Exception {
		return connect(serverNode, null, 0, timeout);
	}

	/**
	 *
	 * @param serverNode
	 * @param bindIp
	 * @param bindPort
	 * @param initClientChannelContext
	 * @param timeout Time-out, per second
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public ClientChannelContext connect(Node serverNode, String bindIp, Integer bindPort, ClientChannelContext initClientChannelContext, Integer timeout) throws Exception {
		return connect(serverNode, bindIp, bindPort, initClientChannelContext, timeout, true);
	}

	/**
	 *
	 * @param serverNode
	 * @param bindIp
	 * @param bindPort
	 * @param initClientChannelContext
	 * @param timeout Time-out, per second
	 * @param isSyn true: Synchronous, false: Asynchronous
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	private ClientChannelContext connect(Node serverNode, String bindIp, Integer bindPort, ClientChannelContext initClientChannelContext, Integer timeout, boolean isSyn)
			throws Exception {

		AsynchronousSocketChannel asynchronousSocketChannel = null;
		ClientChannelContext channelContext = null;
		boolean isReconnect = initClientChannelContext != null;
		//		ClientAioListener clientAioListener = clientGroupContext.getClientAioListener();

		long start = SystemTimer.currentTimeMillis();
