package org.tio.core;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.tio.core.intf.ChannelTraceHandler;
import org.tio.core.intf.Packet;
import org.tio.utils.json.Json;

import com.xiaoleilu.hutool.date.DatePattern;
import com.xiaoleilu.hutool.date.DateTime;

/**
 * @author tanyaowu
 *  
 */
public class DefaultChannelTraceHandler implements ChannelTraceHandler {
	//	private static Logger log = LoggerFactory.getLogger(DefaultClientTraceHandler.class);

	private Logger clientTraceLog = LoggerFactory.getLogger("tio-client-trace-log");

	/**
	 *
	 * @author tanyaowu
	 */
	public DefaultChannelTraceHandler() {
	}

	/**
