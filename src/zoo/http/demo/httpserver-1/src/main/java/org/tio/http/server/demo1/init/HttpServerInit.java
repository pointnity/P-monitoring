package org.tio.http.server.demo1.init;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpConfig;
import org.tio.http.common.handler.HttpRequestHandler;
import org.tio.http.server.HttpServerStarter;
import org.tio.http.server.demo1.HttpServerDemoStarter;
import org.tio.http.server.handler.DefaultHttpRequestHandler;
import org.tio.http.server.mvc.Routes;
import org.tio.utils.SystemTimer;

import com.jfinal.kit.PropKit;

/**
 * @author tanyaowu
 *  
 */
public class HttpServerInit {
	private static Logger log = LoggerFactory.getLogger(HttpServerInit.class);

	public static HttpConfig httpConfig;

	public static HttpRequestHandler requestHandler;

	public static HttpServerStarter httpServerStarter;

	public static void init() throws Exception {
		long start = SystemTimer.currentTimeMillis();

		PropKit.use("app.properties");

		int port = PropKit.getInt("http.port");//Start port
