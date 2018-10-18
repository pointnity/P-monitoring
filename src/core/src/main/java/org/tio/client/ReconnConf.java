package org.tio.client;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.utils.SystemTimer;
import org.tio.utils.thread.pool.DefaultThreadFactory;

/**
 *
 * @author tanyaowu
 *  
 */
public class ReconnConf {
	private static Logger log = LoggerFactory.getLogger(ChannelContext.class);

	public static boolean isNeedReconn(ClientChannelContext clientChannelContext, boolean putIfTrue) {
		ClientGroupContext clientGroupContext = (ClientGroupContext) clientChannelContext.getGroupContext();
		ReconnConf reconnConf = clientGroupContext.getReconnConf();
		if (reconnConf != null && reconnConf.getInterval() > 0) {
			if (reconnConf.getRetryCount() <= 0 || reconnConf.getRetryCount() >= clientChannelContext.getReconnCount()) {
				if (putIfTrue) {
					clientChannelContext.getStat().setTimeInReconnQueue(SystemTimer.currentTimeMillis());
					reconnConf.getQueue().add(clientChannelContext);
				}
				return true;
			} else {
				log.info("No need to re-connect{}", clientChannelContext);
				return false;
			}
		}

		return false;
	}

	public static void put(ClientChannelContext clientChannelContext) {
		isNeedReconn(clientChannelContext, true);
	}

	/**
	 *The interval of the re-connection, in milliseconds per hour
	 */
	private long interval = 5000;

	/**
	 * Consecutive re-connection times, which are no longer re-connected when the successive re-connection fails so many times.0 and negative numbers are always re-connected
	 */
	private int retryCount = 0;

	LinkedBlockingQueue<ChannelContext> queue = new LinkedBlockingQueue<>();

	/**
	 * Thread pool to re-connect
	 */
	private ThreadPoolExecutor threadPoolExecutor = null;

	/**
	 *
	 *
	 * @author tanyaowu
	 *
	 */
	public ReconnConf() {
		if (threadPoolExecutor == null) {
			synchronized (ReconnConf.class) {
				if (threadPoolExecutor == null) {
					threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 60L, TimeUnit.SECONDS,
							new LinkedBlockingQueue<Runnable>(), DefaultThreadFactory.getInstance("tio-client-reconn"));
				}
			}

		}

	}

	/**
	 * @param interval
	 *
	 * @author tanyaowu
	 *
	 */
	public ReconnConf(long interval) {
		this();
		this.setInterval(interval);
	}

	/**
	 * @param interval
	 * @param retryCount
	 *
	 * @author tanyaowu
