package org.tio.websocket.server;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.intf.TioUuid;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;
import org.tio.websocket.common.WsTioUuid;
import org.tio.websocket.server.handler.IWsMsgHandler;

/**
 *
 * @author tanyaowu
 * July 30, 2017 9:45:54 AM
 */
public class WsServerStarter {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(WsServerStarter.class);

	private WsServerConfig wsServerConfig = null;

	private IWsMsgHandler wsMsgHandler = null;

	private WsServerAioHandler wsServerAioHandler = null;

	private WsServerAioListener wsServerAioListener = null;

	private ServerGroupContext serverGroupContext = null;

	private AioServer aioServer = null;

	/**
	 * @return the wsServerConfig
	 */
	public WsServerConfig getWsServerConfig() {
		return wsServerConfig;
	}

	/**
	 * @return the wsMsgHandler
	 */
	public IWsMsgHandler getWsMsgHandler() {
		return wsMsgHandler;
	}

	/**
	 * @return the wsServerAioHandler
	 */
	public WsServerAioHandler getWsServerAioHandler() {
		return wsServerAioHandler;
	}

	/**
	 * @return the wsServerAioListener
	 */
