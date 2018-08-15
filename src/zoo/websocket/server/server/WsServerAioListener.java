package org.tio.websocket.server;

import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;
import org.tio.websocket.common.WsSessionContext;

/**
 *
 * @author tanyaowu
 * July 30, 2017 Morning9:16:02
 */
public class WsServerAioListener implements ServerAioListener {


	public WsServerAioListener() {
	}

	@Override
	public void onAfterClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {
		WsSessionContext wsSessionContext = new WsSessionContext();
