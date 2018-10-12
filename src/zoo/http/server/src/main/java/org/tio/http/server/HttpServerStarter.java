package org.tio.http.server;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.GroupContextKey;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpUuid;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.common.session.id.impl.UUIDSessionIdGenerator;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.http.server.intf.HttpServerInterceptor;
import org.tio.http.server.intf.HttpSessionListener;
import org.tio.http.server.mvc.Routes;
import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.utils.cache.ICache;
import org.tio.utils.cache.guava.GuavaCache;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

/**
 *
 * @author tanyaowu
 */
public class HttpServerStarter {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(HttpServerStarter.class);

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 * @throws IOException
	 *  
	 *
	 */
	public static void main(String[] args) throws IOException {
	}

	private HttpConfig httpConfig = null;

	private HttpServerAioHandler httpServerAioHandler = null;

	//	private HttpGroupListener httpGroupListener = null;

	private HttpServerAioListener httpServerAioListener = null;

	private ServerGroupContext serverGroupContext = null;
