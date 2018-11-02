package org.tio.server;

import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.intf.AioHandler;
import org.tio.core.intf.AioListener;
import org.tio.core.stat.ChannelStat;
import org.tio.core.stat.GroupStat;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;
import org.tio.utils.SystemTimer;
import org.tio.utils.json.Json;
import org.tio.utils.lock.ObjWithLock;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

/**
 * 
 * @author tanyaowu 
 *  
 */
public class ServerGroupContext extends GroupContext {
	static Logger log = LoggerFactory.getLogger(ServerGroupContext.class);

	private AcceptCompletionHandler acceptCompletionHandler = null;

	private ServerAioHandler serverAioHandler = null;

	private ServerAioListener serverAioListener = null;

	protected ServerGroupStat serverGroupStat = new ServerGroupStat();

	/** The accept executor. */
	//private ThreadPoolExecutor acceptExecutor = null;

	private Thread checkHeartbeatThread = null;

	/**
	 * 
	 * @param serverAioHandler
	 * @param serverAioListener
	 * @author: tanyaowu
	 */
	public ServerGroupContext(ServerAioHandler serverAioHandler, ServerAioListener serverAioListener) {
		this(null, serverAioHandler, serverAioListener);
	}

	/**
	 * 
	 * @param name
	 * @param serverAioHandler
	 * @param serverAioListener
	 * @author: tanyaowu
	 */
	public ServerGroupContext(String name, ServerAioHandler serverAioHandler, ServerAioListener serverAioListener) {
		this(name, serverAioHandler, serverAioListener, null, null);
	}

	/**
	 * 
	 * @param serverAioHandler
	 * @param serverAioListener
	 * @param tioExecutor
	 * @param groupExecutor
	 * @author: tanyaowu
	 */
	public ServerGroupContext(ServerAioHandler serverAioHandler, ServerAioListener serverAioListener, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor) {
		this(null, serverAioHandler, serverAioListener, tioExecutor, groupExecutor);
	}

	/**
	 * 
	 * @param name
	 * @param serverAioHandler
	 * @param serverAioListener
	 * @param tioExecutor
	 * @param groupExecutor
	 * @author: tanyaowu
	 */
	public ServerGroupContext(String name, ServerAioHandler serverAioHandler, ServerAioListener serverAioListener, SynThreadPoolExecutor tioExecutor,
			ThreadPoolExecutor groupExecutor) {
		super(tioExecutor, groupExecutor);
		this.name = name;
		this.acceptCompletionHandler = new AcceptCompletionHandler();
		this.serverAioHandler = serverAioHandler;
		this.serverAioListener = serverAioListener == null ? new DefaultServerAioListener() : serverAioListener;
		checkHeartbeatThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!isStopped()) {
					//					long sleeptime = heartbeatTimeout;
					if (heartbeatTimeout <= 0) {
						log.info("{}, User canceled the frame level of heartbeat detection, if the business needs, please users to complete their heartbeat detection", ServerGroupContext.this.name);
						break;
					}
					try {
						Thread.sleep(heartbeatTimeout);
					} catch (InterruptedException e1) {
						log.error(e1.toString(), e1);
					}
					long start = SystemTimer.currentTimeMillis();
					ObjWithLock<Set<ChannelContext>> objWithLock = ServerGroupContext.this.connections.getSetWithLock();
					Set<ChannelContext> set = null;
					ReadLock readLock = objWithLock.getLock().readLock();
					long start1 = 0;
					int count = 0;
					try {
						readLock.lock();
						start1 = SystemTimer.currentTimeMillis();
						set = objWithLock.getObj();

						for (ChannelContext entry : set) {
							count++;
							ChannelContext channelContext = entry;
							ChannelStat stat = channelContext.getStat();
							long timeLatestReceivedMsg = stat.getLatestTimeOfReceivedByte();
							long timeLatestSentMsg = stat.getLatestTimeOfSentPacket();
							long compareTime = Math.max(timeLatestReceivedMsg, timeLatestSentMsg);
							long currtime = SystemTimer.currentTimeMillis();
