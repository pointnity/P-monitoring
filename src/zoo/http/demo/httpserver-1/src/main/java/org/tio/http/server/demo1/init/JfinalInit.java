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

		try {
			final String URL = PropKit.get("db01.jdbc.url");
			final String USERNAME = PropKit.get("db01.jdbc.username");
			String PASSWORD = null;
			try {
				PASSWORD = ConfigTools.decrypt(PropKit.get("db01.jdbc.password"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			final Integer INITIALSIZE = PropKit.getInt("db.jdbc.pool.initialSize");
			final Integer MIDIDLE = PropKit.getInt("db.jdbc.pool.minIdle");
			final Integer MAXACTIVEE = PropKit.getInt("db.jdbc.pool.maxActive");
			druidPlugin = new DruidPlugin(URL, USERNAME, PASSWORD);
			//		druidPlugin.setConnectionProperties(PropKit.get("connectionProperties"));
			druidPlugin.set(INITIALSIZE, MIDIDLE, MAXACTIVEE);
			druidPlugin.setFilters("stat,wall");
			druidPlugin.start();
		} catch (Exception e1) {
			log.error(e1.toString(), e1);
		}

		try {
			ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
			arp.setTransactionLevel(Connection.TRANSACTION_READ_COMMITTED);
			_MappingKit.mapping(arp);
			// Enforces the order of the composite primary keys to prevent different development environments from generating compound primary key orders in _mappingkit
//			arp.setPrimaryKey("document", "mainMenu,subMenu");
			//    me.add(arp);
			if (PropKit.getBoolean("devMode", false)) {
				arp.setShowSql(true);
			}
			arp.setBaseSqlTemplatePath(PathKit.getRootClassPath() + File.separator + PropKit.get("sqlfile"));
			arp.addSqlTemplate("all_sqls.sql");
			arp.start();
		} catch (Exception e) {
			log.error(e.toString(), e);
		}

		try {
			EhCachePlugin ehCache = new EhCachePlugin();
			ehCache.start();
		} catch (Exception e) {
			log.error(e.toString(), e);
		}
