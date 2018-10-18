package org.tio.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Node;
import org.tio.core.ReadCompletionHandler;
import org.tio.utils.SystemTimer;

/**
 *
 * @author tanyaowu
 *  
 */
public class ConnectionCompletionHandler implements CompletionHandler<Void, ConnectionCompletionVo> {
	private static Logger log = LoggerFactory.getLogger(ConnectionCompletionHandler.class);

	/**
	 *
	 * @param result
	 * @param attachment
	 * @author tanyaowu
	 */
	@Override
	public void completed(Void result, ConnectionCompletionVo attachment) {
		handler(result, attachment, null);
	}

	/**
	 *
	 * @param throwable
	 * @param attachment
	 * @author tanyaowu
	 */
	@Override
	public void failed(Throwable throwable, ConnectionCompletionVo attachment) {
		handler(null, attachment, throwable);
	}

	/**
	 *
	 * @param result
	 * @param attachment
	 * @param throwable
	 * @author tanyaowu
	 */
	private void handler(Void result, ConnectionCompletionVo attachment, Throwable throwable) {
		ClientChannelContext channelContext = attachment.getChannelContext();
		AsynchronousSocketChannel asynchronousSocketChannel = attachment.getAsynchronousSocketChannel();
		AioClient aioClient = attachment.getAioClient();
		ClientGroupContext clientGroupContext = aioClient.getClientGroupContext();
		Node serverNode = attachment.getServerNode();
		String bindIp = attachment.getBindIp();
		Integer bindPort = attachment.getBindPort();
		ClientAioListener clientAioListener = clientGroupContext.getClientAioListener();
		boolean isReconnect = attachment.isReconnect();
		boolean isConnected = false;

		try {
			if (throwable == null) {
