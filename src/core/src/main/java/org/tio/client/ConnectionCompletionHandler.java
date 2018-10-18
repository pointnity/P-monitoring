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
				if (isReconnect) {
					channelContext.setAsynchronousSocketChannel(asynchronousSocketChannel);
					//				channelContext.getDecodeRunnable().setCanceled(false);
					channelContext.getHandlerRunnable().setCanceled(false);
					//		channelContext.getHandlerRunnableHighPrior().setCanceled(false);
					channelContext.getSendRunnable().setCanceled(false);
					//		channelContext.getSendRunnableHighPrior().setCanceled(false);

					clientGroupContext.closeds.remove(channelContext);
				} else {
					channelContext = new ClientChannelContext(clientGroupContext, asynchronousSocketChannel);
					channelContext.setServerNode(serverNode);
					channelContext.getStat().setTimeClosed(SystemTimer.currentTimeMillis
				}

				channelContext.setBindIp(bindIp);
				channelContext.setBindPort(bindPort);

				channelContext.setReconnCount(0);
				channelContext.setClosed(false);
				isConnected = true;

				attachment.setChannelContext(channelContext);

//				clientGroupContext.ips.bind(channelContext);
				clientGroupContext.connecteds.add(channelContext);

				ReadCompletionHandler readCompletionHandler = channelContext.getReadCompletionHandler();
				ByteBuffer readByteBuffer = readCompletionHandler.getReadByteBuffer();//ByteBuffer.allocateDirect(channelContext.getGroupContext().getReadBufferSize());
				readByteBuffer.position(0);
			readByteBuffer.limit(readByteBuffer.capacity());
				asynchronousSocketChannel.read(readByteBuffer, readByteBuffer, readCompletionHandler);

				log.info("connected to {}", serverNode);
				if (isConnected && !isReconnect) {
					channelContext.getStat().setTimeFirstConnected(SystemTimer.currentTimeMillis());
				}
			} else {
				log.error(throwable.toString(), throwable);
				if (channelContext == null) {
					channelContext = new ClientChannelContext(clientGroupContext, asynchronousSocketChannel);
					channelContext.setServerNode(serverNode);
					channelContext.getStat().setTimeClosed(SystemTimer.currentTimeMillis());
				}

				if (!isReconnect) //is not the reconnection, is the first connection, need to add channelcontext to the Closeds ranks
				{
					clientGroupContext.closeds.add(channelContext);
				}

				attachment.setChannelContext(channelContext);

				ReconnConf.put(channelContext);
			}
		} catch (Throwable e) {
			log.error(e.toString(), e);
		} finally {
			if (attachment.getCountDownLatch() != null) {
				attachment.getCountDownLatch().countDown();
			}

			try {
