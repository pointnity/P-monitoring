package org.tio.http.server.demo1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.http.server.annotation.RequestPath;
import org.tio.http.server.demo1.model.Donate;
import org.tio.http.server.demo1.service.DonateService;
import org.tio.http.server.util.Resps;

import com.jfinal.plugin.activerecord.Page;

/**
 * @author tanyaowu
 *  
 */
@RequestPath(value = "/donate")
public class DonateController {
	private static Logger log = LoggerFactory.getLogger(DonateController.class);

	static final DonateService srv = DonateService.me;

	/**
