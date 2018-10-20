package org.tio.core;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.tio.core.intf.Packet;
import org.tio.core.intf.PacketWithMeta;
import org.tio.core.stat.ChannelStat;
import org.tio.core.task.DecodeRunnable;
import org.tio.core.task.HandlerRunnable;
import org.tio.core.task.SendRunnable;
import org.tio.utils.json.Json;
import org.tio.utils.prop.MapWithLockPropSupport;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateTime;

/**
 * 
 * @author tanyaowu 
 *  
 */
public abstract class ChannelContext extends MapWithLockPropSupport {
	private static Logger log = LoggerFactory.getLogger(ChannelContext.class);

	private static final String DEFAULT_ATTUBITE_KEY = "t-io-d-a-k";

	public static final String UNKNOWN_ADDRESS_IP = "$UNKNOWN";

	public static final AtomicInteger UNKNOWN_ADDRESS_PORT_SEQ = new AtomicInteger();

	private boolean isTraceClient = false;

	private boolean isTraceSynPacket = false;

	//	private MapWithLock<String, Object> props = null;//

	private GroupContext groupContext = null;

	private DecodeRunnable decodeRunnable = null;

	private HandlerRunnable handlerRunnable = null;

	private SendRunnable sendRunnable = null;
	private ReentrantReadWriteLock closeLock = new ReentrantReadWriteLock();
	private ReadCompletionHandler readCompletionHandler = null;//new ReadCompletionHandler(this);
	private WriteCompletionHandler writeCompletionHandler = null;//new WriteCompletionHandler(this);

	private int reconnCount = 0;//Consecutive reconnection times, this value is reset after the connection is successful 0

	private String userid;
	
	private String token;

	private boolean isWaitingClose = false;

	private boolean isClosed = true;

	private boolean isRemoved = false;

	private ChannelStat stat = new ChannelStat();

	/** The asynchronous socket channel. */
	private AsynchronousSocketChannel asynchronousSocketChannel;

	private String id = null;

	private Node clientNode;

	private String clientNodeTraceFilename;

	private Node serverNode;

	private Logger traceSynPacketLog = LoggerFactory.getLogger("tio-client-trace-syn-log");

	/**
	 *
	 * @param groupContext
	 * @param asynchronousSocketChannel
	 * @author tanyaowu
	 */
	public ChannelContext(GroupContext groupContext, AsynchronousSocketChannel asynchronousSocketChannel) {
		super();
		init(groupContext, asynchronousSocketChannel);
	}

	private void assignAnUnknownClientNode() {
		Node clientNode = new Node(UNKNOWN_ADDRESS_IP, UNKNOWN_ADDRESS_PORT_SEQ.incrementAndGet());
		setClientNode(clientNode);
	}

	/**
	 * Create node
	 * @param asynchronousSocketChannel
	 * @return
	 * @throws IOException
	 * @author tanyaowu
	 */
	public abstract Node createClientNode(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException;

	/**
	 *
	 * @param obj
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChannelContext other = (ChannelContext) obj;
		return Objects.equals(other.hashCode(), this.hashCode());
	}

	/**
	 * @return the asynchronousSocketChannel
	 */
	public AsynchronousSocketChannel getAsynchronousSocketChannel() {
		return asynchronousSocketChannel;
	}

	public Object getAttribute() {
