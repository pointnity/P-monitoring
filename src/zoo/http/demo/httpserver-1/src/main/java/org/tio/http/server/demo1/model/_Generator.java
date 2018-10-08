package org.tio.http.server.demo1.model;

import javax.sql.DataSource;

import org.tio.http.server.demo1.init.JfinalInit;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.xiaoleilu.hutool.util.ReUtil;

/**
 * Model、BaseModel、_MappingKit Generator
 */
public class _Generator {

	/**
	 * Some features are implemented using the Db + Record mode, without the need to generate the model table in this configuration
	 */
