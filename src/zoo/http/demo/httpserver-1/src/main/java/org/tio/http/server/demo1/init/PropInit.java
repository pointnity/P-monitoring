package org.tio.http.server.demo1.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.PropKit;

/**
 * @author tanyaowu
 *  
 */
public class PropInit {
	private static Logger log = LoggerFactory.getLogger(PropInit.class);

	public static void init() {
		try {
			PropKit.use("app.properties");
		} catch (Exception e2) {
