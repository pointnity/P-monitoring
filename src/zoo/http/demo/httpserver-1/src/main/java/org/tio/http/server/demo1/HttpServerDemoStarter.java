package org.tio.http.server.demo1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.init.HttpServerInit;
import org.tio.http.server.demo1.init.JfinalInit;
import org.tio.http.server.demo1.init.JsonInit;
import org.tio.http.server.demo1.init.PropInit;

/**
 * ab -c 10 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 20 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 40 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 60 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 80 -n 200000 -k http://127.0.0.1:9527/test/abtest
* ab -c 100 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 200 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 300 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * ab -c 400 -n 200000 -k http://127.0.0.1:9527/test/abtest
 * @author tanyaowu
 *  
 */
public class HttpServerDemoStarter {
	private static Logger log = LoggerFactory.getLogger(HttpServerDemoStarter.class);

	//	public static Config conf = ConfigFactory.load("app.properties");

	/**
	 * @param args
	 * @author tanyaowu
	 * @throws IOException
	 */
	public static void main(String[] args) throws Exception {
		PropInit.init();
		
