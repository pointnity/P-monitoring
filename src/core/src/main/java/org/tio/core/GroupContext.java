package org.tio.core;

import java.nio.ByteOrder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.ReconnConf;
import org.tio.core.intf.AioHandler;
import org.tio.core.intf.AioListener;
import org.tio.core.intf.ChannelTraceHandler;
import org.tio.core.intf.GroupListener;
import org.tio.core.intf.TioUuid;
import org.tio.core.maintain.ChannelContextMapWithLock;
import org.tio.core.maintain.ChannelContextSetWithLock;
import org.tio.core.maintain.ClientNodeMap;
import org.tio.core.maintain.Groups;
import org.tio.core.maintain.Ids;
import org.tio.core.maintain.IpBlacklist;
import org.tio.core.maintain.IpStats;
import org.tio.core.maintain.Ips;
import org.tio.core.maintain.Tokens;
import org.tio.core.maintain.Users;
import org.tio.core.stat.GroupStat;
import org.tio.utils.prop.MapWithLockPropSupport;
import org.tio.utils.thread.pool.DefaultThreadFactory;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

/**
 * 
 * @author tanyaowu 
 *  
 */
public abstract class GroupContext extends MapWithLockPropSupport {
	static Logger log = LoggerFactory.getLogger(GroupContext.class);

	private static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;

	//	public static final int CORE_POOL_SIZE = _CORE_POOL_SIZE;// < 160 ? 160 : _CORE_POOL_SIZE;

	private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 4 < 256 ? 256 : CORE_POOL_SIZE * 4;

	//	public static final Semaphore SYN_SEND_SEMAPHORE = new Semaphore(CORE_POOL_SIZE);

	//	/**
	//	 * Default Heartbeat Time-out (units: milliseconds)
	//	 */
	//	private static final long DEFAULT_HEARTBEAT_TIMEOUT = 1000 * 120;

	/**
	 * The default buffer size for receiving data
	 */
	public static final int READ_BUFFER_SIZE = Integer.getInteger("tio.default.read.buffer.size", 2048);

	public static final long KEEP_ALIVE_TIME = 90L;

	private final static AtomicInteger ID_ATOMIC = new AtomicInteger();

	private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;

	private boolean isShortConnection = false;

	/**
	 * Heartbeat timeout (in milliseconds), set this value to 0 or negative if the user does not want the frame level to do the heartbeat-related work
	 */
	protected long heartbeatTimeout = 1000 * 120;

	private PacketHandlerMode packetHandlerMode = PacketHandlerMode.SINGLE_THREAD;//.queue;

	/**
	 * Buffer size to receive data
	 */
	protected int readBufferSize = READ_BUFFER_SIZE;

	protected ReconnConf reconnConf;//Re-connect configuration

	private ChannelTraceHandler clientTraceHandler = new DefaultChannelTraceHandler();

	private GroupListener groupListener = null;

	private TioUuid tioUuid = new DefaultTioUuid();

	/** The group executor. */
	protected SynThreadPoolExecutor tioExecutor = null;

	protected ThreadPoolExecutor groupExecutor = null;
	public final ClientNodeMap clientNodeMap = new ClientNodeMap();
	public final ChannelContextSetWithLock connections = new ChannelContextSetWithLock();
	public final ChannelContextSetWithLock connecteds = new ChannelContextSetWithLock();

	public final ChannelContextSetWithLock closeds = new ChannelContextSetWithLock();
	public final Groups groups = new Groups();
	public final Users users = new Users();
	public final Tokens tokens = new Tokens();
	public final Ids ids = new Ids();
	public final Ips ips = new Ips();
	public IpStats ipStats = null;

	/**
