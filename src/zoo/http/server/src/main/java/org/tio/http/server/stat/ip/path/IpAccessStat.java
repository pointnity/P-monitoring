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
	 * Current statistics for how long, units: milliseconds
	 */
	private long duration;

	public long getDuration() {
		duration = SystemTimer.currentTimeMillis() - this.firstAccessTime;
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * ip
	 */
	private String ip;



	/**
	 *First access time, units: milliseconds
	 */
	private long firstAccessTime = SystemTimer.currentTimeMillis();

	/**
	 *First access time, units: milliseconds
	 */
	private long lastAccessTime = SystemTimer.currentTimeMillis();

	/**
	 * The number of times this IP was accessed
	 */
	public final AtomicInteger count = new AtomicInteger();
	
	/**
	 * Number of visits without session
	 */
	public final AtomicInteger noSessionCount = new AtomicInteger();
	
	
	/**
	 * Gets the ipaccesspathstat based on IP, if it does not exist in the cache, creates
	 * @param ipAccessStat
	 * @param path
	 * @return
	 * @author tanyaowu
	 */
	public IpPathAccessStat get(String path) {
		return get(path, true);
	}
	
	/**
	 * Gets ipaccesspathstat based on Ipaccessstat, if it does not exist in the cache, determines whether to create a value based on forcecreate
	 * @param ipAccessStat
	 * @param path
	 * @param forceCreate
	 * @return
	 * @author tanyaowu
	 */
	public IpPathAccessStat get(String path, boolean forceCreate) {
		if (path == null) {
			return null;
		}
		
		IpPathAccessStat ipPathAccessStat = ipPathAccessStatMap.get(path);
		if (ipPathAccessStat == null && forceCreate) {
			ipPathAccessStat = ipPathAccessStatMap.putIfAbsent(path, new IpPathAccessStat(durationType, ip, path));
		}
		
		return ipPathAccessStat;
	}

	/**
	 * 
	 * @param durationType
	 * @param ip
	 * @author tanyaowu
	 */
	public IpAccessStat(Long durationType, String ip) {
		this.durationType = durationType;
		this.ip = ip;
	}

	public MapWithLock<String, IpPathAccessStat> getIpPathAccessStatMap() {
		return ipPathAccessStatMap;
	}

	public void setIpPathAccessStatMap(MapWithLock<String, IpPathAccessStat> ipPathAccessStatMap) {
		this.ipPathAccessStatMap = ipPathAccessStatMap;
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
