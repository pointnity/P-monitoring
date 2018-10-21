package org.tio.core;

import org.tio.client.intf.ClientAioListener;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

/**
 *
 * @author tanyaowu
 */
public class DefaultAioListener implements ClientAioListener, ServerAioListener {
	/**
	 *
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @param isRemove
	 * @author tanyaowu
	 */
	@Override
	public void onAfterClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

	/**
	 *
	 * @param channelContext
	 * @param isConnected
	 * @param isReconnect
	 * @author tanyaowu
	 */
	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {
	}

	/**
	 *
	 * @param channelContext
	 * @param packet
	 * @param packetSize
	 * @author tanyaowu
	 */
	@Override
	public void onAfterReceived(ChannelContext channelContext, Packet packet, int packetSize) {
	}

	/**
	 *
	 * @param channelContext
	 * @param packet
	 * @param isSentSuccess
	 * @throws Exception
	 * @author tanyaowu
