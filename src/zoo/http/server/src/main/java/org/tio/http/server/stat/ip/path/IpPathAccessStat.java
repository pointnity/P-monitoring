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
	//	private MapWithLock<String, AtomicInteger> mapWithLock = new MapWithLock<>(new HashMap<>());
	
	private Long durationType;
	
	/**
	 * Current statistics for how long, units: milliseconds
	 */
	private long duration;

	/**
	 * ip
	 */
	private String ip;

	/**
	 * Path
	 */
	private String path;

	/**
	 * First access time, units: milliseconds
	 */
	private long firstAccessTime = SystemTimer.currentTimeMillis();

	/**
	 * Last access time, unit: milliseconds
	 */
	private long lastAccessTime = SystemTimer.currentTimeMillis();

	/**
	 * The number of times this IP accesses this path
	 */
	public final AtomicInteger count = new AtomicInteger();
	
	public final AtomicInteger noSessionCount = new AtomicInteger();

	/**
	 * 
	 * @author tanyaowu
	 */
	public IpPathAccessStat(Long durationType, String ip, String path) {
		this.durationType = durationType;
		this.ip = ip;
		this.path = path;
	}
	
	/**
	 * @return the duration
	 */
	public String getFormatedDuration() {
		duration = SystemTimer.currentTimeMillis() - this.firstAccessTime;
		BetweenFormater betweenFormater = new BetweenFormater(duration, Level.MILLSECOND);
		return betweenFormater.format();
	}
	
	public double getPerSecond() {
		int count = this.count.get();
		long duration = getDuration();
		double perSecond = (double)((double)count / (double)duration) * (double)1000;
		return perSecond;
	}

	public Long getDurationType() {
		return durationType;
