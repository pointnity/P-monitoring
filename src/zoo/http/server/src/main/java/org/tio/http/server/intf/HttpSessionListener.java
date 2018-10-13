package org.tio.http.server.intf;

import org.tio.http.common.HttpConfig;
import org.tio.http.common.session.HttpSession;

/**
 * @author tanyaowu 
 *  
 */
public interface HttpSessionListener {
	/**
	 * 
	 * @param session
	 * @author: tanyaowu
	 */
	public void doAfterCreated(HttpSession session, HttpConfig httpConfig);

}
