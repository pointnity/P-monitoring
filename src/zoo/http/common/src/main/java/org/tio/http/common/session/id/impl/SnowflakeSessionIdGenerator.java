package org.tio.http.common.session.id.impl;

import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.session.id.ISessionIdGenerator;

import com.xiaoleilu.hutool.lang.Snowflake;

/**
 * @author tanyaowu
 *  
 */
public class SnowflakeSessionIdGenerator implements ISessionIdGenerator {

	/**
	 * @param args
