package org.tio.websocket.common;

import org.tio.core.intf.TioUuid;

import com.xiaoleilu.hutool.lang.Snowflake;
import com.xiaoleilu.hutool.util.RandomUtil;

/**
 * @author tanyaowu
 * 
 */
public class WsTioUuid implements TioUuid {
	private Snowflake snowflake;

	public WsTioUuid() {
		snowflake = new Snowflake(RandomUtil.randomInt(1, 30), RandomUtil.randomInt(1, 30));
	}

	public WsTioUuid(long workerId, long datacenterId) {
		snowflake = new Snowflake(workerId, datacenterId);
	}

	/**
