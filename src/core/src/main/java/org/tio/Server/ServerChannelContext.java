package org.tio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.Node;

/**
 *
 * @author tanyaowu
 *
 */
public class ServerChannelContext extends ChannelContext {

	/**
	 * @param groupContext
	 * @param asynchronousSocketChannel
	 *
 * @author tanyaowu
	 *  
	 *
	 */
	public ServerChannelContext(GroupContext groupContext, AsynchronousSocketChannel asynchronousSocketChannel) {
		super(groupContext, asynchronousSocketChannel);
	}

	/**
	 * @see org.tio.core.ChannelContext#createClientNode(java.nio.channels.AsynchronousSocketChannel)
	 *
	 * @param asynchronousSocketChannel
	 * @return
	 * @throws IOException
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	public Node createClientNode(AsynchronousSocketChannel asynchronousSocketChannel) throws IOException {
		InetSocketAddress inetSocketAddress = (InetSocketAddress) asynchronousSocketChannel.getRemoteAddress();
		Node clientNode = new Node(inetSocketAddress.getHostString(), inetSocketAddress.getPort());
		return clientNode;
	}

}
