package org.tio.http.server.demo1.init;

import java.io.File;
import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.http.server.demo1.model._MappingKit;

import com.alibaba.druid.filter.config.ConfigTools;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;

/**
 * @author tanyaowu
 *  
 */
public class JfinalInit {
	private static Logger log = LoggerFactory.getLogger(JfinalInit.class);

	public static DruidPlugin druidPlugin;

	public static void init() {
		try {
			PropInit.init();
		} catch (Exception e2) {
			log.error(e2.toString(), e2);
		}
