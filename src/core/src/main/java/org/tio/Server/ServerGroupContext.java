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
							long interval = currtime - compareTime;
							if (interval > heartbeatTimeout) {
								log.info("{}, {} Ms does not send and receive messages", channelContext, interval);
								Aio.remove(channelContext, interval + " Ms does not send and receive messages");
							}
						}
					} catch (Throwable e) {
						log.error("", e);
					} finally {
						try {
							readLock.unlock();

							if (log.isInfoEnabled()) {
								int groups = 0;
								ObjWithLock<Set<ChannelContext>> objwithlock = ServerGroupContext.this.groups.clients(ServerGroupContext.this, "g");
								if (objwithlock != null) {
									groups = objwithlock.getObj().size();
								}

								log.info(
										"{}, [{}]:[{}]" 
												+  "\r\nNumber of current connections{}" 
												+  "\r\n currently has a different number of ips{}"
												+  "\r\ngroup(g) {}"
												+  "\r\n accept connection{}" 
												+  "\r\n Total number of connections closed {}"
												+  "\r\nreceived message ({}p)({}b)" 
												+  "\r\nProcessed message {}p"
											+  "\r\nSent message ({}p)({}b)" 
												+  "\r\n average number of bytes received per TCP packet {}" 
												+  "\r\nAverage service packets received per TCP packet{}"
												+  "\r\n Duration of current IP statistics{}" ,
										ServerGroupContext . this . name , 
										SystemTimer . currentTimeMillis (), 
										Id , 
										Set . size (), 
										ServerGroupContext . this . ips . getIpmap (). getObj (). size (),
										Groups , 
										serverGroupStat . getAccepted (). get (),
										serverGroupStat . getClosed (). get (), 
										serverGroupStat . getReceivedPackets (). get (), 
										serverGroupStat . getReceivedBytes (). get (),
										serverGroupStat . getHandledPacket (). get (), 
										serverGroupStat . getSentPacket (). get (), 
										serverGroupStat . getSentBytes (). get (),
										serverGroupStat . getBytesPerTcpReceive (), 
										serverGroupStat . getPacketsPerTcpReceive (), 
										Json . toJson ( ServerGroupContext . this . ipStats . durationList ));
							}

							/ / Print each collection information
							If  ( log . isInfoEnabled ())  {
								Log . info ( "{}, " 
									+  "\r\nclientNodes:{}" 
									+  "\r\nAll connections: {}" 
									+  "\r\nConnected currently:{}" 
									+  "\r\nClosed connections: {}" 
									+  "\r\ngroup: [channelmap:{}, groupmap:{}]"
									+  "\r\n bind userid number: {}" 
									+  "\r\n bind token number: {}" 
									+  "\r\nwaiting for sync message response: {}"
								// + "\r\nThe number of ips being monitored: {}"
										+  "\r\n blacked out ip:{}" ,  ServerGroupContext . this . name ,  ServerGroupContext . this . clientNodeMap . getMap (). getObj (). size (),
										ServerGroupContext . The this . Connections . GetSetWithLock (). GetObj (). Size (),  ServerGroupContext . The this . Connecteds . GetSetWithLock (). GetObj (). Size (),
										ServerGroupContext . this . closeds . getSetWithLock (). getObj (). size (),  ServerGroupContext . this . groups . getChannelmap (). getObj (). size (),
										ServerGroupContext . The this . Groups . GetGroupmap (). GetObj (). Size (),  ServerGroupContext . The this . Users . The getMap (). GetObj (). Size (),  ServerGroupContext . The this . Tokens . The getMap (). GetObj (). Size (),
										ServerGroupContext . this . waitingResps . getMap (). getObj (). size (),
										// ServerGroupContext.this.ips.size(),
										Json . toJson ( ServerGroupContext . this . ipBlacklist . getCopy ()));
							}

							If  ( log . isInfoEnabled ())  {
								Long  end  =  SystemTimer . currentTimeMillis ();
								Long  iv1  =  start1  -  start ;
								Long  iv  =  end  -  start1 ;
								Log . info ( "{}, check heartbeat, total {} connections, take locks {}ms, loop time {}ms, heartbeat timeout: {}ms" ,  ServerGroupContext . this . name ,  count ,  iv1 ,  iv ,  heartbeatTimeout );
							}
						}  catch  ( Throwable  e )  {
							Log . error ( "" ,  e );
						}
					}
				}
			}
		},  "tio-timer-checkheartbeat-"  +  id );
		checkHeartbeatThread . setDaemon ( to true );
		checkHeartbeatThread . setPriority ( Thread . MIN_PRIORITY );
		checkHeartbeatThread . start ();
	}

	/**
	 * @return the acceptCompletionHandler
	 */
	Public  AcceptCompletionHandler  getAcceptCompletionHandler ()  {
		Return  acceptCompletionHandler ;
	}

	/**
	 * @see org.tio.core.GroupContext#getAioHandler()
	 *
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	Public  AioHandler  getAioHandler ()  {
		Return  this . getServerAioHandler ();
	}

	/**
	 * @see org.tio.core.GroupContext#getAioListener()
	 *
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	Public  AioListener  getAioListener ()  {
		Return  getServerAioListener ();
	}

	/**
	 * @see org.tio.core.GroupContext#getGroupStat()
	 *
	 * @return
	 * @author tanyaowu
	 * 
	 *
	 */
