package org.tio.http.server.stat.ip.path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.GroupContext;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author tanyaowu 
 *  
 */
@SuppressWarnings("rawtypes")
public class IpPathAccessStatRemovalListener implements RemovalListener {
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(IpPathAccessStatRemovalListener.class);

	private IpPathAccessStatListener ipPathAccessStatListener;

	private GroupContext groupContext = null;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public IpPathAccessStatRemovalListener(GroupContext groupContext, IpPathAccessStatListener ipPathAccessStatListener) {
		this.groupContext = groupContext;
		this.ipPathAccessStatListener = ipPathAccessStatListener;
	}

	/**
	 * @param args
	 * @author: tanyaowu
	 */
	public static void main(String[] args) {

	}

	@Override
	public void onRemoval(RemovalNotification notification) {
		String ip = (String) notification.getKey();
		IpAccessStat ipAccessStat = (IpAccessStat) notification.getValue();

		if (ipPathAccessStatListener != null) {
			ipPathAccessStatListener.onExpired(groupContext, ip, ipAccessStat);
		}

		//		log.info("IP data statistics [{}] {}", ip, Json.toFormatedJson(ipAccesspathStat));
	}
}
