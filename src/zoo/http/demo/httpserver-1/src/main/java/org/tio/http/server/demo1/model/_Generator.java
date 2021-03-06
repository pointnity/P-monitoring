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
	public static DataSource getDataSource() {
		return JfinalInit.druidPlugin.getDataSource();
	}

	public static void main(String[] args) {
		org.tio.http.server.demo1.init.JfinalInit.init();

		// Package name used by model (mappingkit default package Name)
		String modelPackageName = "org.tio.http.server.demo1.model";

		// base model The name of the package used
	        String baseModelPackageName = modelPackageName + ".base";

		// base model File Save path
		String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/" + ReUtil.replaceAll(baseModelPackageName, "\\.", "/");

		System.out.println("Output Path:" + baseModelOutputDir);

		// model File save path (mappingkit and datadictionary file default save Path)
		String modelOutputDir = baseModelOutputDir + "/..";

		// Creating generators
		Generator gen = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
		// Set Database dialect
		gen.setDialect(new MysqlDialect());
		// Add table names that you do not need to generate
		for (String table : excludedTable) {
			gen.addExcludedTable(table);
		}
		// Sets whether a DAO object is generated in Model
		gen.setGenerateDaoInModel(false);
		// Set whether to generate dictionary files
		gen.setGenerateDataDictionary(false);
		// Set the table name prefix that needs to be removed to generate Modelname.For example, the table name "osc_user", after removing the prefix "osc_" the resulting model named "user" instead of Oscuser
		// gernerator.setRemovedTableNamePrefixes("t_");
		// Generated
		gen.generate();

	}
}
