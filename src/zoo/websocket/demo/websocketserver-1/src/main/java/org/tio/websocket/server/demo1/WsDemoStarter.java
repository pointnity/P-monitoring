package org.tio.websocket.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.server.WsServerStarter;

/**
 * @author tanyaowu
 * 
 */
public class WsDemoStarter {
	private static Logger log = LoggerFactory.getLogger(WsDemoStarter.class);

	/**
	 * @param args
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		WsDemoStarter appStarter = new WsDemoStarter(9321, new WsDemoMsgHandler());
		appStarter.start();
	}

	private WsServerStarter wsServerStarter;
//	private WsDemoMsgHandler wsMsgHandler;
	private ServerGroupContext serverGroupContext;

	/**
	 *
	 * @author tanyaowu
	 */
	public WsDemoStarter(int port, WsDemoMsgHandler wsMsgHandler) throws IOException {
		wsServerStarter = new WsServerStarter(port, wsMsgHandler);
		serverGroupContext = wsServerStarter.getServerGroupContext();
	}

	public WsServerStarter getWsServerStarter() {
		return wsServerStarter;
	}

	public void start() throws IOException {
		wsServerStarter.start();
	}

	/**
