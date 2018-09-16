/**
 *
 */
package org.tio.flash.policy.server;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.tio.utils.thread.pool.DefaultThreadFactory;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

 /**
 * 
 * @author tanyaowu 
 * 
 */
 public class Threads {
	public static int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 1;

	// public static final int CORE_POOL_SIZE = _CORE_POOL_SIZE;// < 160 ? 160 :
	// _CORE_POOL_SIZE;

	public static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2 < 16 ? 16 : CORE_POOL_SIZE * 2;

	public static final long KEEP_ALIVE_TIME = 90L;
	public static ThreadPoolExecutor groupExecutor = null;
	public static SynThreadPoolExecutor tioExecutor = null;

	static {
		LinkedBlockingQueue<Runnable> tioQueue = new LinkedBlockingQueue<>();
		String tioThreadName = "tio";
