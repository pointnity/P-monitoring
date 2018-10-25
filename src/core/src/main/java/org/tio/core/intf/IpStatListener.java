package org.tio.core.intf;

import org.tio.core.GroupContext;
import org.tio.core.stat.IpStat;

/**
 * @author tanyaowu 
 *  
 */
public interface IpStatListener {
	/**
	 * After the statistics period expires, the user can implement in this method to put the relevant data into the library or log, etc.
	 * @param ipStat
