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
	 * @author tanyaowu
	 */
	public static void main(String[] args) {
		JfinalInit.init();

		Page<Donate> page = DonateService.me.page(1, 10);
		System.out.println(page);
	}

	private final Donate dao = new Donate().dao();

	/**
	 *
	 * @author tanyaowu
	 */
	public DonateService() {
	}

	@SuppressWarnings("unchecked")
	public Page<Donate> page(int pageNumber, int pageSize) {
		String cacheName = EhcacheConst.CacheName.T_60;
		String cacheKey = "donate_page" + "_" + pageNumber + "_" + pageSize;
		Object obj = CacheKit.get(cacheName, cacheKey);
		if (obj != null) {
			return (Page<Donate>) obj;
