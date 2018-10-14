package org.tio.http.server.stat.ip.path;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import org.tio.utils.SystemTimer;

import com.xiaoleilu.hutool.date.BetweenFormater;
import com.xiaoleilu.hutool.date.BetweenFormater.Level;

/**
 * IP Access Path Statistics
 * @author tanyaowu 
 * 
 */
public class IpPathAccessStat implements Serializable {
	private static final long serialVersionUID = 5314797979230623121L;

	/**
	 * key:   path, Shaped like："/user/login"
	 * value: Number of visits
	 */
