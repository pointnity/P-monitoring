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
