package org.tio.websocket.server.handler;

import org.tio.core.ChannelContext;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.websocket.common.WsRequest;

/**
 *
 * @author tanyaowu
 * 
 */
public interface IWsMsgHandler {
	/**
	 * The HttpResponse parameter is supplemented and returned, and if NULL indicates that the connection is not established, the framework disconnects, and if a non null is returned, the frame sends the object to the other
   
