package org.tio.core.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.GroupContext;
import org.tio.core.intf.IpStatListener;
import org.tio.core.stat.IpStat;

import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author tanyaowu 
 *  
 */
@SuppressWarnings("rawtypes")
public class IpStatRemovalListener implements RemovalListener {
	private static Logger log = LoggerFactory.getLogger(IpStatRemovalListener.class);

	private IpStatListener ipStatListener;

	private GroupContext groupContext = null;

	/**
	 * 
	 * @author: tanyaowu
	 */
	public IpStatRemovalListener(GroupContext groupContext, IpStatListener ipStatListener) {
		this.groupContext = groupContext;
		this.ipStatListener = ipStatListener;
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
		IpStat ipStat = (IpStat) notification.getValue();

		if (ipStatListener != null) {
			ipStatListener.onExpired(groupContext, ipStat);
		}
