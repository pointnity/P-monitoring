package org.tio.http.server.demo1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.init.JfinalInit;
import org.tio.http.server.demo1.model.Donate;
import org.tio.http.server.demo1.utils.EhcacheConst;

import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author tanyaowu
 *  
 */
public class DonateService {
	private static Logger log = LoggerFactory.getLogger(DonateService.class);
	public static final DonateService me = new DonateService();

	/**
	 * @param args
