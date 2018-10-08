package org.tio.http.server.demo1.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.utils.json.Json;
import org.tio.utils.jfinal.JfinalRecordSerializer;

import com.jfinal.plugin.activerecord.Record;

/**
 * @author tanyaowu
 * 
 */
public class JsonInit {
	private static Logger log = LoggerFactory.getLogger(JsonInit.class);

	public static void init() {
		Json.put(Record.class, JfinalRecordSerializer.INSTANCE);
	}

	/**
	 * @param args
	 * @author tanyaowu
	 */
