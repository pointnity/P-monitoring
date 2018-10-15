package org.tio.http.server.view.freemarker;

import org.tio.http.common.HttpRequest;

/**
 * 
 * @author tanyaowu 
 *  
 */
public interface ModelMaker {
	
	/**
	 * 
	 * @param request
	 * @return
	 * @author tanyaowu
	 */
	Object maker(HttpRequest request);

}
