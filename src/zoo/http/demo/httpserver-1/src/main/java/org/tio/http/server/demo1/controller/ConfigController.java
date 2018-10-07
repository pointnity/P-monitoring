package org.tio.http.server.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.util.Resps;

import com.jfinal.kit.PropKit;

/**
 * @author tanyaowu
 * 
 */
@RequestPath(value = "/config")
public class ConfigController {
	private static Logger log = LoggerFactory.getLogger(ConfigController.class);

	/**
