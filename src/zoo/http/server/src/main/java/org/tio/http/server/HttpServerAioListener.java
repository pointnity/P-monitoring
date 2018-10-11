package org.tio.http.server;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.http.common.HttpConst;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.server.intf.ServerAioListener;

/**
 *
 * @author tanyaowu
 *
 */
public class HttpServerAioListener implements ServerAioListener {

	//	private static Logger log = LoggerFactory.getLogger(HttpServerAioListener.class);
	private static Logger iplog = LoggerFactory.getLogger("tio-ip-trace-log");

	static Map<String, AtomicLong> ipmap = new java.util.concurrent.ConcurrentHashMap<>();
	static AtomicLong accessCount = new AtomicLong();

	/**
	 * @param args
	 *
	 * @author tanyaowu
	 *  
	 *
	 */
	public static void main(String[] args) {
	}

	/**
	 *
	 *
	 * @author tanyaowu
	 * 
	 *
	 */
	public HttpServerAioListener() {
	}

	/**
	 * @see org.tio.core.intf.AioListener#onAfterClose(org.tio.core.ChannelContext, java.lang.Throwable, java.lang.String)
	 *
	 * @param channelContext
	 * @param throwable
	 * @param remark
	 * @author tanyaowu
	 *  
	 *
	 */
	@Override
	public void onAfterClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) {
	}

	/**
	 * @see org.tio.server.intf.ServerAioListener#onAfterAccepted(java.nio.channels.AsynchronousSocketChannel, org.tio.server.AioServer)
	 *
	 * @param asynchronousSocketChannel
	 * @param aioServer
	 * @return
	 * @author tanyaowu
	 *  
	 *
	 */
	//	@Override
	//	public boolean onAfterAccepted(AsynchronousSocketChannel asynchronousSocketChannel, AioServer<ImSessionContext, HttpPacket, Object> aioServer)
	//	{
	//		return true;
	//	}

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) {
		//		HttpSessionContext httpSession = new HttpSessionContext();
		//		channelContext.setAttribute(httpSession);

		//		GroupContext<HttpSessionContext, HttpPacket, Object> groupContext = channelContext.getGroupContext();
		//		log.info(groupContext.toString());
		//		int permitsPerSecond = HttpServerStarter.conf.getInt("request.permitsPerSecond");
		//		int warnClearInterval = 1000 * HttpServerStarter.conf.getInt("request.warnClearInterval");
		//		int maxWarnCount = HttpServerStarter.conf.getInt("request.maxWarnCount");
		//		int maxAllWarnCount = HttpServerStarter.conf.getInt("request.maxAllWarnCount");
		//		RateLimiterWrap rateLimiterWrap = new RateLimiterWrap(permitsPerSecond, warnClearInterval, maxWarnCount, maxAllWarnCount);

		if (isConnected) {
			String ip = channelContext.getClientNode().getIp();

			//			ImUtils.setClient(channelContext);

			AtomicLong ipcount = ipmap.get(ip);
			if (ipcount == null) {
				ipcount = new AtomicLong();
				ipmap.put(ip, ipcount);
			}
			ipcount.incrementAndGet();

			//			String region = StringUtils.leftPad(dataBlock.getRegion(), 12);
			String accessCountStr = StringUtils.rightPad(accessCount.incrementAndGet() + "", 9);
			String ipCountStr = StringUtils.rightPad(ipmap.size() + "", 9);
			String ipStr = StringUtils.leftPad(ip, 15);
