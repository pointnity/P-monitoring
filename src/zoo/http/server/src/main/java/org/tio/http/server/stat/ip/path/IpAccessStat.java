package org.tio.http.server.stat.ip.path;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import org.tio.utils.SystemTimer;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.SetWithLock;

import com.xiaoleilu.hutool.date.BetweenFormater;
import com.xiaoleilu.hutool.date.BetweenFormater.Level;

/**
 * IP Access Statistics
 * @author tanyaowu 
 *  
 */
public class IpAccessStat implements Serializable {
	private static final long serialVersionUID = 5314797979230623121L;

	/**
	 * key:   path, Shaped like："/user/login"
	 * value: IpPathAccessStat
	 */
	private MapWithLock<String, IpPathAccessStat> ipPathAccessStatMap = new MapWithLock<>(new HashMap<>());
	
	private Long durationType;
	
	public final SetWithLock<String> sessionIds = new SetWithLock<>(new HashSet<>());
	
	/**
