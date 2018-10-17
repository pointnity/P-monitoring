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
		asynchronousSocketChannel = AsynchronousSocketChannel.open(channelGroup);
		long end = SystemTimer.currentTimeMillis();
		long iv = end - start;
		if (iv >= 100) {
			log.error("{}, open Take:{} ms", channelContext, iv);
		}

		asynchronousSocketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
		asynchronousSocketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		asynchronousSocketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, true);

		InetSocketAddress bind = null;
		if (bindPort != null && bindPort > 0) {
			if (StringUtils.isNotBlank(bindIp)) {
				bind = new InetSocketAddress(bindIp, bindPort);
			} else {
				bind = new InetSocketAddress(bindPort);
			}
		}

		if (bind != null) {
			asynchronousSocketChannel.bind(bind);
		}

		channelContext = initClientChannelContext;

		start = SystemTimer.currentTimeMillis();

		InetSocketAddress inetSocketAddress = new InetSocketAddress(serverNode.getIp(), serverNode.getPort());

		ConnectionCompletionVo attachment = new ConnectionCompletionVo(channelContext, this, isReconnect, asynchronousSocketChannel, serverNode, bindIp, bindPort);

		if (isSyn) {
			Integer realTimeout = timeout;
			if (realTimeout == null) {
				realTimeout = 5;
			}

			CountDownLatch countDownLatch = new CountDownLatch(1);
			attachment.setCountDownLatch(countDownLatch);
			asynchronousSocketChannel.connect(inetSocketAddress, attachment, clientGroupContext.getConnectionCompletionHandler());
			countDownLatch.await(realTimeout, TimeUnit.SECONDS);
			return attachment.getChannelContext();
		} else {
			asynchronousSocketChannel.connect(inetSocketAddress, attachment, clientGroupContext.getConnectionCompletionHandler());
			return null;
		}
	}

	/**
	 *
	 * @param serverNode
	 * @param bindIp
	 * @param bindPort
	 * @param timeout Time-out, per second
	 * @return
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public ClientChannelContext connect(Node serverNode, String bindIp, Integer bindPort, Integer timeout) throws Exception {
		return connect(serverNode, bindIp, bindPort, null, timeout);
	}

	/**
	 * @return the channelGroup
	 */
	public AsynchronousChannelGroup getChannelGroup() {
		return channelGroup;
	}

	/**
	 * @return the clientGroupContext
	 */
	public ClientGroupContext getClientGroupContext() {
		return clientGroupContext;
	}

	/**
	 *
	 * @param channelContext
	 * @param timeout
	 * @return
	 * @throws Exception
	 *
	 * @author tanyaowu
	 *
	 */
	public void reconnect(ClientChannelContext channelContext, Integer timeout) throws Exception {
		connect(channelContext.getServerNode(), channelContext.getBindIp(), channelContext.getBindPort(), channelContext, timeout);
	}

	/**
	 * @param clientGroupContext the clientGroupContext to set
	 */
	public void setClientGroupContext(ClientGroupContext clientGroupContext) {
		this.clientGroupContext = clientGroupContext;
	}

	/**
	 * Timed tasks: Heartbeat
	 * @author tanyaowu
	 *
	 */
	private void startHeartbeatTask() {
		final ClientGroupStat clientGroupStat = clientGroupContext.getClientGroupStat();
		final ClientAioHandler aioHandler = clientGroupContext.getClientAioHandler();

		final String id = clientGroupContext.getId();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!clientGroupContext.isStopped()) {
					final long heartbeatTimeout = clientGroupContext.getHeartbeatTimeout();
					if (heartbeatTimeout <= 0) {
						log.warn("The user cancels the frame-level heartbeat timing send function, please the user to complete the heartbeat mechanism");
						break;
					}
					ReadLock readLock = null;
					try {
						SetWithLock<ChannelContext> setWithLock = clientGroupContext.connecteds.getSetWithLock();
						readLock = setWithLock.getLock().readLock();
						readLock.lock();
						Set<ChannelContext> set = setWithLock.getObj();
						long currtime = SystemTimer.currentTimeMillis();
						for (ChannelContext entry : set) {
							ClientChannelContext channelContext = (ClientChannelContext) entry;
							if (channelContext.isClosed() || channelContext.isRemoved()) {
								continue;
							}

							ChannelStat stat = channelContext.getStat();
							long latestTimeOfReceivedByte = stat.getLatestTimeOfReceivedByte();
							long latestTimeOfSentPacket = stat.getLatestTimeOfSentPacket();
							long compareTime = Math.max(latestTimeOfReceivedByte, latestTimeOfSentPacket);
							long interval = currtime - compareTime;
							if (interval >= heartbeatTimeout / 2) {
								Packet packet = aioHandler.heartbeatPacket();
								if (packet != null) {
									log.info("{}Send Heartbeat Pack", channelContext.toString());
									Aio.send(channelContext, packet);
								}
							}
						}
						if (log.isInfoEnabled()) {
							log.info("[{}]: curr:{}, closed:{}, received:({}p)({}b), handled:{}, sent:({}p)({}b)", id, set.size(), clientGroupStat.getClosed().get(),
									clientGroupStat.getReceivedPackets().get(), clientGroupStat.getReceivedBytes().get(), clientGroupStat.getHandledPacket().get(),
									clientGroupStat.getSentPacket().get(), clientGroupStat.getSentBytes().get());
						}

					} catch (Throwable e) {
						log.error("", e);
					} finally {
						try {
							if (readLock != null) {
								readLock.unlock();
							}
							Thread.sleep(heartbeatTimeout / 4);
						} catch (Throwable e) {
							log.error(e.toString(), e);
						} finally {

						}
					}
				}
			}
		}, "tio-timer-heartbeat" + id).start();
	}

	/**
	 * Start the re-connect task
	 *
	 *
	 * @author tanyaowu
	 *
	 */
