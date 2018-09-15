package org.tio.flash.policy.server;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

 /**
 *
 * @author tanyaowu
 * 
 */
public class FlashPolicyServerStarter { 
	private static Logger log = LoggerFactory.getLogger(FlashPolicyServerStarter.class);
