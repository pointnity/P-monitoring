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
	private static String[] excludedTable = { "news_feed_reply", // Do not implement this feature temporarily
			"project_page_view", "share_page_view", "feedback_page_view", "login_log", "sensitive_words", "upload_counter", "task_run_log", "message_tip", "friend", "project_like",
			"share_like", "feedback_like", "share_reply_like", "feedback_reply_like", "like_message_log" };

	/**
	 *Reuse the data source configuration in Jfinalclubconfig to avoid redundant configuration
	 */
