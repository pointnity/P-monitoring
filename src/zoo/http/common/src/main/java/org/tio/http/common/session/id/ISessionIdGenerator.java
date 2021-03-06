package org.tio.http.common.session.id;

import org.tio.http.common.HttpConfig;
import org.tio.http.common.HttpRequest;

/**
 * @author tanyaowu
 *  
 */
public interface ISessionIdGenerator {

	/**
	 *
	 * @return
	 * @author tanyaowu
	 */
	String sessionId(HttpConfig httpConfig, HttpRequest request);

}
