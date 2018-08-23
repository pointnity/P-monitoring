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
   	 * @param httpRequest
	 * @param httpResponse
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception;

	/**
	 *
	 * @param wsRequest
	 * @param bytes
	 * @param channelContext
	 * @return Can be wsresponse, byte[], Bytebuffer, string, or null, and if it is null, the framework does not return messages
	 * @throws Exception
	 * @author tanyaowu
	 */
	Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception;

	/**
	 *
	 * @param wsRequest
	 * @param bytes
	 * @param channelContext
	 * @return Can be wsresponse, byte[], Bytebuffer, string, or null, and if it is null, the framework does not return messages
	 * @throws Exception
	 * @author tanyaowu
	 */
	Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception;

	/**
	 * @param wsRequest
	 * @param text
	 * @param channelContext
	 * @return Can be wsresponse, byte[], Bytebuffer, string, or null, and if it is null, the framework does not return messages
	 * @throws Exception
	 * @author tanyaowu
	 */
	Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception;
}
