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

	//handler, Includes encoding, decoding, message processing
	public static ServerAioHandler aioHandler = null;

	//Event listener, which can be null, but it is recommended to implement the interface yourself, you can refer to showcase to learn some interfaces
	public static ServerAioListener aioListener = null;

	//A set of connection-sharing context objects
	public static ServerGroupContext serverGroupContext = null;

	//aioServer Object
	public static AioServer aioServer = null;

	/**
	 * 
	 * @param ip can be null
	 * @param port If NULL, the default port is used
	 * @param tioExecutor
	 * @param groupExecutor
	 * @author tanyaowu
	 */
	public static void start(String ip, Integer port, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor) {
		if (port == null) {
			port = Const.PORT;
		}
		aioHandler = new FlashPolicyServerAioHandler();

		serverGroupContext = new ServerGroupContext(aioHandler, aioListener, tioExecutor, groupExecutor);

		serverGroupContext.setHeartbeatTimeout(Const.TIMEOUT);

		aioServer = new AioServer(serverGroupContext);

		try {
			aioServer.start(ip, port);
		} catch (Throwable e) {
			log.error(e.toString(), e);
			System.exit(1);
		}
	}
	
	/**
	 * 
	 * @param ip
	 * @param port
	 * @author tanyaowu
	 */
	public static void start(String ip, Integer port) {
